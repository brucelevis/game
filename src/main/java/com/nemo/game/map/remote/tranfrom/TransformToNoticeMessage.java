package com.nemo.game.map.remote.tranfrom;

import com.nemo.game.map.remote.TransformUtil;
import com.nemo.game.server.AbstractMessage;
import com.nemo.net.kryo.KryoInput;
import com.nemo.net.kryo.KryoOutput;


/**
 * notice传输消息
 * @author 张力
 * @date 2018/8/27 18:11
 */
public class TransformToNoticeMessage extends AbstractMessage {

    /**
     * 玩家id列表
     */
    private byte processId;

    private int noticeId;

    private long playerId;

    private byte[] noticeBytes;

    @Override
    public int getId() {
        return 3;
    }

    @Override
    public void doAction() {
        TransformUtil.dispatch(this);
    }

    @Override
    public boolean write(KryoOutput output) {
        writeByte(output, processId);
        writeInt(output,noticeId, false);
        writeLong(output, playerId);
        writeBytes(output,noticeBytes);

        return false;
    }

    @Override
    public boolean read(KryoInput input) {
        this.processId = readByte(input);
        this.noticeId = readInt(input, false);
        this.playerId = readLong(input);
        this.noticeBytes = readBytes(input);

        return true;
    }

    public byte getProcessId() {
        return processId;
    }

    public void setProcessId(byte processId) {
        this.processId = processId;
    }

    public int getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

    public byte[] getNoticeBytes() {
        return noticeBytes;
    }

    public void setNoticeBytes(byte[] noticeBytes) {
        this.noticeBytes = noticeBytes;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}
