package com.nemo.game.data.mysql.factory;

import com.nemo.common.jdbc.SerializerUtil;
import com.nemo.common.persist.PersistFactory;
import com.nemo.common.persist.Persistable;
import com.nemo.game.data.DataType;
import com.nemo.game.entity.RoleMail;

/** 
 * 背包 数据持久化工具
 * @author zhangli
 * 2017年6月6日 下午9:30:40   
 */
public class RoleMailPersistFactory implements PersistFactory {

	private static final String INSERT = "insert into p_mail (id, data) values (?, ?)";

	private static final String UPDATE = "update p_mail set data = ? where id = ?";

	private static final String DELETE = "delete from p_mail where id = ?";

	@Override
	public String name() {
		return null;
	}
	
	

	@Override
	public int dataType() {
		return DataType.ROLE_MAIL;
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
		RoleMail role = (RoleMail) obj;
		byte[] bytes = SerializerUtil.encode(role, RoleMail.class);
		return new Object[]{ obj.getId(), bytes };
	}

	@Override
	public Object[] createUpdateParameters(Persistable obj) {
		RoleMail role = (RoleMail) obj;
		byte[] bytes = SerializerUtil.encode(role, RoleMail.class);
		return new Object[]{ bytes, obj.getId() };
	}

	@Override
	public Object[] createDeleteParameters(Persistable obj) {
		return new Object[]{ obj.getId() };
	}

	@Override
	public long taskPeriod() {
		return 60 * 1000;
	}

}
