package com.bfsu.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.ServerSocket;

public class GroupChatServer {
    private int port;//监听端口

    public GroupChatServer(int port){
        this.port=port;
    }

    public void run() throws Exception{

        EventLoopGroup bossEventLoopGroup = new NioEventLoopGroup(1);
        EventLoopGroup workEventLoopGroup = new NioEventLoopGroup();

        try {


            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossEventLoopGroup, workEventLoopGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast("decoder", new StringDecoder());
                            pipeline.addLast("encoder", new StringEncoder());
                            //加入自己业务逻辑的handler
                            pipeline.addLast(new GroupChatServerHandler());

                        }
                    });
            System.out.println("netty服务器启动！");
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            //监听 关闭
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossEventLoopGroup.shutdownGracefully();
            workEventLoopGroup.shutdownGracefully();
        }
    }
    public static void main(String[] args) throws Exception{
            new GroupChatServer(7000).run();
    }


}
