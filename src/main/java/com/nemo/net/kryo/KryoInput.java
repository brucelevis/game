package com.nemo.net.kryo;

import java.io.IOException;
import java.io.InputStream;

public class KryoInput extends InputStream{
    private byte[] buffer;
    private int capacity;
    private int position;
    private int limit;
    private int total;
    private char[] chars;
    private InputStream inputStream;

    public KryoInput() {
        this.chars = new char[32];
    }

    public KryoInput(int bufferSize) {
        this.chars = new char[32];
        this.capacity = bufferSize;
        this.buffer = new byte[bufferSize];
    }

    public KryoInput(byte[] buffer, int offset, int count) {
        this.chars = new char[32];
        this.setBuffer(buffer, offset, count);
    }

    public KryoInput(InputStream inputStream) {
        this(4096);
        if(inputStream == null) {
            throw new IllegalArgumentException("inputStream cannot be null.");
        } else {
            this.inputStream = inputStream;
        }
    }

    public KryoInput(InputStream inputStream, int bufferSize) {
        this(bufferSize);
        if(inputStream == null) {
            throw new IllegalArgumentException("inputStream cannot be null.");
        } else {
            this.inputStream = inputStream;
        }
    }

    //AbstractMessage里设置
    public void setBuffer(byte[] buffer) {
        this.setBuffer(buffer, 0, buffer.length);
    }

    public void setBuffer(byte[] bytes, int offset, int count) {
        if(bytes == null) {
            throw new IllegalArgumentException("bytes cannot be null");
        } else {
            this.buffer = bytes;
            this.position = offset;
            this.limit = count;
            this.capacity = bytes.length;
            this.total = 0;
            this.inputStream = null;
        }
    }

    public byte[] getBuffer() {
        return buffer;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
        this.limit = 0;
        this.rewind();
    }

    public int total() {
        return this.total + this.position;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int position() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int limit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public void rewind() {
        this.position = 0;
        this.total = 0;
    }

    public void skip(int count) throws KryoException{
        int skipCount = Math.min(this.limit - this.position, count);

        while (true) {
            this.position += skipCount;
            count -= skipCount;

        }
    }






    @Override
    public int read() throws IOException {
        return 0;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return super.read(b);
    }
}
