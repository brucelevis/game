package com.nemo.game.data.mysql.mapper;

import com.nemo.common.jdbc.RowMapper;
import com.nemo.game.entity.User;
import com.nemo.game.system.user.field.UserField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User>{
	@Override
	public User mapping(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(UserField.id));
        user.setLoginName(rs.getString(UserField.loginName));
        user.setSid(rs.getInt(UserField.sid));
        user.setPid(rs.getInt(UserField.pid));
        user.setType(rs.getInt(UserField.type));
        user.setIDNumber(rs.getString(UserField.IDNumber));
        user.setClient(rs.getInt(UserField.client));
        user.setRegTime(rs.getInt(UserField.regTime));
        user.setQudao(rs.getInt(UserField.QUDAO));
        return user;
	}

	@Override
	public void release() {
	}
}
