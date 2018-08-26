package com.nemo.game.data.mysql;

import com.nemo.common.jdbc.ConnectionPool;
import com.nemo.common.jdbc.DruidConnectionPool;
import com.nemo.common.jdbc.JdbcTemplate;
import com.nemo.game.data.IDataProvider;
import com.nemo.game.data.mysql.factory.*;
import com.nemo.game.server.ServerOption;
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




        }







    }




}
