package com.nemo.game.server;

import com.nemo.concurrent.IQueueDriverCommand;
import com.nemo.concurrent.queue.ICommandQueue;
import com.nemo.net.Message;
import com.nemo.net.kryo.KryoBean;
import com.nemo.net.kryo.KryoInput;
import com.nemo.net.kryo.KryoOutput;
import com.nemo.net.kryo.KryoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//抽象消息，实现了Message的一些方法
public abstract class AbstractMessage extends KryoBean implements Message {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractMessage.class);
    //命令队列
    private ICommandQueue<IQueueDriverCommand> commandQueue;
    //消息长度
    private int length;
    //一个额外的参数 用于绑定用户
    protected Session session;
    //队列id
    protected int queueId;
    //过滤器
    protected MessageFilter filter;

    protected short sequence;

    @Override
    public short getSequence() {
        return sequence;
    }

    @Override
    public void setSequence(short sequence) {
        this.sequence = sequence;
    }

    @Override
    public ICommandQueue<IQueueDriverCommand> getCommandQueue() {
        return commandQueue;
    }

    @Override
    public void setCommandQueue(ICommandQueue<IQueueDriverCommand> commandQueue) {
        this.commandQueue = commandQueue;
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public Session getParam() {
        return session;
    }

    @Override
    public void setParam(Object param) {
        this.session = (Session)param;
    }

    @Override
    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    @Override
    public int getQueueId() {
        return queueId;
    }

    public String toString(){
        return "[id->"+getId()+",sequence->"+sequence+"]";
    }

    @Override
    public void decode(byte[] bytes) {
        KryoInput input = KryoUtil.getInput();
        input.setBuffer(bytes);
        //推迟到各个子类中实现 将子类的定义字段解码
        read(input);
    }

    @Override
    public byte[] encode() {
        KryoOutput output = KryoUtil.getOutput();
        //推迟到各个子类中实现 将子类的定义字段编码
        write(output);
        return output.toBytesAndClear();
    }

    @Override
    public void run() {
        try {
            //过滤器
            if(filter != null && !filter.before(this)){
                //...
                return;
            }
            doAction();
            if(filter != null && !filter.after(this)){
                //...
            }
        } catch (Throwable e) {
            LOGGER.error("命令执行错误", e);
        }
    }
}
