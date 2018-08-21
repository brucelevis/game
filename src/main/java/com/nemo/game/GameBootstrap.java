package com.nemo.game;

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

        ServerOption option = new ServerOption();
        option.build(configPath);
        LOGGER.info("服务器当前版本号：{}", version);



    }



}
