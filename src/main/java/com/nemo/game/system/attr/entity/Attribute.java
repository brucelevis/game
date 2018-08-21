package com.nemo.game.system.attr.entity;

import io.protostuff.Tag;
import lombok.extern.slf4j.Slf4j;

//玩家属性
@Slf4j
public class Attribute implements Cloneable{
    //生命值
    @Tag(1)
    protected int hp;
    //魔法值
    @Tag(2)
    protected int mp;
    //物攻
    @Tag(3)
    private int phyatk;
    //物攻上限
    @Tag(4)
    private int phyatkmax;






}
