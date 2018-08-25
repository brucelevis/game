package com.nemo.game.data;

import com.nemo.game.data.mysql.MysqlDataProviderProxy;
import com.nemo.game.server.ServerOption;

//数据中心，游戏中的缓存全由此类管理
public class DataCenter {
    private static IDataProvider provider;

    public static void init(ServerOption option) throws Exception {
        //数据库代理类
        provider = new MysqlDataProviderProxy(option);
    }





}
