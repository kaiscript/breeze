package io.breeze.transport.connector;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.ThreadFactory;

/**
 * 维护连接实例
 * Created by kaiscript on 2018/11/14.
 */
public class Connector {

    private Bootstrap bootstrap;
    private EventLoopGroup eventLoopGroup;
    private int nThreads;

    public void init() {
        ThreadFactory threadFactory = workerThreadFactory("io.breeze.connector");
        eventLoopGroup = new NioEventLoopGroup(nThreads, threadFactory);
        bootstrap = new Bootstrap().group(eventLoopGroup);

    }

    protected ThreadFactory workerThreadFactory(String name) {
        return new DefaultThreadFactory(name, Thread.MAX_PRIORITY);
    }

    public Bootstrap bootstrap() {
        return bootstrap;
    }

}
