package com.nemo.game.server;

import com.nemo.concurrent.IQueueDriverCommand;

public interface MessageProcessor {
    void process(IQueueDriverCommand message);

    default void process(IQueueDriverCommand message, long id) {

    }
}
