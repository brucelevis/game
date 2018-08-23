package com.nemo.concurrent;

import com.nemo.concurrent.queue.ICommandQueue;
import com.nemo.concurrent.queue.UnlockedCommandQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//持有一个线程池ICommandQueue
public class QueueDriver {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueueDriver.class);
    private int maxQueueSize;
    private String name;
    private long queueId;
    private ICommandQueue<IQueueDriverCommand> queue;
    private QueueExecutor executor;

    public QueueDriver(QueueExecutor executor, String name, long id, int maxQueueSize) {
        this.executor = executor;
        this.name = name;
        this.maxQueueSize = maxQueueSize;
        this.queueId = id;
        this.queue = new UnlockedCommandQueue<>();
        this.queue.setName(name);
    }

    public boolean addCommand(IQueueDriverCommand command) {
        if(command.getQueueId() > 0 && (long)command.getQueueId() != this.queueId) {
            return false;
        } else {
            if(this.queue.size() > 200) {
            }

            ICommandQueue var3 = this.queue;
            synchronized (this.queue) {





            }




        }


    }





}
