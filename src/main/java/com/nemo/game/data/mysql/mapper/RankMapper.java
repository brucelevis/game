package com.nemo.game.data.mysql.mapper;

import com.nemo.common.jdbc.RowMapper;
import com.nemo.game.entity.Rank;
import com.nemo.game.system.rank.constant.RankField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RankMapper implements RowMapper<Rank>{
	@Override
	public Rank mapping(ResultSet rs) throws SQLException {
        Rank rank = new Rank();
        rank.setId(rs.getLong(RankField.id));
        rank.setName(rs.getString(RankField.name));
        rank.setAllFightPower(rs.getInt(RankField.allFightPower));
        rank.setAllLevel(rs.getInt(RankField.allLevel));
        rank.setAchievement(rs.getInt(RankField.achievement));
        rank.setVitality(rs.getInt(RankField.vitality));
        rank.setTimeFp(rs.getInt(RankField.time_fp));
        rank.setTimeLv(rs.getInt(RankField.time_lv));
        rank.setTimeAc(rs.getInt(RankField.time_ac));
        rank.setTimeVt(rs.getInt(RankField.time_vt));
        return rank;
	}

	@Override
	public void release() {
	}
}
