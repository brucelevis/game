package com.nemo.game.map.obj;

import com.nemo.game.map.scene.GameMap;
import com.nemo.game.map.scene.Point;

//地图中的对象
public interface IMapObject {
    long getId();

    void setId(long id);

    int getConfigId();

    void setConfigId(int configId);

    int getMapId();

    void setMapId(int mapId);

    int getLine();

    void setLine(int line);

    boolean isVisible();

    void setVisible(boolean visible);

    Point getPoint();

    void setPoint(Point point);

    String getName();

    void setName(String name);

    String getShowName();

    boolean penetrate(IMapObject obj, GameMap map); //穿透规则

    boolean overlying(IMapObject obj, GameMap map); //站一起规则

    boolean isEnemy(IMapObject obj, boolean ignore);

    boolean isFriend(IMapObject obj, boolean ignore);

    long getRid();

    int getType(); //各种类型的地图对象
}
