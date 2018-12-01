package io.breeze.transport.connector;

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

    private int nBosses;
    private int nWorker;

    protected SocketAddress localAddress;

    public Acceptor(SocketAddress localAddress) {
        this.localAddress = localAddress;
    }

    public void init() {
        serverBootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(nBosses);
        worker = new NioEventLoopGroup(nWorker);
        serverBootstrap.group(bossGroup, worker);
    }

    public abstract ChannelFuture bind();

    public void start() throws InterruptedException {
        ChannelFuture channelFuture = bind().sync();
    }

    public ServerBootstrap bootstrap() {
        return serverBootstrap;
    }

}
