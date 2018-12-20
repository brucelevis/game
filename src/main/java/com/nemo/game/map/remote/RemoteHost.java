package com.nemo.game.map.remote;

import com.nemo.client.Client;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;

@Setter
@Getter
@Slf4j
public class RemoteHost {

    //远程主机id
    private int id;
    //ip
    private String host;
    //端口
    private int port;
    //远程主机客户端
    private Client client;
    //是否已登录
    private boolean login;
    //是否server端的client 不是真的netty客户端 仅保存对应host的一组channel
    private boolean serverSide;
    //开服时间
    private long openTime;
    //合服时间
    private long combineTime;

    public RemoteHost() {

    }



    //获取开服天数，开服首日算作第一天
    public int getOpenServerDay() {
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.openTime), ZoneId.systemDefault());
        return (int) (LocalDateTime.now().getLong(ChronoField.EPOCH_DAY) - ldt.getLong(ChronoField.EPOCH_DAY) + 1);
    }

}
