package com.nemo.game.entity;

import com.nemo.common.persist.Persistable;
import com.nemo.game.data.DataType;
import lombok.Data;

@Data
public class RankHero implements Persistable{
    private boolean dirty = false;
    private long id;
    private long rid;
    private String name;
    private int career;
    private int fightPower;
    private int lv;
    private int time;

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
        return DataType.RANK_HERO;
    }
}
