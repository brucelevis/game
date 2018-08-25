package com.nemo.game.data.mysql;

import com.nemo.common.jdbc.ConnectionPool;
import com.nemo.common.jdbc.DruidConnectionPool;
import com.nemo.common.jdbc.JdbcTemplate;
import com.nemo.game.data.IDataProvider;
import com.nemo.game.data.mysql.factory.UserPersistFactory;
import com.nemo.game.server.ServerOption;
import com.nemo.game.util.JdbcUtil;

//mysql数据库代理，所有数据库存储相关的请求，都代理到了CacheManager
public class MysqlDataProviderProxy implements IDataProvider{

    private JdbcTemplate template;

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








    }




}
