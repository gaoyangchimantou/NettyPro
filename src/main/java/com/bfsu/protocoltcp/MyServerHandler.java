package com.bfsu.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol>{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        String string=new String(msg.getContent(),"utf-8");
        System.out.println("服务端接收到消息------>"+string);
    }
}
