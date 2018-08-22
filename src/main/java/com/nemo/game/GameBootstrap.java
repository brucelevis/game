package com.nemo.game;

import com.nemo.game.back.BackServer;
import com.nemo.game.server.ServerOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameBootstrap {
    private static Logger LOGGER = LoggerFactory.getLogger(GameBootstrap.class);

    public static void main(String[] args) {
        String configPath;
        int version = 0;
        if(args.length > 0) {
            configPath = args[0];
        } else {
            throw new RuntimeException("缺少参数 启动失败");
        }
        LOGGER.info("----------------configPath {} -------------------", configPath);

        //将配置属性读取到ServerOption中
        ServerOption option = new ServerOption();
        option.build(configPath);
        LOGGER.info("服务器当前版本号：{}", version);
        //初始化GameContext
        GameContext.init(option);
        //启动游戏服
        try{
            GameServer gameServer = GameContext.createGameServer();
            gameServer.start();
        } catch (Exception e) {
            LOGGER.error("游戏服务器启动失败", e);
            System.exit(0);
        }
        LOGGER.info("游戏服务器启动成功...");
        //启动后台服务器
        try{
            BackServer backServer = GameContext.createBackServer();
            backServer.start();
        } catch (Exception e) {
            LOGGER.error("后台服务器启动失败", e);
            System.exit(0);
        }








    }



}
