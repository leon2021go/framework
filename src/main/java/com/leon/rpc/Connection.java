package com.leon.rpc;

import com.leon.rpc.protocol.Message;
import com.leon.rpc.protocol.NettyResponseFuture;
import com.leon.rpc.protocol.Request;
import com.leon.rpc.protocol.Response;
import io.netty.channel.ChannelFuture;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Copyright (c) 2021 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author longmu
 * @since 2021/2/19
 */
public class Connection implements Closeable {

    private static AtomicLong GLOBEL_ID_GENERATOR = new AtomicLong(0);

    private static Map<Long, NettyResponseFuture<Request>> REQUEST_MAP = new ConcurrentHashMap<>();

    private ChannelFuture future;
    private AtomicBoolean isConnected = new AtomicBoolean();
    public Connection(ChannelFuture future, boolean isConnected) {
        this.future = future;
        this.isConnected.set(isConnected);
    }

    public NettyResponseFuture<Response> request(Message<Request> message, long timeOut){
        Long messageId = GLOBEL_ID_GENERATOR.incrementAndGet();
        message.getHeader().setMessageId(messageId);
        // 创建消息关联的Future
        NettyResponseFuture responseFuture = new NettyResponseFuture(System.currentTimeMillis(),
                timeOut, message, future.channel(), new DefaultPromise(new DefaultEventLoop()));
        // 将消息ID和关联的Future记录到IN_FLIGHT_REQUEST_MAP集合中
        REQUEST_MAP.put(messageId, responseFuture);
        try {
            future.channel().writeAndFlush(message);
        }catch (Exception e){
            REQUEST_MAP.remove(messageId);
            throw e;
        }
        return responseFuture;
    }



    @Override
    public void close() throws IOException {
        future.channel().close();
    }
}