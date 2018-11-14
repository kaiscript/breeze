package io.breeze.registry;


import io.breeze.model.Message;
import io.breeze.model.MessageHeader;
import io.breeze.model.ProtocolState;
import io.breeze.serialization.FSTSerializer;
import io.breeze.serialization.SerializerFactory;
import io.breeze.transport.connector.Connector;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * Created by chenkai on 2018/11/13.
 */
public class Registry extends Connector {

    private static final short MAGIC = (short) 0xbabe;

    FSTSerializer fstSerializer = SerializerFactory.getFSTSerializer();

    /** connect的时候为channel赋值 */
    private Channel channel;

    /**
     * Message编码器
     */
    public class MessageEncoder extends MessageToByteEncoder<Message>{

        @Override
        protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
            out.writeShort(MAGIC)
            .writeByte(msg.getType())
            .writeByte(msg.getStatus())
            .writeLong(msg.getReqId())
            .writeInt(msg.getLength());
            byte[] bytes = fstSerializer.writeObject(msg.getBody());
            out.writeBytes(bytes);
        }

    }

    /**
     * Message解码器
     */
    public class MessageDecoder extends ReplayingDecoder<ProtocolState> {

        private MessageHeader header = new MessageHeader();

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

            switch (state()){
                case MAGIC:
                    checkMagic(in.readShort());
                    checkpoint(ProtocolState.MAGIC);
                case TYPE:
                    header.setType(in.readByte());
                    checkpoint(ProtocolState.TYPE);
                case STATUS:
                    header.setStatus(in.readByte());
                    checkpoint(ProtocolState.STATUS);
                case ID:
                    header.setReqId(in.readLong());
                    checkpoint(ProtocolState.ID);
                case BODY_SIZE:
                    header.setLength(in.readInt());
                    checkpoint(ProtocolState.BODY_SIZE);
                case BODY:
                    byte[] bytes = new byte[header.getLength()];
                    in.readBytes(bytes);
                    Object object = fstSerializer.readObject(bytes, Object.class);
                    out.add(new Message(header.getType(), header.getStatus(), header.getLength(), header.getReqId(), object));
            }
            checkpoint(ProtocolState.MAGIC);
        }

        private void checkMagic(short magic) throws Exception{
            if (MAGIC != magic) {
                throw new Exception("error magic!");
            }
        }

    }

}
