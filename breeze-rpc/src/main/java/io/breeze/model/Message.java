package io.breeze.model;

/**
 * Created by chenkai on 2018/11/13.
 */
public class Message {

    /**
     * 标识Message类型，标识注册订阅
     */
    private byte type;
    private long reqId;
    private Object body;

    public Message(byte type, long reqId, Object body) {
        this.type = type;
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
