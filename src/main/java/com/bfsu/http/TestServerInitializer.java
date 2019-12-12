package com.bfsu.http;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

import java.net.ServerSocket;
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //向管道中加入处理器
        //先得到处理器
        ChannelPipeline pipeline = ch.pipeline();
        //加入一个netty 提供的httpServerCodec codec =>[coder - decoder]
        //HttpServerCodec 说明
        //1. HttpServerCodec 是netty 提供的处理http的 编-解码器
        pipeline.addLast("MyHttpServerCodec",new HttpServerCodec());
        //添加一个一个自定义的handller
        pipeline.addLast("myhandler",new TestHttpServerHandler());
        System.out.println("ok............");

    }
}
