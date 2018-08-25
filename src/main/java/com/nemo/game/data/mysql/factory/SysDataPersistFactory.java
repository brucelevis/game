package com.nemo.game.data.mysql.factory;

import com.nemo.common.jdbc.SerializerUtil;
import com.nemo.common.persist.PersistFactory;
import com.nemo.common.persist.Persistable;
import com.nemo.game.data.DataType;
import com.nemo.game.entity.sys.SysData;

//系统数据持久化工厂
public class SysDataPersistFactory implements PersistFactory {
	private static final String INSERT = "insert into s_data (id, data) values (?, ?)";

	private static final String UPDATE = "update s_data set data = ? where id = ?";

	private static final String DELETE = "delete from s_data where id = ?";

	@Override
	public String name() {
		return null;
	}

	@Override
	public int dataType() {
		return DataType.SYS;
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
		Class<SysData> clazz = (Class<SysData>) obj.getClass();
		SysData sysData = (SysData) obj;
		byte[] bytes = SerializerUtil.encode(sysData, clazz);
		return new Object[]{obj.getId(), bytes};
	}

	@Override
	public Object[] createUpdateParameters(Persistable obj) {
		SysData sysData = (SysData) obj;
		Class<SysData> clazz = (Class<SysData>) obj.getClass();
		byte[] bytes = SerializerUtil.encode(sysData, clazz);
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
