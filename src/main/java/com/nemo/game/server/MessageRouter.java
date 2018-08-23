package com.nemo.game.server;

import com.nemo.net.Message;
import com.nemo.net.NetworkConsumer;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

//MessageExecutor将Message传给该类去处理
public class MessageRouter implements NetworkConsumer{
    private static Logger LOGGER = LoggerFactory.getLogger(MessageRouter.class);

    private Map<Integer, MessageProcessor> processors = new HashMap<>();

    public MessageRouter() {



    }

    public void registerProcessor(int queueId, MessageProcessor consumer) {
        processors.put(queueId, consumer);
    }

    @Override
    public void consume(Channel var1, Message msg) {
        //将消息分发到指定的队列中，改对垒可能在同一个线程，也可能不同一个线程


    }

    public MessageProcessor getProcessor(int queueId) {
        return processors.get(queueId);
    }
}
