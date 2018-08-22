package com.nemo.game;

import com.nemo.game.back.BackServer;
import com.nemo.game.server.ServerOption;
import com.nemo.game.util.TimeUtil;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Created by h on 2018/8/5.
 */
@Getter
@Setter
public class GameContext {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameContext.class);

    private static int serverId;

    private static int serverType;
    //开服日期
    private static LocalDateTime openTime;
    //开服当天0点时间戳
    private static long openDayZeroTime;
    //合服日期
    private static LocalDateTime combineTime;
    //合服当天0点时间戳
    private static long combineDayZeroTime;
    //是否已经合服
    private static boolean combined = false;
    //是否开启全服双倍经验
    private static int expDouble = 1;

    private static ServerOption option;

    private static GameServer gameServer;

    private static BackServer backServer;

    private static boolean isDebug;
    //服务器关闭逻辑是否已经执行
    private static boolean serverCloseLogicExecuted;
    //游戏服务器关闭
    private static boolean closed;
    //是否防沉迷
    private static boolean fcm = false;

    private static boolean ready;

    public static void init(ServerOption option) {
        GameContext.option = option;
        serverId = option.getServerId();
        serverType = option.getServerType();
        openTime = option.getOpenTime();
        isDebug = option.isDebug();
        //开服日期时间戳
        long openTimeMills = openTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000;
        openDayZeroTime = TimeUtil.dayZeroMillsFromTime(openTimeMills);

        LOGGER.info("开服时间：{}", openTime);
        LOGGER.info("开服当天0点时间戳：{}", openDayZeroTime);
        LOGGER.info("开服距离开服当天0点时间：{}", (openTimeMills - openDayZeroTime));

        combineTime = option.getCombineTime();
        if(combineTime != null) {
            long combineTimeMills = combineTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000;
            combineDayZeroTime = TimeUtil.dayZeroMillsFromTime(combineTimeMills);
            if(combineTimeMills <= openTimeMills) {
                throw new RuntimeException("开服与合服时间配置错误");
            }
            combined = true;

            LOGGER.info("合服时间：{}", combineTime);
            LOGGER.info("合服当天0点时间戳：{}", combineDayZeroTime);
            LOGGER.info("合服距离合服当天0点：{}", (combineTimeMills - combineDayZeroTime));
        }
    }

    public static GameServer createGameServer() {
        try {
            gameServer = new GameServer(option);
            return gameServer;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public static BackServer createBackServer() {
        try {
            backServer = new BackServer(option);
            ready = true;
            return backServer;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
