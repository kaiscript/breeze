package io.breeze.server;

import io.breeze.model.Header;
import io.breeze.model.Message;
import io.breeze.model.ProtocolState;
import io.breeze.registry.RegistryService;
import io.breeze.serialization.FSTSerializer;
import io.breeze.serialization.SerializerFactory;
import io.breeze.transport.connector.Acceptor;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.net.SocketAddress;
import java.util.List;

/**
 * Created by chenkai on 2018/11/15.
 */
public class DefaultServer extends Acceptor {

    FSTSerializer fstSerializer = SerializerFactory.getFSTSerializer();

    public RegistryService registryService;

    public DefaultServer(RegistryService registryService) {
        this.registryService = registryService;
    }

    public void bind(SocketAddress localAddress) {
        ServerBootstrap serverBootstrap = bootstrap();

        serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {

            }
        });

    }

    /**
     * Server端 Message编码器
     */
    public class MessageEncoder extends MessageToByteEncoder<Message> {

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
     * Server端 Message解码器
     */
    public class MessageDecoder extends ReplayingDecoder<ProtocolState>{

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        }

    }

}
