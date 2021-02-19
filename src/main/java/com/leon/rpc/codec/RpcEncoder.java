package com.leon.rpc.codec;

import com.leon.rpc.compress.Compressor;
import com.leon.rpc.constants.Constants;
import com.leon.rpc.protocol.Header;
import com.leon.rpc.protocol.Message;
import com.leon.rpc.serialization.HessianSerialization;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Copyright (c) 2021 Choice, Inc.
 * All Rights Reserved.
 * Choice Proprietary and Confidential.
 *
 * @author longmu
 * @since 2021/2/19
 */
public class RpcEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) throws Exception {
        Header header = message.getHeader();
        byteBuf.writeLong(header.getMessageId());
        byteBuf.writeShort(header.getMagic());
        byteBuf.writeByte(header.getVersion());
        byteBuf.writeByte(header.getExtraInfo());
        byteBuf.writeInt(header.getSize());

        if(Constants.isHeartBeats(header.getExtraInfo())){
            byteBuf.writeInt(0);
            return;
        }

        byte[] payload = HessianSerialization.serialize(message.getRequest());
        if(Constants.isNeedCompress(header.getExtraInfo())){
            payload = Compressor.compress(payload);
        }

        byteBuf.writeInt(payload.length);
        byteBuf.writeBytes(payload);
    }
}