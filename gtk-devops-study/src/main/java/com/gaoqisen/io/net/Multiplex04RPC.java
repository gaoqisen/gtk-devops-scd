package com.gaoqisen.io.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.Builder;
import lombok.Data;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * RPC思路（就像调用本地方法一样，调用远程的方法。面向接口开发）：
 *
 * 1. 客户端、服务端来回通信，连接数量，拆包
 * 2. 动态代理，序列化，协议封装
 * 3. 连接池
 *
 *
 *
 */
public class Multiplex04RPC {

    @Test
    public void testSend() {
        ClientFactory.getFactory().create(new InetSocketAddress("127.0.0.1", 9090));
    }

    @Test
    public void startServer() {

        MyCar car = new MyCar();
        MyFly fly = new MyFly();

        Dispatcher dis = new Dispatcher();
        dis.register(Car.class.getName(), car);
        dis.register(MyFly.class.getName(), fly);


        // 混搭模式，即是客户端，也是服务端
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = boss;

        ServerBootstrap sbs = new ServerBootstrap();
        ChannelFuture bind = sbs.group(boss, worker).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        System.out.println("server accept client port:" + ch.remoteAddress().getAddress());
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new ServerDecode());
                        pipeline.addLast(new ServerRequestHandler(dis));
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
        new Thread(() -> {
            startServer();
        }).start();
        Thread.sleep(2000);
        System.out.println("server started...");

        AtomicInteger num = new AtomicInteger(0);
        int size = 50;
        Thread[] threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            threads[i] = new Thread(() -> {
                Car cat = proxyGet(Car.class);
                String args = "开车" + num.incrementAndGet();
                String res = cat.open(args);
                System.out.println(">>> " + res + " args: " + args);
            });
        }

        for (Thread thread : threads) {
            thread.start();
        }

        System.in.read();

        Car cat = proxyGet(Car.class);
        cat.open("开车");

//        Fly fly = proxyGet(Fly.class);
//        fly.open("开飞机");
    }

    /**
     * 通过动态代理获取实现类
     */
    private static <T>T proxyGet(Class<T> iInfo) {
        ClassLoader loader = iInfo.getClassLoader();
        Class<?>[] method = {iInfo};

        Object o = Proxy.newProxyInstance(loader, method, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 通过netty调用服务端方法

                // 1. 收集：调用服务、方法、参数 为message
                String name = iInfo.getName();
                String methodName = method.getName();
                Class<?>[] parameterTypes = method.getParameterTypes();
                MyContent build = MyContent.builder()
                        .args(args).methodName(methodName)
                        .type(parameterTypes)
                        .name(name).build();

                // 序列化
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ObjectOutputStream stream = new ObjectOutputStream(out);
                stream.writeObject(build);
                byte[] bodyBytes = out.toByteArray();

                // 2. 通过requestID + message, 本地缓存。找到服务端返回的数据
                // 协议：header：MyHeader, body: MyContent
                MyHeader header = createHeader(bodyBytes);
                out.reset();
                stream = new ObjectOutputStream(out);
                stream.writeObject(header);
                // todo 解决数据decode问题
                byte[] headBytes = out.toByteArray();

                System.out.println("headBytes size: " + headBytes.length);

                // 3. 链接池：取得链接
                ClientFactory factory = ClientFactory.getFactory();
                NioSocketChannel clientChannel = factory.getClient(new InetSocketAddress("127.0.0.1", 9090));
                // 获取连接过程中：开始-创建，


                // 4. 发送：io out(netty 驱动)
                ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(
                        headBytes.length + bodyBytes.length);
             //   CountDownLatch countDownLatch = new CountDownLatch(1);
                CompletableFuture<String> res = new CompletableFuture<>();
                ResponseMappingCallback.addCallBack(header.getRequestId(), res);
                byteBuf.writeBytes(headBytes);
                byteBuf.writeBytes(bodyBytes);
                ChannelFuture future = clientChannel.writeAndFlush(byteBuf);
                // io是双向的，看似是sync。仅代表out
                future.sync();

               // countDownLatch.await();


                // 5. 获取的返回数据，如何往下执行（countDown）

                // 阻塞获取
                return res.get();
            }
        });

        return (T)o;
    }

    private static MyHeader createHeader(byte[] bytes) {
        // 标志位(类似sql的码表)
        int f = 0x14141414;
        long requestId = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        return MyHeader.builder()
                .dataLen(bytes.length)
                .flag(f)
                .requestId(requestId).build();
    }


}

// spark 源码
// 单例
class ClientFactory{

    private ClientFactory(){
    }

    // 随机取client
    Random random = new Random();
    int pollSize = 10;
    NioEventLoopGroup worker;

    private static final ClientFactory factory;
    // 一个消费者可以连接多个提供者，每个提供者都有poll
    ConcurrentHashMap<InetSocketAddress, ClientPoll> outboxs = new ConcurrentHashMap<>();

    static {
        factory = new ClientFactory();
    }

    public static ClientFactory getFactory() {
        return factory;
    }

    public synchronized NioSocketChannel getClient(InetSocketAddress address) {
        ClientPoll clientPoll = outboxs.get(address);
        if(clientPoll == null) {
            outboxs.put(address, new ClientPoll(pollSize));
            clientPoll = outboxs.get(address);
        }

        int i =random.nextInt(pollSize);
        // 有效可用
        if(clientPoll.clients[i] != null && clientPoll.clients[i].isActive()) {
            return clientPoll.clients[i];
        }

        // 新建
        synchronized (clientPoll.lock[i]) {
            return clientPoll.clients[i] = create(address);
        }

    }

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

// 解决粘包，拆包问题
class ServerDecode extends ByteToMessageDecoder {

    // 父类里存在channel read > bytebuf
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> list) throws Exception {
        // 长度不一样。path不一样的全限定名就不一样
        while(buf.readableBytes() >= 98) {
            byte[] bytes = new byte[98];
            // getBytes不会移动指针
            buf.getBytes(buf.readerIndex(), bytes);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            MyHeader header = (MyHeader)objectInputStream.readObject();
            System.out.println("server requestId: " + header.getRequestId());

            // 编码和解码都使用（通信协议）
            if(buf.readableBytes() >= header.getDataLen()) {
                // 指针移动到body开始的位置，处理指针。
                buf.readBytes(98);
                byte[] data = new byte[(int)header.getDataLen()];
                buf.readBytes(data);
                ByteArrayInputStream din = new ByteArrayInputStream(data);
                ObjectInputStream doin = new ObjectInputStream(din);

                if(header.getFlag() == 0x14141414) {
                    MyContent content = (MyContent) doin.readObject();
                    list.add(new PackageMsg(header, content));
                }

                if(header.getFlag() == 0x14141424) {
                    MyContent content = (MyContent) doin.readObject();
                    list.add(new PackageMsg(header, content));
                }

            } else  {
                // 将丢弃的数据存储，下次在拼接即可
                System.out.println("body不够" + buf.readableBytes());
                break;
            }
        }
    }


}

@Data
class PackageMsg {

    private MyHeader header;
    private MyContent content;

    public PackageMsg(MyHeader header, MyContent content) {
        this.header = header;
        this.content = content;
    }
}

class ServerRequestHandler extends ChannelInboundHandlerAdapter{

    Dispatcher dis;

    public ServerRequestHandler(Dispatcher dis) {
        this.dis = dis;
    }

    /**
     * 提供端
     *
     *
     // 多线程会出现多个数据混合在一起的情况[head][body][head][body]..., 可能会出现body只有一半的情况
     // 总结： netty channel.read不能保证数据完整性。不是一次read处理一个message。前后的read能保证数据完整性
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PackageMsg requestPkg = (PackageMsg) msg;
        System.out.println("获取到的数据: " + requestPkg.getContent().getArgs()[0]);
        // 处理完成， 需要给客户端返回数据
        // 1. bytebuf 2. requestId 3. 解码问题 4. 关注rpc通信协议()

        String ioThreadName = Thread.currentThread().getName();
        // 用线程池里面的线程处理返回数据
        // 1. 直接在当前方法 处理io和返回。
        // 2. 使用netty自己的eventloop来处理业务以及返回
        // 3. 自己创建线程池
        ctx.executor().execute(new Runnable(){
            //        ctx.executor().parent().next().execute(new Runnable() {

            @Override
            public void run() {
                String serviceName = requestPkg.getContent().getName();
                String method = requestPkg.getContent().getMethodName();
                Object c = dis.get(serviceName);
                Class<?> aClass = c.getClass();
                Object res = null;
                try {
                    // 反射，性能最差的的方式
                    Method m = aClass.getMethod(method, requestPkg.getContent().getType());
                    res = m.invoke(c, requestPkg.getContent().getArgs());

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }


                String execThreadName = Thread.currentThread().getName();
                MyContent content = MyContent.builder().build();
             //   String s = "io thread: " + ioThreadName + " exec thread: " + execThreadName + " from args:" + requestPkg.getContent().getArgs()[0];
             //   System.out.println(s);
                content.setRes((String)res);
                byte[] contentByte = SerDerUtil.ser(content);
                MyHeader resHeader = MyHeader.builder().build();
                resHeader.setRequestId(requestPkg.getHeader().getRequestId());
                resHeader.setFlag(0x14141424);
                resHeader.setDataLen(contentByte.length);

                byte[] headerByte = SerDerUtil.ser(resHeader);

                ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(headerByte.length + contentByte.length);
                byteBuf.writeBytes(headerByte);
                byteBuf.writeBytes(contentByte);

                ctx.writeAndFlush(byteBuf);
            }
        });

    }


}

class ClientResponsesHandler extends ChannelInboundHandlerAdapter{

    /**
     * 消费端
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PackageMsg res = (PackageMsg) msg;
        //
        ResponseMappingCallback.runCallRack(res);

    }
}

class ResponseMappingCallback {

    static ConcurrentHashMap<Long, CompletableFuture<String>> mapping = new ConcurrentHashMap<>();

    public static void addCallBack(long requestId, CompletableFuture<String> cb) {
        mapping.putIfAbsent(requestId, cb);
    }
    public static void runCallRack(PackageMsg msg) {
        CompletableFuture<String> cf = mapping.get(msg.getHeader().getRequestId());
        // runnable.run();
        cf.complete(msg.getContent().getRes());
        removeCB(msg.getHeader().getRequestId());
    }

    private static void removeCB(long requestId) {
        mapping.remove(requestId);
    }

}


/**
 * 连接池
 */
class ClientPoll{

    NioSocketChannel[] clients;

    Object[] lock;

    ClientPoll(int size) {
        // init
        clients = new NioSocketChannel[size];
        lock = new Object[size];

        for (int i = 0; i < size; i++) {
            lock[i] = new Object();
        }
    }

}

class Dispatcher{

    public static ConcurrentHashMap<String, Object> invokeMap = new ConcurrentHashMap<>();

    public void register(String k, Object obj) {
        invokeMap.put(k, obj);
    }

    public Object get(String k) {
        return invokeMap.get(k);
    }


}

@Data
@Builder
class MyHeader implements Serializable{

    /**
     * 通信上的协议
     * 1. ooxx值
     * 2. uuid
     * 3. data_len
     *
     * 32位
     */
    private int flag;
    private long requestId;
    private long dataLen;

}

@Data
@Builder
class MyContent implements Serializable{

    String name;
    String methodName;
    Class<?>[] type;
    Object[] args;
    String res;

}

class MyCar implements Car {

    @Override
    public String open(String name) {
        System.out.println("实现类收到");
        return "server res > " + name;
    }
}

interface Car{

    /**
     * 开车
     */
    public String open(String name);

}

class MyFly implements Fly{

    @Override
    public void open(String name) {
        System.out.println("实现类收到");
    }
}

interface Fly {

    /**
     * 开车
     */
    public void open(String name);

}
