package com.sh.game.map.scene;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//场景顶层父类 所有地图类都继承
public class GameMap {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameMap.class);

    //地形
    protected Topography topography;


    public GameMap(Topography topography) {
        this.topography = topography;



    }

}
