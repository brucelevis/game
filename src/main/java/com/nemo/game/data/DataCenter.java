package com.nemo.game.data;

import com.nemo.common.persist.Cacheable;
import com.nemo.game.data.mysql.MysqlDataProviderProxy;
import com.nemo.game.server.ServerOption;

//数据中心，游戏中的缓存全由此类管理
public class DataCenter {
    private static IDataProvider provider;

    public static void init(ServerOption option) throws Exception {
        //数据库代理类
        provider = new MysqlDataProviderProxy(option);
    }

    //更新数据到磁盘
    public static void updateData(Cacheable cache, boolean immediately) {
        provider.updateData(cache, immediately);
    }

    public static void updateData(Cacheable cache) {
        provider.updateData(cache, false);
    }





}
