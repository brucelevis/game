package com.nemo.net.ws;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.List;

public class WebSocketEncoder extends MessageToMessageEncoder<ByteBuf>{
    public WebSocketEncoder(){
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        out.add((new BinaryWebSocketFrame(msg)).retain());
    }
}
