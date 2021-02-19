package com.leon.rpc.handler;

import com.leon.rpc.protocol.Message;
import com.leon.rpc.protocol.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Copyright (c) 2021 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author longmu
 * @since 2021/2/19
 */
public class RpcClientHandler extends SimpleChannelInboundHandler<Message<Response>> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Message message) throws Exception {

    }
}