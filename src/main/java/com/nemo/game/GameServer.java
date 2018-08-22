package com.nemo.game;

import com.nemo.game.server.MessageRouter;
import com.nemo.game.server.ServerOption;

//游戏服
public class GameServer {
    public static String GAME_DB = "GAME_DB";
    //是否已经启动标志
    private boolean state = false;
    //消息分发器
    MessageRouter router;

    public GameServer(ServerOption option) throws Exception{







    }

    public MessageRouter getRouter(){
        return router;
    }

    public void start() {



    }

    public void stop() {

        state = false;
    }


    public boolean isOpen() {
        return state;
    }
}
