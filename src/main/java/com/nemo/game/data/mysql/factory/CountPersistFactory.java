package com.nemo.game.data.mysql.factory;

import com.nemo.common.jdbc.SerializerUtil;
import com.nemo.common.persist.PersistFactory;
import com.nemo.common.persist.Persistable;
import com.nemo.game.data.DataType;
import com.nemo.game.entity.RoleCount;

//玩家计数数据持久化工厂
public class CountPersistFactory implements PersistFactory {
    private static final String INSERT = "insert into p_count (id, data) values (?, ?)";

    private static final String UPDATE = "update p_count set data = ? where id = ?";

    private static final String DELETE = "delete from p_count where id = ?";

    @Override
    public String name() {
        return null;
    }

    @Override
    public int dataType() {
        return DataType.COUNT;
    }

    @Override
    public String createInsertSql() {
        return INSERT;
    }

    @Override
    public String createUpdateSql() {
        return UPDATE;
    }

    @Override
    public String createDeleteSql() {
        return DELETE;
    }

    @Override
    public Object[] createInsertParameters(Persistable obj) {
        RoleCount roleCount = (RoleCount) obj;
        byte[] bytes = SerializerUtil.encode(roleCount, RoleCount.class);
        return new Object[]{obj.getId(), bytes};
    }

    @Override
    public Object[] createUpdateParameters(Persistable obj) {
    	RoleCount roleCount = (RoleCount) obj;
        byte[] bytes = SerializerUtil.encode(roleCount, RoleCount.class);
        return new Object[]{bytes, obj.getId()};
    }

    @Override
    public Object[] createDeleteParameters(Persistable obj) {
        return new Object[]{obj.getId()};
    }

    @Override
    public long taskPeriod() {
        return 60 * 1000;
    }
}
