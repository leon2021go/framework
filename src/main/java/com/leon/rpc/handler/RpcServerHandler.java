package com.leon.rpc.handler;

import com.leon.rpc.InvokeTask;
import com.leon.rpc.protocol.Message;
import com.leon.rpc.protocol.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Copyright (c) 2021 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author longmu
 * @since 2021/2/19
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<Message<Request>> {

    private ApplicationContext applicationContext;

    public RpcServerHandler(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message<Request> requestMessage) throws Exception {
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(new InvokeTask(applicationContext, channelHandlerContext, requestMessage));
    }
}