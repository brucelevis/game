package com.nemo.game.entity.sys;

import com.nemo.common.persist.Persistable;
import com.nemo.game.data.DataType;
import io.protostuff.Exclude;

//系统总数据
public abstract class SysData implements Persistable{




    @Exclude
    private boolean dirty;

    public abstract void setId(long id);

    @Override
    public int dataType() {
        return DataType.SYS;
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
}
