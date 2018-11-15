package io.breeze.model;

/**
 * Created by chenkai on 2018/11/13.
 */
public class Header {

    public static final short MAGIC = (short) 0xbabe;

    /**
     * Message Type
     * @see ProtocolState
     * */
    public static final byte REQUEST                    = 0x01;     // Request
    public static final byte RESPONSE                   = 0x02;     // Response
    public static final byte PUBLISH_SERVICE            = 0x03;     // 发布服务
    public static final byte PUBLISH_CANCEL_SERVICE     = 0x04;     // 取消发布服务
    public static final byte SUBSCRIBE_SERVICE          = 0x05;     // 订阅服务

}
