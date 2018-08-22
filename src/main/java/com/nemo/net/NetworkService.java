package com.nemo.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class NetworkService {
    private static final Logger LOGGER = LoggerFactory.getLogger(NetworkService.class);
    private int bossLoopGroupCount;
    private int workerLoopGroupCount;
    private int port;
    private ServerBootstrap bootstrap;
    private int state;
    NioEventLoopGroup bossGroup;
    NioEventLoopGroup workerGroup;
    private static final byte STATE_STOP = 0;
    private static final byte STATE_START = 1;

    NetworkService() {






    }

    public void start() {
        try {
            ChannelFuture f = this.bootstrap.bind(this.port);
            f.sync();
        } catch (Exception var2) {
            throw new RuntimeException(var2);
        }

        this.state = STATE_START;
        LOGGER.info("server on port: {} is start", this.port);
    }

    public void stop() {
        this.state = STATE_STOP;
        Future<?> bf = this.bossGroup.shutdownGracefully();
        Future<?> wf = this.workerGroup.shutdownGracefully();

        try {
            bf.get(5000L, TimeUnit.MILLISECONDS);
            wf.get(5000L, TimeUnit.MILLISECONDS);
        } catch (Exception var4) {
            LOGGER.info("Netty服务器关闭失败", var4);
        }
        LOGGER.info("Netty Server on port:{} is closed", this.port);
    }

    public boolean isRunning() {
        return state == STATE_START;
    }
}
