package com.nemo.net.kryo;

public abstract class KryoBean {
    public KryoBean() {
    }

    public abstract boolean write(KryoOutput var1);

    public abstract boolean read(KryoInput var1);
}
