package com.bfsu.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端 handler。。");
        String s="今天天气冷，吃火锅！";

        for (int i = 0; i < 5; i++) {
            byte[] content=s.getBytes("utf-8");
            int len=content.length;
            MessageProtocol messageProtocol=new MessageProtocol();
            messageProtocol.setContent(content);
            messageProtocol.setLen(len);
            ctx.writeAndFlush(messageProtocol);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {

    }
}
