package com.bfsu.http;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/*
说明
1. SimpleChannelInboundHandler 是 ChannelInboundHandlerAdapter
2. HttpObject 客户端和服务器端相互通讯的数据被封装成 HttpObject
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject>{

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
            //判断msg是不是request请求
        if(msg instanceof HttpRequest) {
            //获取URI，过滤指定的资源
            HttpRequest httpRequest=(HttpRequest) msg;
            String uriS = httpRequest.uri();
            URI uri=new URI(uriS);
            if("/favicon.ico".equals(uri.getPath())){
                //
                System.out.println("请求了favicon.ico，不做响应！");
                return;
            }

            //----------------------------------------
            System.out.println("msg类型：" + msg.getClass());
            System.out.println("客户端地址。。" + ctx.channel().remoteAddress());
            //回复信息给浏览器[http协议]
            ByteBuf byteBuf= Unpooled.copiedBuffer("hello  我是服务器", CharsetUtil.UTF_8);
            //构建一个http的响应  即httpresponse

            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());
            //将构建好的response返回
            ctx.writeAndFlush(response);
        }
    }
}
