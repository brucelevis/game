package com.nemo.game.map.msg;

import com.sh.game.map.MapManager;
import com.sh.game.server.AbstractMessage;
import com.sh.net.kryo.KryoInput;
import com.sh.net.kryo.KryoOutput;


/**
 * <p>玩家登录地图</p>
 * <p>Created by MessageUtil</p>
 *
 * @author : admin
 */

public class ReqLoginMapMessage extends AbstractMessage {

	@Override
	public void doAction() {
		MapManager.getInstance().loginMap(this.session.getRole().getId());
	}
	
	public ReqLoginMapMessage() {
		this.queueId = 0;
	}
	
	@Override
	public int getId() {
		return 67012;
	}
	


	@Override
	public boolean read(KryoInput buf) {


		return true;
	}

	@Override
	public boolean write(KryoOutput buf) {


		return true;
	}
}
