package io.breeze.transport.connector;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Created by chenkai on 2018/11/15.
 */
public class Acceptor {

    private ServerBootstrap serverBootstrap;
    private EventLoopGroup bossGroup;
    private EventLoopGroup worker;

    private int nBosses;
    private int nWorker;

    public void init() {
        serverBootstrap = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup(nBosses);
        worker = new NioEventLoopGroup(nWorker);
        serverBootstrap.group(bossGroup, worker);

    }

    public ServerBootstrap bootstrap() {
        return serverBootstrap;
    }

}
