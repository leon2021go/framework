package com.leon.rpc;

import com.leon.rpc.codec.RpcDecoder;
import com.leon.rpc.codec.RpcEncoder;
import com.leon.rpc.handler.RpcServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.springframework.context.ApplicationContext;

/**
 * Copyright (c) 2021 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author longmu
 * @since 2021/2/19
 */
public class RpcServer {

    private EventLoopGroup bossEventLoopGroup;
    private EventLoopGroup workerEventLoopGroup;
    private ServerBootstrap serverBootstrap;
    private Channel channel;
    private int port;

    public RpcServer(int port, ApplicationContext applicationContext){
        bossEventLoopGroup = System.getProperty("os.name").toLowerCase().contains("linux")?
                new EpollEventLoopGroup(1, new DefaultThreadFactory("boss", true))
                : new NioEventLoopGroup(1, new DefaultThreadFactory("boss", true));
        workerEventLoopGroup = System.getProperty("os.name").toLowerCase().contains("linux")?
                new EpollEventLoopGroup(Math.min(Runtime.getRuntime().availableProcessors() + 1,
                        32), new DefaultThreadFactory("worker", true))
                : new NioEventLoopGroup(Math.min(Runtime.getRuntime().availableProcessors() + 1,
                32), new DefaultThreadFactory("worker", true));

        serverBootstrap = new ServerBootstrap().group(bossEventLoopGroup, workerEventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
                .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new RpcEncoder());
                        ch.pipeline().addLast(new RpcDecoder());
                        ch.pipeline().addLast(new RpcServerHandler(applicationContext));

                    }
                });
    }

    public ChannelFuture  start(){
        ChannelFuture channelFuture = serverBootstrap.bind();
        channel = channelFuture.channel();
        channel.closeFuture();
        return channelFuture;
    }


}