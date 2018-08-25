package com.nemo.game.data.mysql.factory;

import com.nemo.common.jdbc.SerializerUtil;
import com.nemo.common.persist.PersistFactory;
import com.nemo.common.persist.Persistable;
import com.nemo.game.data.DataType;
import com.nemo.game.entity.RoleBag;

//背包 数据持久化工具
public class BagPersistFactory implements PersistFactory {
	private static final String INSERT = "insert into p_bag (id, data) values (?, ?)";

	private static final String UPDATE = "update p_bag set data = ? where id = ?";

	private static final String DELETE = "delete from p_bag where id = ?";

	@Override
	public String name() {
		return null;
	}

	@Override
	public int dataType() {
		return DataType.BAG;
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
		RoleBag roleBag = (RoleBag) obj;
		byte[] bytes = SerializerUtil.encode(roleBag, RoleBag.class);
		return new Object[]{obj.getId(), bytes};
	}

	@Override
	public Object[] createUpdateParameters(Persistable obj) {
		RoleBag roleBag = (RoleBag) obj;
		byte[] bytes = SerializerUtil.encode(roleBag, RoleBag.class);
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
