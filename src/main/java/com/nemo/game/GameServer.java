package com.nemo.game;

import com.nemo.game.data.DataCenter;
import com.nemo.game.server.EventListener;
import com.nemo.game.server.GameMessagePool;
import com.nemo.game.server.MessageRouter;
import com.nemo.game.server.ServerOption;
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
        int workerLoopGroupCount = Runtime.getRuntime().availableProcessors() < 8 ? 8 : Runtime.getRuntime().availableProcessors();

        NetworkServiceBuilder builder = new NetworkServiceBuilder();
        builder.setBossLoopGroupCount(bossLoopGroupCount);
        builder.setWorkerLoopGroupCount(workerLoopGroupCount);
        builder.setPort(option.getGameServerPort());

        builder.setMsgPool(new GameMessagePool());
        builder.setNetworkEventlistener(new EventListener());
        builder.setWebSocket(true);
        router = new MessageRouter();
        builder.setConsumer(router);
        //创建网络服务
        netWork = builder.createService(); //搭建NetworkerService 最根本是搭建netty服务所需的ServerBootstrap
        //初始化数据中心
        DataCenter.init(option);








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
