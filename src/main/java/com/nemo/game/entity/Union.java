package com.nemo.game.entity;

import com.nemo.common.persist.Persistable;
import com.nemo.game.data.DataType;
import io.protostuff.Exclude;
import io.protostuff.Tag;
import lombok.Data;

//帮会数据
@Data
public class Union implements Persistable{
    @Exclude
    private boolean dirty;

    //使用 IDUtil.getId生成一个唯一id
    @Tag(1)
    private long id;

    @Override
    public boolean isDirty() {
        return dirty;
    }

    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public int dataType() {
        return DataType.SYS_UNION;
    }

    @Override
    public long getId() {
        return id;
    }

}
