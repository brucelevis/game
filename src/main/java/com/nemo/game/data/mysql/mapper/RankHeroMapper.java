package com.nemo.game.data.mysql.mapper;

import com.nemo.common.jdbc.RowMapper;
import com.nemo.game.entity.RankHero;
import com.nemo.game.system.rank.constant.RankHeroField;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RankHeroMapper implements RowMapper<RankHero>{
	@Override
	public RankHero mapping(ResultSet rs) throws SQLException {
        RankHero rank = new RankHero();
        rank.setId(rs.getLong(RankHeroField.id));
        rank.setRid(rs.getLong(RankHeroField.rid));
        rank.setName(rs.getString(RankHeroField.name));
        rank.setCareer(rs.getInt(RankHeroField.career));
        rank.setFightPower(rs.getInt(RankHeroField.fightPower));
        rank.setLv(rs.getInt(RankHeroField.lv));
        rank.setTime(rs.getInt(RankHeroField.time));
        return rank;
	}

	@Override
	public void release() {
	}
}
