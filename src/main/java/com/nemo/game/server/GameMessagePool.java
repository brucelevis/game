package com.nemo.game.server;

import com.nemo.concurrent.AbstractCommand;
import com.nemo.game.constant.GameConst;
import com.nemo.game.map.obj.MapObject;
import com.nemo.net.Message;
import com.nemo.net.MessagePool;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GameMessagePool implements MessagePool{
    private Map<Integer, Class<? extends Message>> pool = new HashMap<>();
    private Map<Integer, Integer> queueIdMap = new HashMap<>();

    @Override
    public Message get(int messageId) {
        Class<? extends Message> clazz = pool.get(messageId);
        if(clazz != null) {
            int queueId = queueIdMap.get(messageId);
            try {
                Message msg = clazz.getDeclaredConstructor().newInstance();
                msg.setQueueId(queueId); //设置第几条线程去执行

                log.debug("msg get msg name {}({})", msg.getClass().getSimpleName(), messageId);
                return msg;
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    private void register(int messageId, Class<? extends Message> clazz) {
        pool.put(messageId, clazz);
        //默认走玩家主逻辑队列
        queueIdMap.put(messageId, GameConst.QueueId.TWO_PLAYER);
    }

    private void register(int messageId, Class<? extends Message> clazz, int queueId) {
        pool.put(messageId, clazz);
        queueIdMap.put(messageId, queueId);
    }




}
