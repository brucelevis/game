package com.nemo.game.data.mysql.factory;

import com.nemo.common.persist.PersistFactory;
import com.nemo.common.persist.Persistable;
import com.nemo.game.data.DataType;
import com.nemo.game.entity.Rank;

//排行榜数据持久化工厂
public class RankPersistFactory implements PersistFactory {
	private static final String INSERT = "insert into s_rank (id, name, allFightPower, allLevel, achievement, vitality, time_fp, time_lv, time_ac, time_vt) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE = "update s_rank set allFightPower = ? , allLevel = ? , achievement = ? , vitality = ? , time_fp = ?, time_lv = ?, time_ac = ?, time_vt = ? where id = ?";

	private static final String DELETE = "delete from s_rank where id = ?";

	@Override
	public String name() {
		return null;
	}

	@Override
	public int dataType() {
		return DataType.RANK;
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
		Rank rank = (Rank) obj;
		return new Object[]{ rank.getId(), rank.getName(), rank.getAllFightPower(), rank.getAllLevel(), rank.getAchievement(), rank.getVitality(), rank.getTimeFp(), rank.getTimeLv(), rank.getTimeAc(), rank.getTimeVt() };
	}

	@Override
	public Object[] createUpdateParameters(Persistable obj) {
		Rank rank = (Rank) obj;
		return new Object[]{ rank.getAllFightPower(), rank.getAllLevel(), rank.getAchievement(), rank.getVitality(), rank.getTimeFp(), rank.getTimeLv(), rank.getTimeAc(), rank.getTimeVt(), rank.getId() };
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
