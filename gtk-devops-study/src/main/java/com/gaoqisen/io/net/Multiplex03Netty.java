package com.gaoqisen.io.net;

import com.gaoqisen.io.net.handler.MyAcceptHandler;
import com.gaoqisen.io.net.handler.TestHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * 手工处理链接(client, server)
 */
public class Multiplex03Netty {

    @Test
    public void loopExecutor() throws IOException {
        // group 线程池的概念 NioEventLoopGroup里面的loopGroup里面只有1个线程，给定的nThreads数量就是执行器的数量
        NioEventLoopGroup selector = new NioEventLoopGroup(1);
        selector.execute(() -> {
            try {
                for(;;) {
                    System.out.println("hello");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        selector.execute(() -> {
            try {
                for(;;) {
                    System.out.println("hello1");
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });



        System.in.read();
    }

    /**
     * 手工处理链接（创建一个客户端和服务端进行通信）
     *
     * 服务端创建方式： nc -l localhost 9090
     * reactor 异步特征
     */
    @Test
    public void clientMode() throws InterruptedException {
        // 线程就是多路复用器
        NioEventLoopGroup thread = new NioEventLoopGroup(1);
        // 创建client并链接
        NioSocketChannel client = new NioSocketChannel();

        // epoll_ctl(5, ADD, 3)
        thread.register(client);

        // 添加回调 响应式 读服务端的数据
        ChannelPipeline pipeline = client.pipeline();
        pipeline.addLast(new TestHandler());

        ChannelFuture future = client.connect(new InetSocketAddress("127.0.0.1", 9090));
        ChannelFuture sync = future.sync();

        // 准备数据写出
        ByteBuf byteBuf = Unpooled.copiedBuffer("test".getBytes());
        ChannelFuture channelFuture = client.writeAndFlush(byteBuf);
        channelFuture.sync();

        // 等待服务端关闭后才断开链接
        sync.channel().closeFuture().sync();
        System.out.println("结束");
    }

    @Test
    public void serverMode() throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup(1);

        NioServerSocketChannel server = new NioServerSocketChannel();
        group.register(server);

        ChannelPipeline pipeline = server.pipeline();
        // 利用ChannelInit可以
        pipeline.addLast(new MyAcceptHandler(group, new TestHandler()));

        ChannelFuture future = server.bind(new InetSocketAddress("127.0.0.1", 9090));

        future.sync().channel().closeFuture().sync();
    }

    /**
     * 官方写法
     */
    @Test
    public void nettyClient() throws InterruptedException {
        // 创建多路复用器
        NioEventLoopGroup group = new NioEventLoopGroup(1);

        // 利用Bootstrap创建链接并注册
        ChannelFuture future = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new TestHandler())
                .connect(new InetSocketAddress("127.0.0.1", 9090));

        // 获取client
        Channel client = future.sync().channel();

        // 向服务端写数据
        ByteBuf byteBuf = Unpooled.copiedBuffer("test".getBytes());
        ChannelFuture channelFuture = client.writeAndFlush(byteBuf);
        channelFuture.sync();

        client.closeFuture().sync();
    }

    @Test
    public void nettyServer() throws InterruptedException {
        // 是一个线程executor，并按照逻辑内核数据设置线程数量，后完成select的绑定
        NioEventLoopGroup group = new NioEventLoopGroup(1);

        // 完成注册、绑定、和通知后的回调
        ChannelFuture bind = new ServerBootstrap().group(group, group).channel(NioServerSocketChannel.class)
               // .childHandler(new TestHandler())
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nc) throws Exception {
                        ChannelPipeline p = nc.pipeline();
                        p.addLast(new TestHandler());
                    }
                })
                .bind(new InetSocketAddress("127.0.0.1", 9090));

        // 同步处理通道
        bind.sync().channel().closeFuture().sync();
    }

}
