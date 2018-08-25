package com.nemo.game.data.mysql.factory;

import com.nemo.common.persist.PersistFactory;
import com.nemo.common.persist.Persistable;
import com.nemo.game.data.DataType;
import com.nemo.game.entity.RankHero;

//战力数据持久化工厂
public class RankHeroPersistFactory implements PersistFactory {
	private static final String INSERT = "insert into s_rank_hero (id, rid, name, career, fightPower, lv, `time` ) values (?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE = "update s_rank_hero set fightPower = ? , lv = ? , `time` = ? where id = ?";

	private static final String DELETE = "delete from s_rank_hero where id = ?";

	@Override
	public String name() {
		return null;
	}

	@Override
	public int dataType() {
		return DataType.RANK_HERO;
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
		RankHero rankHero = (RankHero) obj;
		return new Object[]{rankHero.getId(), rankHero.getRid(), rankHero.getName(), rankHero.getCareer(),
				rankHero.getFightPower(), rankHero.getLv(), rankHero.getTime()};
	}

	@Override
	public Object[] createUpdateParameters(Persistable obj) {
		RankHero rankHero = (RankHero) obj;
		return new Object[]{rankHero.getFightPower(), rankHero.getLv(), rankHero.getTime(), rankHero.getId()};
	}

	@Override
	public Object[] createDeleteParameters(Persistable obj) {
		return new Object[]{obj.getId()};
	}

	@Override
	public long taskPeriod() {
		return 60 * 100;
	}
}
