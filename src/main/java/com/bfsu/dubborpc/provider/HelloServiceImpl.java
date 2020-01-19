package com.bfsu.dubborpc.provider;

import com.atguigu.netty.dubborpc.publicinterface.HelloService;

public class HelloServiceImpl implements HelloService {
    public int count;
    @Override
    public String hello(String msg) {
        System.out.println("收到客户端的消息！");
        if(msg!=null){
            return "您好 我已收到你的消息 ["+msg+"]   第"+(++count)+"次";

        }else{
            return "您好 我已收到你的消息";
        }
    }
}
