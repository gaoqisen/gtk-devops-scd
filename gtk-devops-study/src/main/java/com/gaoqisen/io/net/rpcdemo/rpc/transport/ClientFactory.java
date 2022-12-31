package com.gaoqisen.io.net.rpcdemo.rpc.transport;

import com.gaoqisen.io.net.SerDerUtil;
import com.gaoqisen.io.net.rpcdemo.rpc.ResponseMappingCallback;
import com.gaoqisen.io.net.rpcdemo.rpc.protocol.MyContent;
import com.gaoqisen.io.net.rpcdemo.rpc.protocol.MyHeader;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class ClientFactory{

    private ClientFactory(){
    }

    /**
     * 随机取client
     */
    Random random = new Random();
    int pollSize = 5;
    NioEventLoopGroup worker;
    private static final ClientFactory factory;

    /**
     * 一个消费者可以连接多个提供者，每个提供者都有poll
     */
    ConcurrentHashMap<InetSocketAddress, ClientPoll> outboxs = new ConcurrentHashMap<>();

    static {
        factory = new ClientFactory();
    }

    public static ClientFactory getFactory() {
        return factory;
    }

    public static CompletableFuture<Object> transport(MyContent content) {
        /**
         * 用自定义rpc传输协议，也可以用http协议传输
         * 有无状态源于协议，自定义的rpc协议是有状态。 http协议是无状态， 每个请求对应一个连接
         * dubbo可以是rpc/http协议
         */

        String type = "http";
        CompletableFuture<Object> res = new CompletableFuture<>();
        if("rpc".equals(type)) {

            rpcTransport(content, res);
        }
        // http协议为载体
        else {
            // 1. 用url已有的工具（包含http的编解码，发送 socket，连接）
            // urlTransport(content, res);

            // 2. 利用no netty(io 框架) + 已经提供的http相关的编解码
            nettyTransport(content, res);
        }


        return res;
    }

    private static void nettyTransport(MyContent content, CompletableFuture<Object> res) {
        // 每个请求对应一个连接
        // 1. 通过nett建立io 建立连接
        NioEventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap();
        Bootstrap client = bootstrap.group(group).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline p = nioSocketChannel.pipeline();
                        p.addLast(new HttpClientCodec())
                                .addLast(new HttpObjectAggregator(1024 * 512))
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        // 3. 接收， 预埋回调，根据netty事件响应
                                        // msg: 完整的http-response
                                        FullHttpResponse response = (FullHttpResponse) msg;
                                        ByteBuf resContent = response.content();
                                        byte[] data = new byte[resContent.readableBytes()];
                                        resContent.readBytes(data);

                                        ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(data));
                                        MyContent o = (MyContent) stream.readObject();
                                        res.complete(o.getRes());
                                    }
                                });
                    }
                });

        try {
            ChannelFuture future = client.connect("localhost", 9090).sync();
            Channel channel = future.channel();
            byte[] ser = SerDerUtil.ser(content);
            DefaultFullHttpRequest httpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0,
                    HttpMethod.POST, "/", Unpooled.copiedBuffer(ser));

            httpRequest.headers().set(HttpHeaderNames.CONTENT_LENGTH, ser.length);
            channel.writeAndFlush(httpRequest).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * rpc传输
     */
    private static void rpcTransport(MyContent content, CompletableFuture<Object> res) {
        byte[] bodyBytes = SerDerUtil.ser(content);
        // 2. 通过requestID + message, 本地缓存。找到服务端返回的数据
        // 协议：header：MyHeader, body: MyContent
        MyHeader header = MyHeader.createHeader(bodyBytes);
        byte[] headBytes = SerDerUtil.ser(header);
        System.out.println("headBytes size: " + headBytes.length);

        // 3. 链接池：取得链接
        ClientFactory factory = getFactory();
        NioSocketChannel clientChannel = factory.getClient(new InetSocketAddress("127.0.0.1", 9090));
        // 获取连接过程中：开始-创建，


        // 4. 发送：io out(netty 驱动)
        ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(
                headBytes.length + bodyBytes.length);
        //   CountDownLatch countDownLatch = new CountDownLatch(1);
        ResponseMappingCallback.addCallBack(header.getRequestId(), res);
        byteBuf.writeBytes(headBytes);
        byteBuf.writeBytes(bodyBytes);
        ChannelFuture future = clientChannel.writeAndFlush(byteBuf);
    }

    private static void urlTransport(MyContent content, CompletableFuture<Object> res) {
        Object o = null;
        try {
            URL url = new URL("http://localhost:9090/");
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            OutputStream outputStream = connection.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(content);

            if(connection.getResponseCode() == 200) {
                InputStream inputStream = connection.getInputStream();
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                MyContent myContent = (MyContent)objectInputStream.readObject();
                o = myContent.getRes();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        res.complete(o);
    }

    public NioSocketChannel getClient(InetSocketAddress address) {
        // todo 在并发情况下一定要谨慎
        ClientPoll clientPoll = outboxs.get(address);
        if(clientPoll == null) {
            synchronized(outboxs) {
                if(clientPoll == null) {
                    outboxs.put(address, new ClientPoll(pollSize));
                    clientPoll = outboxs.get(address);
                }
            }
        }

        int i =random.nextInt(pollSize);
        // 有效可用
        if(clientPoll.clients[i] != null && clientPoll.clients[i].isActive()) {
            return clientPoll.clients[i];
        } else {
            // 新建
            synchronized (clientPoll.lock[i]) {
                if(clientPoll.clients[i] == null || !clientPoll.clients[i].isActive()){
                    clientPoll.clients[i] = create(address);

                }
            }
        }
        return clientPoll.clients[i];
    }

    /**
     * netty创建连接
     * 1. 定义worker
     * 2. 添加拆包、粘包pipeline
     * 3. 添加响应处理pipeline
     */
    public NioSocketChannel create(InetSocketAddress address) {
        // 基于netty的客户端创建(create多路复用器)
        NioEventLoopGroup worker = new NioEventLoopGroup(1);
        ChannelFuture future = new Bootstrap()
                .group(worker)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println("client accept client port:" + ch.remoteAddress());

                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new ServerDecode());
                        // 解决给谁的
                        p.addLast(new ClientResponsesHandler());
                    }
                }).connect(address);

        try {
            Channel channel = future.sync().channel();
            return (NioSocketChannel)channel;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}