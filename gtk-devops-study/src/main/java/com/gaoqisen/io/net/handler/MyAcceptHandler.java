package com.gaoqisen.io.net.handler;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;

public class MyAcceptHandler extends ChannelInboundHandlerAdapter {


    private EventLoopGroup selector;
    private ChannelHandler handler;

    public MyAcceptHandler(EventLoopGroup group, ChannelHandler handler) {
        this.selector = group;
        this.handler = handler;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("注册回调");
    }

    /**
     * netty调用accept后回调该方法
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("读取事件");
        SocketChannel channel = (SocketChannel) msg;

        selector.register((Channel) channel);
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast(handler);
    }


}
