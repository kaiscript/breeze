package io.breeze.server;

import io.breeze.model.HeaderConstant;
import io.breeze.model.Message;
import io.breeze.model.MessageHeader;
import io.breeze.model.ProtocolState;
import io.breeze.serialization.FSTSerializer;
import io.breeze.serialization.SerializerFactory;
import io.breeze.transport.connector.Acceptor;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.net.SocketAddress;
import java.util.List;

/**
 * 默认注册中心实现
 * Created by chenkai on 2018/11/15.
 */
public class DefaultRegisterServer extends Acceptor {

    FSTSerializer fstSerializer = SerializerFactory.getFSTSerializer();

    public DefaultRegisterServer(SocketAddress localAddress) {
        super(localAddress);
    }

    public void startRegisterServer() {
        try {
            start();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ChannelFuture bind() {
        ServerBootstrap serverBootstrap = bootstrap();

        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new MessageDecoder())
                        .addLast(new MessageEncoder());
            }
        });
        return serverBootstrap.bind(localAddress);
    }

    /**
     * Server端 Message编码器
     */
    public class MessageEncoder extends MessageToByteEncoder<Message> {

        @Override
        protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
            out.writeShort(HeaderConstant.MAGIC)
                    .writeByte(msg.getType())
                    .writeByte(0)
                    .writeLong(0);
            byte[] bytes = fstSerializer.writeObject(msg.getBody());
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        }

    }

    /**
     * Server端 Message解码器
     */
    public class MessageDecoder extends ReplayingDecoder<ProtocolState>{

        public MessageHeader header = new MessageHeader();

        public MessageDecoder() {
            super(ProtocolState.MAGIC);
        }

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            switch (state()){
                case MAGIC:
                    checkMagic(in.readShort());
                    checkpoint(ProtocolState.TYPE);
                case TYPE:
                    header.setType(in.readByte());
                    checkpoint(ProtocolState.STATUS);
                case STATUS:
                    header.setStatus(in.readByte());
                    checkpoint(ProtocolState.ID);
                case ID:
                    header.setReqId(in.readLong());
                    checkpoint(ProtocolState.BODY_SIZE);
                case BODY_SIZE:
                    header.setLength(in.readInt());
                    checkpoint(ProtocolState.BODY);
                case BODY:
                    byte[] bytes = new byte[header.getLength()];
                    in.readBytes(bytes);
                    Object object = fstSerializer.readObject(bytes, Object.class);
                    out.add(new Message(header.getType(), header.getReqId(), object));

            }
        }

        private void checkMagic(short magic) throws Exception{
            if (HeaderConstant.MAGIC != magic) {
                throw new Exception("error magic!");
            }
        }

    }



}
