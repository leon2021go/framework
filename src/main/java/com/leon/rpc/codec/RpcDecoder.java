package com.leon.rpc.codec;

import com.leon.rpc.compress.Compressor;
import com.leon.rpc.constants.Constants;
import com.leon.rpc.protocol.Header;
import com.leon.rpc.protocol.Message;
import com.leon.rpc.protocol.Request;
import com.leon.rpc.serialization.HessianSerialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Copyright (c) 2021 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author longmu
 * @since 2021/2/19
 */
public class RpcDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        byteBuf.markReaderIndex();

        Long messageId = byteBuf.readLong();
        short magic = byteBuf.readShort();
        byte version = byteBuf.readByte();
        byte extraInfo = byteBuf.readByte();
        int size = byteBuf.readInt();

        if(byteBuf.readableBytes() < size){
            byteBuf.resetReaderIndex();
            return;
        }

        byte[] payload = new byte[size];
        byteBuf.readBytes(payload);

        Header header = new Header();
        header.setExtraInfo(extraInfo);
        header.setMagic(magic);
        header.setMessageId(messageId);
        header.setVersion(version);
        header.setSize(size);

        if(Constants.isNeedCompress(extraInfo)){
            payload = Compressor.unCompress(payload);
        }
        Request request = (Request)HessianSerialization.deSerialize(payload, Request.class);

        Message message = new Message();
        message.setHeader(header);
        message.setRequest(request);

        list.add(message);
    }
}