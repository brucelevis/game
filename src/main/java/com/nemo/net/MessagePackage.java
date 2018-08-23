package com.nemo.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class MessagePackage {
    public MessagePackage(){
    }

    public static ByteBuf packageMsg(Message msg) {
        byte[] content = msg.encode();
        int length = 10 + content.length;
        msg.setLength(length);
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(length);
        buffer.writeInt(length);
        buffer.writeInt(msg.getId());
        buffer.writeShort(msg.getSequence());
        buffer.writeBytes(content);
        return buffer;
    }

    public static ByteBuf packageMsgGroup(Message msg) {
        byte[] content = msg.encode();
        int length = content.length;
        msg.setLength(length);
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer(length);
        buffer.writeBytes(content);
        return buffer;
    }
}
