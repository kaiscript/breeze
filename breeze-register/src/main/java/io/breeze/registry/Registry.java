package io.breeze.registry;


import io.breeze.model.Header;
import io.breeze.model.Message;
import io.breeze.model.MessageHeader;
import io.breeze.model.ProtocolState;
import io.breeze.serialization.FSTSerializer;
import io.breeze.serialization.SerializerFactory;
import io.breeze.transport.connector.Connector;
import io.breeze.transport.connector.UnresolvedAddress;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Created by chenkai on 2018/11/13.
 */
public class Registry extends Connector {

    private static final Logger logger = LoggerFactory.getLogger(Registry.class);

    FSTSerializer fstSerializer = SerializerFactory.getFSTSerializer();

    /** connect的时候为channel赋值 */
    private Channel channel;

    /**
     * Message编码器
     */
    public class MessageEncoder extends MessageToByteEncoder<Message>{

        @Override
        protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
            out.writeShort(Header.MAGIC)
                    .writeByte(msg.getType())
                    .writeByte(0)
                    .writeLong(0);
            byte[] bytes = fstSerializer.writeObject(msg.getBody());
            out.writeInt(bytes.length);
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
                    out.add(new Message(header.getType(), header.getReqId(), object));
            }
            checkpoint(ProtocolState.MAGIC);
        }

        private void checkMagic(short magic) throws Exception{
            if (Header.MAGIC != magic) {
                throw new Exception("error magic!");
            }
        }

    }

    //todo 后续修改为返回 Connection
    public void connect(UnresolvedAddress address) {
        Bootstrap bootstrap = bootstrap();
        InetSocketAddress inetSocketAddress = InetSocketAddress.createUnresolved(address.getHost(), address.getPort());
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new MessageDecoder());
                ch.pipeline().addLast(new MessageEncoder());
            }
        });

        try {
            ChannelFuture future = bootstrap.connect(inetSocketAddress).sync();
            channel = future.channel();
        } catch (Exception e) {
            logger.error("bootstrap connect e:", e);
        }

        testClientSend();

    }

    public void testClientSend() {
        RegisterMeta registerMeta = new RegisterMeta();
        RegisterMeta.ServiceMeta serviceMeta = new RegisterMeta.ServiceMeta();
        serviceMeta.serviceName = "testService";
        registerMeta.setServiceMeta(serviceMeta);
        Message message = new Message((byte) Header.MAGIC, Header.MAGIC, registerMeta);
        channel.pipeline().writeAndFlush(message);
    }

}
