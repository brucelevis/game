package com.nemo.game.entity;

import io.protostuff.Tag;
import lombok.Data;

@Data
public class Hero {
    @Tag(1)
    private long id;
    //对应role id
    @Tag(2)
    private long ownerId;
    //英雄编号
    @Tag(3)
    private int number;
    //名字
    @Tag(4)
    private String name;
    //1男2女
    @Tag(5)
    private int sex;
    //职业
    @Tag(6)
    private int career;
    //等级
    @Tag(7)
    private int level = 1;



}
