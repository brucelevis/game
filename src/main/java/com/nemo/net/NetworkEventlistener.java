package com.nemo.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;

//在netty的各个事件方法中去调用触发
public interface NetworkEventlistener {
    void onConnected(ChannelHandlerContext var1);

    void onDisconnected(ChannelHandlerContext var1);

    void onExceptionOccur(ChannelHandlerContext var1, Throwable var2);

    void idle(ChannelHandlerContext var1, IdleState var2);
}
