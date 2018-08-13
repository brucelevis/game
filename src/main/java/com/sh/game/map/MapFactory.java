package com.sh.game.map;

import com.sh.game.GameContext;
import com.sh.game.config.model.MapConfig;
import com.sh.game.map.play.constant.PlayConst.MapType;
import com.sh.game.map.scene.GameMap;
import com.sh.game.map.scene.Topography;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MapFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(MapFactory.class);

    public static GameMap createMap(MapConfig mapConfig, int line){
        //配置地形的文件路径
        String binaryFile = GameContext.getOption().getConfigDataPath() + "map/" + mapConfig.getId() + "/mapdata.list";

        GameMap map = null;
        try {
            Topography topography = new Topography(binaryFile, mapConfig.getWidth(), mapConfig.getHeight());
            MapType mapType = MapType.parse(mapConfig.getMapType());
            switch (mapType) {
                case SHENYU_BOSS:
                case SHENLONGDIANTANG_BOSS:
//                    map = new ShengyuBossMap(topography);
                    break;
                case SECRET_BOSS:
                case OUT_BOSS:
//                    map = new WorldBossMap(topography);
                    break;
                default:
                    map = new GameMap(topography);
                    break;
            }



        } catch (Exception ex) {
            LOGGER.error("地图{}：{}创建失败", mapConfig.getId(), mapConfig.getName());
        }
        return map;
    }






}
