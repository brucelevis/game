package com.nemo.game;

import com.nemo.common.config.ConfigDataManager;
import com.nemo.common.jdbc.ConnectionPool;
import com.nemo.common.jdbc.DruidConnectionPool;
import com.nemo.common.jdbc.JdbcTemplate;
import com.nemo.game.data.DataCenter;
import com.nemo.game.server.EventListener;
import com.nemo.game.server.GameMessagePool;
import com.nemo.game.server.MessageRouter;
import com.nemo.game.server.ServerOption;
import com.nemo.log.LogService;
import com.nemo.log.consumer.mysql.MysqlLogConsumer;
import com.nemo.net.NetworkService;
import com.nemo.net.NetworkServiceBuilder;

//游戏服
public class GameServer {
    public static String GAME_DB = "GAME_DB";
    //是否已经启动标志
    private boolean state = false;
    //消息分发器
    MessageRouter router;
    //Netty网络服务
    NetworkService netWork;

    public GameServer(ServerOption option) throws Exception{
        int bossLoopGroupCount = 4;
        int workerLoopGroupCount = Runtime.getRuntime().availableProcessors() < 8 ? 8
                : Runtime.getRuntime().availableProcessors();

        NetworkServiceBuilder builder = new NetworkServiceBuilder();
        builder.setBossLoopGroupCount(bossLoopGroupCount);
        builder.setWorkerLoopGroupCount(workerLoopGroupCount);
        builder.setPort(option.getGameServerPort());

        builder.setMsgPool(new GameMessagePool());
        builder.setNetworkEventlistener(new EventListener());

        builder.setWebSocket(true);
        builder.setSsl(option.isSsl());
        builder.setSslKeyCertChainFile(option.getSslKeyCertChainFile());
        builder.setSslKeyFile(option.getSslKeyFile());
//        builder.setIdleMaxTime(option.getIdelTime() > 0 ? option.getIdelTime() : 10);

        //注册消息处理器
        router = new MessageRouter();
        builder.setConsumer(router);
        //创建网络服务
        netWork = builder.createService(); //搭建NetworkerService 最根本是搭建netty服务所需的ServerBootstrap
        //初始化数据中心
        DataCenter.init(option);
        //初始化配置表
        ConfigDataManager.getInstance().init(option.getConfigDataPath());
        //初始化日志服务
        ConnectionPool connectionPool = new DruidConnectionPool(option.getLogDBConfigPath());
        JdbcTemplate template = new JdbcTemplate(connectionPool);
        LogService.addConsumer(new MysqlLogConsumer(template));
        //LogService.addConsumer(new TextLogConsumer());
        LogService.start("com.nemo.game.log", 4, 4);









    }

    public MessageRouter getRouter(){
        return router;
    }

    public void start() {
        netWork.start(); //启动netty服务
        if(netWork.isRunning()) {
            state = true;
        }
    }

    public void stop() {
        netWork.stop(); //优雅关闭netty服务
        state = false;
    }

    public boolean isOpen() {
        return state;
    }
}
