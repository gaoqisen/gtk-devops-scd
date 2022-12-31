package com.gaoqisen.io.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Multiplex02Threads {

    // socket服务通道
    private ServerSocketChannel server = null;
    // 多路复用器
    private Selector selector1 = null;
    private Selector selector2 = null;
    private Selector selector3 = null;
    int port = 9090;

    public void initServer() {
        try {
            // 初始化socket通道
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));

            // 创建多路复用器
            selector1 = Selector.open();
            selector2 = Selector.open();
            selector3 = Selector.open();

            // 将第一个多路复用器注册到socket服务里面
            server.register(selector1, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 创建服务
        Multiplex02Threads service = new Multiplex02Threads();
        service.initServer();
        NioThread T1 = new NioThread(service.selector1 ,2);
        NioThread T2 = new NioThread(service.selector2);
        NioThread T3 = new NioThread(service.selector3);

        // 启动主服务，等待1秒后启动子线程
        T1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        T2.start();
        T3.start();

        System.out.println("服务器启动了。。。。。");

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

class NioThread extends Thread {
    Selector selector = null;
    int selectors = 0;
    int id = 0;

    volatile static BlockingQueue<SocketChannel>[] queue;

    static AtomicInteger idx = new AtomicInteger();

    // 初始化指定个数的多路复用器
    NioThread(Selector sel,int n ) {
        this.selector = sel;
        this.selectors =  n;

        queue =new LinkedBlockingQueue[selectors];
        for (int i = 0; i < n; i++) {
            queue[i] = new LinkedBlockingQueue<>();
        }
        System.out.println("Boss 启动");
    }

    // 初始化工作线程
    NioThread(Selector sel  ) {
        this.selector = sel;
        id = idx.getAndIncrement() % selectors  ;
        System.out.println("worker: "+id +" 启动");
    }

    @Override
    public void run() {
        try {
            while (true) {

                while (selector.select(10) > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        // 可接受处理
                        if (key.isAcceptable()) {
                            acceptHandler(key);
                        }
                        // 可读处理
                        else if (key.isReadable()) {
                            readHandler(key);
                        }
                    }
                }
                // 如果当前队列不空则进行注册
                if( ! queue[id].isEmpty()) {
                    ByteBuffer buffer = ByteBuffer.allocate(8192);
                    SocketChannel client = queue[id].take();
                    client.register(selector, SelectionKey.OP_READ, buffer);
                    System.out.println("-------------------------------------------");
                    System.out.println("新客户端：" + client.socket().getPort()+"分配到："+ (id));
                    System.out.println("-------------------------------------------");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void acceptHandler(SelectionKey key) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel client = ssc.accept();
            client.configureBlocking(false);
            int num = idx.getAndIncrement() % selectors;

            queue[num].add(client);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readHandler(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.clear();
        int read = 0;
        try {
            while (true) {
                read = client.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        client.write(buffer);
                    }
                    buffer.clear();
                } else if (read == 0) {
                    break;
                } else {
                    client.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
