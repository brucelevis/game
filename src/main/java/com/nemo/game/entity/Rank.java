package com.nemo.game.entity;

import com.nemo.common.persist.Persistable;
import com.nemo.game.data.DataType;
import lombok.Data;

//排行
@Data
public class Rank implements Persistable{
    private boolean dirty = false;
    private long id;
    private String name;
    private int allFightPower;
    private int allLevel;
    private int achievement;
    private int vitality;
    private int pk;
    private int timeFp;
    private int timeLv;
    private int timeAc;
    private int timeVt;
    private int timePk;

    @Override
    public boolean isDirty() {
        return dirty;
    }

    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public int dataType() {
        return DataType.RANK;
    }
}
