package com.nemo.game.system.user.msg;

import com.sh.game.server.AbstractMessage;
import com.sh.game.system.user.UserManager;
import com.sh.net.kryo.KryoInput;
import com.sh.net.kryo.KryoOutput;


/**
 * 心跳请求
 */
public class ReqHeartMessage extends AbstractMessage {

	@Override
	public void doAction() {
		UserManager.getInstance().clientHeart(session);
	}
	
	public ReqHeartMessage() {
		this.queueId = 1;
	}
	
	@Override
	public int getId() {
		return 1009;
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

