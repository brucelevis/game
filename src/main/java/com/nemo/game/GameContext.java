package com.nemo.game;

import com.nemo.game.server.ServerOption;

/**
 * Created by h on 2018/8/5.
 */
public class GameContext {
    private static ServerOption option;

    public static ServerOption getOption() {
        return option;
    }
}