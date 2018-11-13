package io.breeze.registry;


import io.breeze.model.Message;
import io.breeze.model.ProtocolState;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Created by chenkai on 2018/11/13.
 */
public class Registry {

    /**
     * Message编码器
     */
    public class MessageEncoder extends MessageToByteEncoder<Message>{

        @Override
        protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {

        }

    }

    /**
     * Message解码器
     */
    public class MessageDecoder extends ReplayingDecoder<ProtocolState> {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        }

    }

}
