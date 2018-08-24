package com.nemo.net.kryo;

import java.io.IOException;
import java.io.OutputStream;

public class KryoOutput extends OutputStream{
    private int maxCapacity;
    private int capacity;
    private int position;
    private int total;
    private byte[] buffer;
    private OutputStream outputStream;

    public KryoOutput() {
    }

    public KryoOutput(int bufferSize) {
        this(bufferSize, bufferSize);
    }

    public KryoOutput(int bufferSize, int maxBufferSize) {
        if (maxBufferSize < -1) {
            throw new IllegalArgumentException("maxBufferSize cannot be < -1: " + maxBufferSize);
        } else {
            this.capacity = bufferSize;
            this.maxCapacity = maxBufferSize == -1 ? 2147483647 : maxBufferSize;
            this.buffer = new byte[bufferSize];
        }
    }

    public int position() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void clear() {
        this.position = 0;
        this.total = 0;
    }

    public byte[] toBytesAndClear() {
        byte[] newBuffer = new byte[this.position];
        System.arraycopy(this.buffer, 0, newBuffer, 0, this.position);
        this.position = 0;
        this.total = 0;
        return newBuffer;
    }

    @Override
    public void write(int b) throws IOException {

    }

    @Override
    public void write(byte[] b) throws IOException {
        super.write(b);
    }
}
