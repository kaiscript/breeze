package io.breeze.transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.SocketAddress;

/**
 * 服务端Acceptor
 * Created by chenkai on 2018/11/15.
 */
public abstract class Acceptor {

    private ServerBootstrap serverBootstrap;
    private EventLoopGroup bossGroup;
    private EventLoopGroup worker;

    int nBosses = 1;
    int nWorker = 16;

    protected SocketAddress localAddress;

    public void init() {
        serverBootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(nBosses);
        worker = new NioEventLoopGroup(nWorker);
        serverBootstrap.group(bossGroup, worker);
    }

    public abstract ChannelFuture bind();

    protected void start(){
        try {
            ChannelFuture channelFuture = bind().sync();
            //wait until the server is closed
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ServerBootstrap bootstrap() {
        return serverBootstrap;
    }

}
