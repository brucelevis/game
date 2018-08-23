package com.nemo.game.server;

import com.nemo.concurrent.IQueueDriverCommand;

//消息处理器 将消息交给指定线程去处理
public interface MessageProcessor {
    void process(IQueueDriverCommand message);

    default void process(IQueueDriverCommand message, long id) {

    }
}
