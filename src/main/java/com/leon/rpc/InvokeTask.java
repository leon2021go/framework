package com.leon.rpc;

import com.leon.rpc.protocol.Message;
import com.leon.rpc.protocol.Request;
import com.leon.rpc.protocol.Response;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Copyright (c) 2021 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author longmu
 * @since 2021/2/19
 */
public class InvokeTask implements Runnable{

    private ApplicationContext applicationContext;

    private ChannelHandlerContext ctx;
    private Message<Request> message;

    public InvokeTask(ApplicationContext applicationContext, ChannelHandlerContext ctx, Message<Request> message){
        this.applicationContext = applicationContext;
        this.ctx = ctx;
        this.message = message;
    }

    @Override
    public void run() {
        Response response = new Response();
        Request request = message.getRequest();
        Object bean = applicationContext.getBean(request.getServiceName());
        Object result = null;
        try {
            Method method = bean.getClass().getMethod(request.getMethodName(), request.getArgsTypes());
            result = method.invoke(bean, request.getArgs());
            ctx.writeAndFlush(result);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        response.setResult(result);
        ctx.writeAndFlush(new Message<>(message.getHeader(), response));
    }
}