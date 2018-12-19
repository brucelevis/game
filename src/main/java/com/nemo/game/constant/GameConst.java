package com.nemo.game.constant;

public interface GameConst {

    interface QueueId {
        //登录和下线队列
        int ONE_LOGIN_LOGOUT = 1;
        //玩家队列
        int TWO_PLAYER = 2;
        //帮会模块
        int THREE_UNION = 3;
        //副本模块 主要针对多人副本（战报）
        int FOUR_INSTANCE = 4;
        //场景
        int FIVE_MAP = 5;
        //通用处理器，主要针对内部使用，不对外
        int SIX_COMMON = 6;
    }
}
