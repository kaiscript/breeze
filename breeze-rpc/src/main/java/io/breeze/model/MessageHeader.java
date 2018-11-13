package io.breeze.model;

/**
 * Created by kaiscript on 2018/11/14.
 */
public class MessageHeader {

    private byte type;
    private byte status;
    private int length;
    private long reqId;

    public byte getType() {
        return type;
    }

    public MessageHeader setType(byte type) {
        this.type = type;
        return this;
    }

    public byte getStatus() {
        return status;
    }

    public MessageHeader setStatus(byte status) {
        this.status = status;
        return this;
    }

    public int getLength() {
        return length;
    }

    public MessageHeader setLength(int length) {
        this.length = length;
        return this;
    }

    public long getReqId() {
        return reqId;
    }

    public MessageHeader setReqId(long reqId) {
        this.reqId = reqId;
        return this;
    }
}
