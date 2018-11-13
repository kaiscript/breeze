package io.breeze.model;

/**
 * Created by chenkai on 2018/11/13.
 */
public class Message {

    private byte type;
    private byte status;
    private int length;
    private long reqId;
    private Object body;

    public Message(byte type, byte status, int length, long reqId, Object body) {
        this.type = type;
        this.status = status;
        this.length = length;
        this.reqId = reqId;
        this.body = body;
    }

    public byte getType() {
        return type;
    }

    public Message setType(byte type) {
        this.type = type;
        return this;
    }

    public byte getStatus() {
        return status;
    }

    public Message setStatus(byte status) {
        this.status = status;
        return this;
    }

    public int getLength() {
        return length;
    }

    public Message setLength(int length) {
        this.length = length;
        return this;
    }

    public long getReqId() {
        return reqId;
    }

    public Message setReqId(long reqId) {
        this.reqId = reqId;
        return this;
    }

    public Object getBody() {
        return body;
    }

    public Message setBody(Object body) {
        this.body = body;
        return this;
    }

}
