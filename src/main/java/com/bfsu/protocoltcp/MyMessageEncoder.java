package com.bfsu.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
//编码器
public class MyMessageEncoder extends MessageToByteEncoder<MessageProtocol>{
    @Override
    protected void encode(ChannelHandlerContext ctx, MessageProtocol msg, ByteBuf out) throws Exception {
        System.out.println(".......");
        out.writeInt(msg.getLen());
        out.writeBytes(msg.getContent());
        System.out.println("编码器被调用！！");

    }
}
