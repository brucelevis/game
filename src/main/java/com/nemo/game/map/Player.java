package com.nemo.game.map;

import com.google.common.collect.Maps;
import com.nemo.game.map.obj.PlayerActor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@Slf4j
public class Player {
    private long id;

    private String name;
    //是否登录了地图
    private boolean loginMap;
    //所有角色总血量
    private long totalMaxHp;

    private int dieCount;
    //当前主角
    private PlayerActor current;
    //玩家本身Actor
    private PlayerActor main;
    //药品等道具使用情况
    private Map<Integer, Integer> itemUsedMap = Maps.newHashMap();

    private Map<Long, PlayerActor> actorMap = new HashMap<>();

    public byte totalHpPercent() {
        int totalHp = 0;
        for(PlayerActor playerActor : actorMap.values()) {
            if(playerActor.isDead()) {
                continue;
            }
            totalHp += playerActor.getHp();
        }
        if(totalHp <= 0) {
            return -1;
        }
        if(totalMaxHp > 0) {
            byte percent = (byte) Math.floor(totalHp / (double)totalMaxHp * 100);
            return percent;
        } else {
            return 0;
        }
    }

}
