package com.gaoqisen.io.net.rpcdemo;

import com.gaoqisen.io.net.SerDerUtil;
import com.gaoqisen.io.net.rpcdemo.proxy.InterfaceProxy;
import com.gaoqisen.io.net.rpcdemo.rpc.Dispatcher;
import com.gaoqisen.io.net.rpcdemo.rpc.protocol.MyContent;
import com.gaoqisen.io.net.rpcdemo.rpc.transport.MyHttpRpcHandler;
import com.gaoqisen.io.net.rpcdemo.service.Car;
import com.gaoqisen.io.net.rpcdemo.service.Persion;
import com.gaoqisen.io.net.rpcdemo.service.impl.MyCar;
import com.gaoqisen.io.net.rpcdemo.service.impl.MyFly;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RPC思路（就像调用本地方法一样，调用远程的方法。面向接口开发）：
 *
 * 1. 客户端、服务端来回通信，连接数量，拆包
 * 2. 动态代理，序列化，协议封装
 * 3. 连接池
 */
public class RpcTest {

    /**
     * 利用jetty 启动服务端
     */
    @Test
    public void startHttpServer() throws Exception {
        registered();

        // tomcat jetty
        Server server = new Server(new InetSocketAddress("localhost", 9090));
        ServletContextHandler handler = new ServletContextHandler(server, "/");
        server.setHandler(handler);
        handler.addServlet(MyHttpRpcHandler.class, "/*");

        server.start();
        server.join();
    }


    /**
     * client rpc调用
     */
    @Test
    public void testRpc() {
        Car car = InterfaceProxy.proxyGet(Car.class);
        Persion persion = car.get("123", "tom");
        System.out.println(persion);
    }

    /**
     * netty启动服务
     */
    @Test
    public void startServer() {

        MyCar car = new MyCar();
        MyFly fly = new MyFly();

        Dispatcher dis = new Dispatcher();
        dis.register(Car.class.getName(), car);
        dis.register(MyFly.class.getName(), fly);


        // 混搭模式，即是客户端，也是服务端
        NioEventLoopGroup boss = new NioEventLoopGroup(10);
        NioEventLoopGroup worker = boss;

        ServerBootstrap sbs = new ServerBootstrap();
        ChannelFuture bind = sbs.group(boss, worker).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println("server accept client port:" + ch.remoteAddress().getAddress());
                        ChannelPipeline pipeline = ch.pipeline();

                        // 自定义rpc
//                        pipeline.addLast(new ServerDecode());
//                        pipeline.addLast(new ServerRequestHandler(dis));

                        // http协议，netty提供
                        pipeline.addLast(new HttpServerCodec())
                                .addLast(new HttpObjectAggregator(1024 * 512))
                                .addLast(new ChannelInboundHandlerAdapter(){
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        // http协议， msg是一个完整的http-request
                                        FullHttpRequest request = (FullHttpRequest)msg;
                                        System.out.println(request.toString());

                                        // 序列化的数据
                                        ByteBuf content = request.content();
                                        byte[] data = new byte[content.readableBytes()];
                                        content.readBytes(data);
                                        ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(data));
                                        MyContent myContent = (MyContent) oin.readObject();

                                            String serviceName = myContent.getName();
                                            String method = myContent.getMethodName();
                                            Object c = dis.get(serviceName);
                                            Class<?> aClass = c.getClass();
                                            Object res = null;
                                            try {
                                                // 反射，性能最差的的方式/ javasist(dubbo对javasist进行来封装) 性能好
                                                Method m = aClass.getMethod(method, myContent.getType());
                                                res = m.invoke(c, myContent.getArgs());
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }


                                            MyContent resContent = MyContent.builder().build();
                                            resContent.setRes(res);
                                            byte[] contentByte = SerDerUtil.ser(resContent);

                                            DefaultFullHttpResponse defaultFullHttpRequest = new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.OK,Unpooled.copiedBuffer(contentByte));
                                            defaultFullHttpRequest.headers().set(HttpHeaderNames.CONTENT_LENGTH, contentByte.length);

                                            ctx.writeAndFlush(defaultFullHttpRequest);
                                    }
                                });
                    }
                }).bind(new InetSocketAddress("localhost", 9090));

        try {
            System.out.println("server start sync!");
            bind.sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * customer端
     */
    @Test
    public void get() throws InterruptedException, IOException {
//        new Thread(() -> {
//            startServer();
//        }).start();
//        Thread.sleep(2000);
//        System.out.println("server started...");

        AtomicInteger num = new AtomicInteger(0);
        int size = 50;
        Thread[] threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            threads[i] = new Thread(() -> {
                Car cat = InterfaceProxy.proxyGet(Car.class);
                String args = "开车" + num.incrementAndGet();
                String res = cat.open(args);
                System.out.println(">>> " + res + " args: " + args);
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        System.in.read();

        Car cat = InterfaceProxy.proxyGet(Car.class);
        cat.open("开车");
    }

    private void registered() {
        MyCar car = new MyCar();
        MyFly fly = new MyFly();

        Dispatcher dis = new Dispatcher();
        dis.register(Car.class.getName(), car);
        dis.register(MyFly.class.getName(), fly);
    }

}






















