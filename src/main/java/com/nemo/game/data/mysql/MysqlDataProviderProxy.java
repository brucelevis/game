package com.nemo.game.data.mysql;

import com.nemo.common.jdbc.ConnectionPool;
import com.nemo.common.jdbc.DruidConnectionPool;
import com.nemo.common.jdbc.JdbcTemplate;
import com.nemo.common.jdbc.ProtostuffRowMapper;
import com.nemo.common.persist.Cacheable;
import com.nemo.common.persist.Persistable;
import com.nemo.game.back.entity.Announce;
import com.nemo.game.back.listener.BackServerHeartListener;
import com.nemo.game.data.DataCenter;
import com.nemo.game.data.DataType;
import com.nemo.game.data.IDataProvider;
import com.nemo.game.data.mysql.factory.*;
import com.nemo.game.data.mysql.mapper.AnnounceMapper;
import com.nemo.game.data.mysql.mapper.RankHeroMapper;
import com.nemo.game.data.mysql.mapper.RankMapper;
import com.nemo.game.data.mysql.mapper.UserMapper;
import com.nemo.game.entity.*;
import com.nemo.game.entity.sys.SysData;
import com.nemo.game.server.ServerOption;
import com.nemo.game.system.count.CountManager;
import com.nemo.game.system.count.entity.Count;
import com.nemo.game.system.user.field.UserField;
import com.nemo.game.util.JdbcUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//mysql数据库代理，所有数据库存储相关的请求，都代理到了CacheManager
public class MysqlDataProviderProxy implements IDataProvider{
    private static final Object NULL = new Object();

    private Map<String, Long> nameSidPid2Uid = new ConcurrentHashMap<>();

    private Map<Long, Object> uidMap = new ConcurrentHashMap<>();

    public static Map<Long, Object> unionMaps = new ConcurrentHashMap<>();

    private JdbcTemplate template;
    //管理各类数据缓存和定时任务执行
    private MysqlDataProvider provider;

    public MysqlDataProviderProxy(ServerOption option) throws Exception {
        String gameDbConfigPath = option.getGameDbConfigPath();
        //创建数据库连接池
        ConnectionPool connectionPool = new DruidConnectionPool(gameDbConfigPath);
        //创建JDBC模板
        JdbcTemplate template = new JdbcTemplate(connectionPool);
        this.template = template;
        provider = new MysqlDataProvider();
        provider.init(this.template);
        JdbcUtil.init(this.template);

        //注册factory
        provider.registerPersistTask(new UserPersistFactory());
        provider.registerPersistTask(new RolePersistFactory());
        provider.registerPersistTask(new BagPersistFactory());
        provider.registerPersistTask(new CountPersistFactory());
        provider.registerPersistTask(new SysDataPersistFactory());
        provider.registerPersistTask(new SysUnionPersistFactory());
        provider.registerPersistTask(new RankPersistFactory());
        provider.registerPersistTask(new RankHeroPersistFactory());
        provider.registerPersistTask(new OrderPersistFactory());
        provider.registerPersistTask(new RoleMailPersistFactory());
        provider.registerPersistTask(new AnnouncePersistFactory());

        //加载所有的uid
        List<Map<String, Object>> users = template.queryList("select id, loginName, sid, pid from p_user",
                JdbcTemplate.MAP_MAPPER);
        for(Map<String, Object> user : users) {
            long id = (long)user.get(UserField.id);
            String loginName = (String) user.get(UserField.loginName);
            int sid = (int) user.get(UserField.sid);
            int pid = (int) user.get(UserField.pid);
            nameSidPid2Uid.put(loginName + "_" + sid + "_" + pid, id);
            uidMap.put(id, NULL);
        }

        //加载所有unionId
        List<Map<String, Object>> mapList = template.queryList("select id from s_union", JdbcTemplate.MAP_MAPPER);
        for(Map<String, Object> map : mapList) {
            long unionId = (long)map.get("id");
            unionMaps.put(unionId, NULL);
        }
    }

    //是否有这个玩家
    private boolean hasUser(long id) {
        return uidMap.containsKey(id);
    }

    //是否有这个公会
    private boolean hasUnion(long id) {
        return unionMaps.containsKey(id);
    }

    @Override
    public void updateData(Cacheable cache, boolean immediately) {
        provider.update(cache.getId(), cache.dataType(), immediately);
    }

    @Override
    public void updateData(long dataId, int dataType, boolean immediately) {
        provider.update(dataId, dataType, immediately);
    }

    @Override
    public void deleteData(Cacheable cache, boolean immediately) {
        provider.removeFromDisk(cache.getId(), cache.dataType(), immediately);
    }

    @Override
    public void insertData(Cacheable cache, boolean immediately) {
        provider.insert((Persistable) cache, immediately);
    }

    @Override
    public void addData(Cacheable cache) {
        provider.put((Persistable) cache);
    }

    @Override
    public void removeData(Cacheable cache) {
        provider.removeFromCache(cache.getId(), cache.dataType());
    }

    @Override
    public void store() {
        provider.store();
    }

    @Override
    public void registerUser(User user) {
        nameSidPid2Uid.put(user.getLoginName() + "_" + user.getSid() + "_" + user.getPid(), user.getId());
    }

    @Override
    public User getUser(String loginName, int sid, int pid) {
        String key = loginName + "_" + sid + "_" + pid;
        Long id = nameSidPid2Uid.get(key);
        if(id == null) {
            return null;
        }
        User user = provider.get(id, DataType.USER);
        if(user == null) {
            user = template.query("select id, loginName, roleName, sid, pid, ip, client, type, IDNumber, regTime, qudao " +
                    "from p_user where id = ?", new UserMapper(), id);
            return user;
        }
        return user;
    }

    @Override
    public User getUser(long id) {
        if (hasUser(id)) {
            User user = provider.get(id, DataType.USER);
            if (user == null) {
                user = template.query("select id, loginName, roleName, sid, pid, ip, client, type, IDNumber, regTime, qudao " +
                        "from p_user where id = ?", new UserMapper(), id);
                if (user != null) {
                    provider.put(user);
                }
            }
            return user;
        }
        return null;
    }

    @Override
    public Role getRole(long id) {
        Role role = provider.get(id, DataType.ROLE);
        if(role == null) {
            if(hasUser(id)) {
                role = template.query("select data from p_role where id = ?", new ProtostuffRowMapper<Role>(Role.class), id);
            }
            if(role != null) {
                provider.put(role);
            }
        }
        return role;
    }

    @Override
    public RoleBag getBag(long id) {
        RoleBag bag = provider.get(id, DataType.BAG);
        if (bag == null) {
            if (hasUser(id)) {
                bag = template.query("select data from p_bag where id = ?", new ProtostuffRowMapper<RoleBag>(RoleBag.class), id);
                if (bag != null) {
                    provider.put(bag);
                }
                return bag;
            }
        }
        return bag;
    }

    @Override
    public RoleMail getRoleMail(long id) {
        RoleMail roleMail = provider.get(id, DataType.ROLE_MAIL);
        if (roleMail == null) {
            if (hasUser(id)) {
                roleMail = template.query("select data from p_mail where id = ?", new ProtostuffRowMapper<RoleMail>(RoleMail.class), id);
                if (roleMail != null) {
                    provider.put(roleMail);
                }
                return roleMail;
            }
        }
        return roleMail;
    }

    @Override
    public RoleCount getCount(long id) {
        RoleCount roleCount = provider.get(id, DataType.COUNT);
        if (roleCount == null) {
            if (hasUser(id)) {
                roleCount = template.query("select data from p_count where id = ?", new ProtostuffRowMapper<RoleCount>(RoleCount.class), id);
                if (roleCount != null) {
                    provider.put(roleCount);
                }
                return roleCount;
            }
        }
        for (Count count : roleCount.getCountMap().values()) {
            if (count.getCount() > 0) {
                if (CountManager.getInstance().checkReset(count)) {
                    DataCenter.updateData(roleCount);
                }
            }
        }
        return roleCount;
    }

    @Override
    public <T extends SysData> T getSysData(long id, Class<T> clazz) {
        T data = provider.get(id, DataType.SYS);
        if (data == null) {
            data = template.query("select data from s_data where id = ?", new ProtostuffRowMapper<>(clazz), id);
            if (data != null) {
                provider.put(data);
            }
            return data;
        }
        return data;
    }

    @Override
    public Union getUnion(long id) {
        Union union = provider.get(id, DataType.SYS_UNION);
        if (union == null) {
            if (hasUnion(id)) {
                union = template.query("select data from s_union where id = ?", new ProtostuffRowMapper<>(Union.class), id);
                if (union != null) {
                    provider.put(union);
                }
                return union;
            }
        }
        return union;
    }

    @Override
    public Rank getRank(long id) {
        Rank rank = provider.get(id, DataType.RANK);
        if (rank == null) {
            rank = template.query("select id, name, allFightPower, allLevel, achievement, vitality, pk," +
                    " time_fp, time_lv, time_ac, time_vt, time_pk from s_rank where id = ?", new RankMapper(), id);
            if (rank != null) {
                provider.put(rank);
            }
        }
        return rank;
    }

    @Override
    public RankHero getRankHero(long id) {
        RankHero rank = provider.get(id, DataType.RANK_HERO);
        if (rank == null) {
            rank = template.query("select id, rid, name, career, fightPower, lv, `time` from s_rank_hero where id = ?",
                    new RankHeroMapper(), id);
            if (rank != null) {
                provider.put(rank);
            }
        }
        return rank;
    }

    @Override
    public List<Rank> getRankList(String type, String timeType) {
        List<Rank> rankList = this.template.queryList("select id, name, allFightPower, allLevel, achievement, " +
                "vitality, pk, time_fp, time_lv, time_ac, time_vt, time_pk from s_rank order by "
                + type + " desc , " + timeType + " asc limit 0,100", new RankMapper());
        return rankList;
    }

    @Override
    public List<RankHero> getRankHeroList(int career) {
        List<RankHero> rankList = this.template.queryList("select id, rid, name, career, fightPower, lv, `time` from s_rank_hero " +
                "where career = ? order by fightPower desc , `time` asc limit 0,100", new RankHeroMapper(), career);
        return rankList;
    }

    @Override
    public List<Announce> getAnnounceList() {
        return BackServerHeartListener.announces;
    }

    @Override
    public Announce getAnnounce(long id) {
        return this.template.query("select id, uniqueId, startTime, endTime, period, type, content from s_announce where id = ?",
                new AnnounceMapper(), id);
    }
}
