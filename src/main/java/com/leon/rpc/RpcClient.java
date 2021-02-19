package com.leon.rpc;

import com.leon.rpc.codec.RpcDecoder;
import com.leon.rpc.codec.RpcEncoder;
import com.leon.rpc.handler.RpcClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.Closeable;
import java.io.IOException;

/**
 * Copyright (c) 2021 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author longmu
 * @since 2021/2/19
 */
public class RpcClient implements Closeable {

    private EventLoopGroup eventLoopGroup;
    private Bootstrap clientBootStrap;
    private String host;
    private int port;

    public RpcClient(String host, int port){
        this.host = host;
        this.port = port;
        clientBootStrap = new Bootstrap();
        clientBootStrap.group(eventLoopGroup)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new RpcEncoder());
                        ch.pipeline().addLast(new RpcDecoder());
                        ch.pipeline().addLast(new RpcClientHandler());
                    }
                });

    }

    public ChannelFuture connect(){
        ChannelFuture channelFuture = clientBootStrap.connect(host, port);
        channelFuture.awaitUninterruptibly();
        return channelFuture;
    }

    @Override
    public void close() throws IOException {
        eventLoopGroup.shutdownGracefully();
    }
}