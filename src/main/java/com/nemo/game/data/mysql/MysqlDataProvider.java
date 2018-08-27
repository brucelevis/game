package com.nemo.game.data.mysql;

import com.nemo.common.jdbc.JdbcTemplate;
import com.nemo.common.persist.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

//数据管理类 主要是指玩家游戏数据，保存方式二进制
public class MysqlDataProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlDataProvider.class);
    //线程池线程个数
    private static final int EXECUTOR_CORE_SIZE = 8;
    //持久化任务map
    private Map<Integer, PersistTask> persistTaskMap = new HashMap<>();
    //缓存map
    private Map<Integer, PersistableCache> cacheMap = new HashMap<>();
    //JDBC模板类
    private JdbcTemplate template;
    //持久化线程池
    ScheduledThreadPoolExecutor executor;

    public void init(JdbcTemplate template) {
        this.template = template;
        executor = new ScheduledThreadPoolExecutor(EXECUTOR_CORE_SIZE, new ThreadFactory() {
            final AtomicInteger count = new AtomicInteger(0);
            @Override
            public Thread newThread(Runnable r) {
                int curCount = count.incrementAndGet();
                return new Thread(r, "数据库持久化线程（线程池）-" + curCount);
            }
        });
    }

    //注册一个持久化任务
    public void registerPersistTask(PersistFactory persistFactory) {
        //创建对应缓存
        PersistableCache cache = new PersistableCache(this.template, 10000);
        cacheMap.put(persistFactory.dataType(), cache);
        //注册一个持久化任务
        PersistTask task = new PersistTask(template, persistFactory, cache);
        persistTaskMap.put(persistFactory.dataType(), task);
        //初始延迟10s后以工厂定义的频率执行任务
        executor.scheduleAtFixedRate(task, 10*1000, persistFactory.taskPeriod(), TimeUnit.MILLISECONDS);
    }

    //定时线程中添加一条数据 异步更新
    public void persist(Persistable data, PersistType type) {
        PersistTask task = persistTaskMap.get(data.dataType());
        task.add(data, type);
    }

    //从缓存中获得一条数据
    public <T> T get(long id, int dataType) {
        return (T)cacheMap.get(dataType).get(id);
    }

    //当前数据数量
    public int size(int dataType) {
        return cacheMap.get(dataType).size();
    }

    //结束持久化任务 并存储尚未更新的数据
    public void store() {
        executor.shutdown();
        int t = 120;
        try {
            while (t > 0 && !executor.awaitTermination(1, TimeUnit.SECONDS)) {
                t--;
            }
        } catch (InterruptedException e) {
            LOGGER.error("结束持久化任何，并且存储尚未更新的数据 中断", e);
        } catch (Exception e) {
            LOGGER.error("结束持久化任何，并且存储尚未更新的数据 异常", e);
        }

        try {
            for (PersistTask task : persistTaskMap.values()) {
                task.run();
            }
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }

    //放入缓存数据
    public void put(Persistable obj) {
        cacheMap.get(obj.dataType()).put(obj);
    }

    //插入一条数据
    public void insert(Persistable obj, boolean immediately) {
        if(obj == null) {
            return;
        }
        PersistableCache cache = cacheMap.get(obj.dataType());
        cache.put(obj);
        if(immediately) {
            PersistFactory factory = getFactory(obj.dataType());
            if(factory == null) {
                return;
            }
            try {
                template.update(factory.createInsertSql(), factory.createInsertParameters(obj));
            } catch (Exception e) {
                LOGGER.error("立即写入数据库失败,id:" + obj.getId(), e);
            }
        } else {
            persist(obj, PersistType.INSERT);
        }
    }

    //更新一条数据
    public void update(long dataId, int dataType, boolean immediately) {
        Persistable obj = cacheMap.get(dataType).get(dataId);
        if(obj == null) {
            return;
        }
        if(immediately) {
            PersistFactory factory = getFactory(dataType);
            if(factory == null) {
                return;
            }
            try {
                //立即更新直接调用模板
                template.update(factory.createUpdateSql(), factory.createUpdateParameters(obj));
            } catch (Exception e) {
                LOGGER.error("立即更新数据库失败, id:" + obj.getId(), e);
            }
        } else {
            //不立即添加到线程池
            persist(obj, PersistType.UPDATE);
        }
    }

    //删除一条数据，缓存和数据库中都删除
    public Persistable removeFromDisk(long id, int dataType, boolean immediately) {
        PersistableCache cache = cacheMap.get(dataType);
        Persistable obj = cache.get(id);
        if(obj == null) {
            return null;
        }
        if(immediately) {
            cache.remove(id);
            PersistFactory factory = getFactory(obj.dataType());
            if(factory == null) {
                throw new RuntimeException("找不到持久化工厂类" + obj.dataType());
            }
            try {
                template.update(factory.createDeleteSql(), factory.createDeleteParameters(obj));
            } catch (Exception e) {
                LOGGER.error("立即删除数据库失败,id:" + obj.getId(), e);
            }
        } else {
            persist(obj, PersistType.DELETE);
        }
        return obj;
    }

    //只从缓存中删除一条数据
    public void removeFromCache(long id, int dataType) {
        cacheMap.get(dataType).remove(id);
    }



    private PersistFactory getFactory(int dataType) {
        PersistTask task = persistTaskMap.get(dataType);
        if(task == null) {
            return null;
        }
        return task.getPersistFactory();
    }




}
