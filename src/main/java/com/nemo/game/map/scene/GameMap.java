package com.nemo.game.map.scene;

import com.nemo.game.map.aoi.AOIEventListenerImpl;
import com.nemo.game.map.aoi.TowerAOI;
import com.nemo.game.map.route.PathFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//场景顶层父类 所有地图类都继承
public class GameMap {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameMap.class);

    //地形
    protected Topography topography;
    //灯塔视野管理
    protected TowerAOI aoi;
    //路径寻找器
    protected PathFinder pathFinder;

    public GameMap(Topography topography) {
        this.topography = topography;
        this.aoi = new TowerAOI(topography.getWidth(), topography.getHeight());
        this.aoi.addListener(new AOIEventListenerImpl());
        this.pathFinder = new PathFinder(topography.getWidth(), topography.getHeight());
    }

}
