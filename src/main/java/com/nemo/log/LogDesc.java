package com.nemo.log;

import com.nemo.log.consumer.LogConsumer;

import java.util.ArrayList;
import java.util.List;

public class LogDesc {
    private List<LogConsumer> consumers = new ArrayList();

    public LogDesc() {
    }

    public List<LogConsumer> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<LogConsumer> consumers) {
        this.consumers = consumers;
    }
}
