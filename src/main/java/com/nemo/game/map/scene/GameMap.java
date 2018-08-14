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

    //地图id
    protected int id;
    //地图分线
    protected int line;
    //地图名字
    protected String name;
    //分线id 配置表id
    protected int cfgId;
    //地图能否重叠站
    protected boolean canCross = false;

    public GameMap(Topography topography) {
        this.topography = topography;
        this.aoi = new TowerAOI(topography.getWidth(), topography.getHeight());
        this.aoi.addListener(new AOIEventListenerImpl());
        this.pathFinder = new PathFinder(topography.getWidth(), topography.getHeight());
    }

    public void setTopography(Topography topography) {
        this.topography = topography;
    }

    public Topography getTopography() {
        return topography;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCfgId(int cfgId) {
        this.cfgId = cfgId;
    }

    public int getCfgId() {
        return cfgId;
    }

    public void setCanCross(boolean canCross) {
        this.canCross = canCross;
    }

    public boolean isCanCross() {
        return canCross;
    }

    //用于做一些额外的初始化工作 在各种地图中
    public boolean init(){
        return true;
    }

}
