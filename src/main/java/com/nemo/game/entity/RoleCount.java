package com.nemo.game.entity;

import com.nemo.common.persist.Persistable;
import com.nemo.game.data.DataType;
import io.protostuff.Exclude;
import io.protostuff.Tag;
import lombok.Data;

@Data
public class RoleCount implements Persistable {
    @Exclude
    private boolean dirty = false;

    @Tag(1)
    private long id;

    @Override
    public long getId() {
        return id;
    }

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
        return DataType.COUNT;
    }
}
