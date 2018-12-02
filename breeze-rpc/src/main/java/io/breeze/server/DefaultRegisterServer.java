package io.breeze.server;

import io.breeze.model.HeaderConstant;
import io.breeze.model.Message;
import io.breeze.model.MessageHeader;
import io.breeze.model.ProtocolState;
import io.breeze.serialization.FSTSerializer;
import io.breeze.serialization.SerializerFactory;
import io.breeze.transport.Acceptor;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.ReplayingDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 默认注册中心实现
 * Created by chenkai on 2018/11/15.
 */
public class DefaultRegisterServer extends Acceptor {

    private static final Logger logger = LoggerFactory.getLogger(DefaultRegisterServer.class);

    FSTSerializer fstSerializer = SerializerFactory.getFSTSerializer();

    private MessageHandler messageHandler = new MessageHandler();

    public DefaultRegisterServer(int port) {
        this.localAddress = new InetSocketAddress(port);
    }

    public static DefaultRegisterServer createRegisterServer(int port) {
        DefaultRegisterServer server = new DefaultRegisterServer(port);
        server.init();
        return server;
    }

    public void startRegisterServer() {
        try {
            start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ChannelFuture bind() {
        logger.info("DefaultRegisterServer bind at :{}", localAddress);
        ServerBootstrap serverBootstrap = bootstrap();

        initChannel();
        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast("decoder",new MessageDecoder())
                        .addLast("encoder",new MessageEncoder())
                        .addLast(messageHandler);
            }
        }).option(ChannelOption.SO_BACKLOG, 128)
        .childOption(ChannelOption.SO_KEEPALIVE, true);
        return serverBootstrap.bind(localAddress);
    }

    //set channel or channelFactory
    public void initChannel() {
        //or channelFactory
        bootstrap().channel(NioServerSocketChannel.class);
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

    @ChannelHandler.Sharable
    class MessageHandler extends ChannelInboundHandlerAdapter{
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            logger.info("defaultRegisterServer receive:{}", msg);
            super.channelRead(ctx, msg);
        }
    }

}
