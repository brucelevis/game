package com.nemo.net.ws;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;

import java.util.List;

public class WebSocketDecoder extends MessageToMessageDecoder<BinaryWebSocketFrame>{
    public WebSocketDecoder(){
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, BinaryWebSocketFrame msg, List<Object> out) throws Exception {
        out.add(msg.content().retain());
    }
}
