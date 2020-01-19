package com.bfsu.dubborpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable{

    private ChannelHandlerContext context;//上下文
    private String result; //返回的结果

    public void setPara(String para) {
        this.para = para;
    }

    private String para; //客户端调用方法时，传入的参数

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive方法调用!");
        context=ctx;
    }
    //服务器返回的数据   会在这里接收
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //ctx.fireChannelRead(msg);
        result=msg.toString();
        notify();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        ctx.close();
    }
    /**
     * callable接口方法  ，在线程池中调用  向服务端发送数据
     * @return
     * @throws Exception
     */
    //
    @Override
    public synchronized Object call() throws Exception {
        System.out.println("call()........1");
        context.writeAndFlush(para);
        wait();
        System.out.println("call()........2");
        return result;
    }

}
