package com.nemo.game.data.mysql;

import com.nemo.common.jdbc.JdbcTemplate;
import com.nemo.common.persist.PersistFactory;
import com.nemo.common.persist.PersistTask;
import com.nemo.common.persist.PersistableCache;
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
        PersistableCache cache = new PersistableCache(this.template, 10000);
        cacheMap.put(persistFactory.dataType(), cache);
        PersistTask task = new PersistTask(template, persistFactory, cache);
        persistTaskMap.put(persistFactory.dataType(), task);
        executor.scheduleAtFixedRate(task, 10*1000, persistFactory.taskPeriod(), TimeUnit.MILLISECONDS);
    }





}
