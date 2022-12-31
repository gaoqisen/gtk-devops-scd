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

/**
 * nc 127.0.0.1 9090 可以测试
 */
public class Multiplex01SingleThread {

    private ServerSocketChannel server = null;
    // 将select poll epoll多路复用器抽象为selector，  通过-D可以设置
    private Selector selector;
    private int port = 9090;

    /**
     * 初始化
     */
    public void initServer() {
        try {
            server = ServerSocketChannel.open();
            // 非阻塞
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));

            // select poll epoll三个多路复用器中优先使用epoll（epoll_create）
            selector = Selector.open();
            /*
            listen状态的fd
            select poll: jvm里开辟一个数组存放fd
            epoll: epoll_ctl(fd3, add, fd4 EPOLLIN
             */
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        initServer();
        try{
            while (true) {
                Set<SelectionKey> keys = selector.keys();
                System.out.println("keys size: " + keys.size());

                // 调用多路复用器，参数可以带时间，0-阻塞，有时间则是超时 ：
                // select, poll: 内核调用select(fd4), poll(fd4)，
                // epoll: 内核调用的epoll_wait()
                // selector.wakeup() 外部线程可以控制不阻塞了。
                while (selector.select() > 0) {
                    // 获取有状态的fd集合
                    Set<SelectionKey> keySet = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keySet.iterator();

                    // 所有的多路复用器，都返回状态，主线程需要自己处理r/w
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        // 是否可接受（之后注册一次）
                        if (key.isAcceptable()) {
                            System.out.println("处理accept");
                            // 新链接，accept接受新链接并返回fd
                            // select，poll，因为他们内核没有空间，那么在jvm中保存和前边的fd4那个listen的一起
                            // epoll： 我们希望通过epoll_ctl把新的客户端fd注册到内核空间
                            acceptHandler(key);
                        }
                        // 是否可读
                        else if (key.isReadable()) {
                            System.out.println("处理readable");
                            // 可能会阻塞 （io threads）, 可以读取数据后主线程结束处理，子线程完成读取操作
                            // 读完后直接回写
                            // 多线程时候先进行取消操作后在注册
                            // key.cancel();
                            readHandler(key);
                        }
                        // 读写分开
                        else if(key.isWritable()) {
                            writeHandler(key);
                        }

                    }
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 接受处理
    public void acceptHandler(SelectionKey key) {
        try {
            // 获取socket服务通道
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            // 通过socket服务的accept获取socket通道
            SocketChannel client = ssc.accept();
            // 设置非阻塞
            client.configureBlocking(false);

            // 字节缓冲
            ByteBuffer buffer = ByteBuffer.allocate(10);

            // socket通道注册读
            /*
            select，poll：jvm里开辟一个数组 fd7 放进去
            epoll：  epoll_ctl(fd3,ADD,fd7,EPOLLIN
             */
            client.register(selector, SelectionKey.OP_READ, buffer);
            System.out.println("-------------------------------------------");
            System.out.println("新客户端：" + client.getRemoteAddress());
            System.out.println("-------------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 写可以放在thread里面(充分利用cpu核数)
     * 主线程负责死循环获取key分配时候，子线程负责read/write操作。相当于子线程是异步处理的，而主线程回一直调用read/write方法
     * 可以用key.cancel处理，这样就是每次获取read/write后就先取消注册（进行系统调用 epoll_ctl(n, del,m... )），
     * 异步子线程里面进行register操作。这样的弊端就是频繁系统调用消耗资源。
     *
     * 当有N个fd有r/w处理时：
     *      将n个fd分组，每组一个selector，将一个selector压到一个线程上。
     *      一个selector有一部分fd，线性处理
     *      多个线程在自己的cpu上执行，代表多个selector并行处理，线程内线性，最终并行的fd被处理
     *      这样就不用进行key.cancel()调用了
     *
     *  分治思想：
     *      如果有100w个链接，4个selector线程，则每个线程处理25w.
     *      一个线程的selector只关注accept， 然后把接受到的客户端fd，分配给其他线程的selector(主从模式)
     */
    private void writeHandler(SelectionKey key) {

        System.out.println("write handler...");
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.flip();
        while (buffer.hasRemaining()) {
            try {

                client.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        buffer.clear();
        key.cancel();
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 写可以放在thread里面
     */
    public void readHandler(SelectionKey key) {
        // 获取socket通道
        SocketChannel client = (SocketChannel) key.channel();
        // 获取缓冲数据
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.clear();
        int read = 0;
        try {
            while (true) {
                // 轮询读取缓冲区的数据，读完则跳出循环
                read = client.read(buffer);
                // 存在数据则将获取的数据重新写入返回
                if (read > 0) {
                    System.out.println("read: " + (char) read);
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        client.write(buffer);
                    }
                    buffer.clear();

                    // 读写分开时
                    // client.register(key.selector(),SelectionKey.OP_WRITE,buffer);
                }
                // 等于0则跳出循环
                else if (read == 0) {
                    System.out.println("跳出循环");
                    break;
                }
                // 小于0则关闭链接
                else {
                    System.out.println("关闭链接");
                    client.close();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void main(String[] args) {
        Multiplex01SingleThread service = new Multiplex01SingleThread();
        service.start();
    }

}
