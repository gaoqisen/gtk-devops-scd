package com.gaoqisen.io.net.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.Channel;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

public class SelectorThreadGroup {

    SelectorThread[] sts;
    ServerSocketChannel server = null;

    AtomicInteger xid = new AtomicInteger(0);
    SelectorThreadGroup  stg =  this;

    public void setWorker(SelectorThreadGroup  stg){
        this.stg =  stg;

    }

    /**
     * 初始化线程
     * @param i 线程数
     */
    public SelectorThreadGroup(int i) {
        sts = new SelectorThread[i];
        for (int j = 0; j < i; j++) {
            sts[j] = new SelectorThread(this);
            new Thread(sts[j]).start();
        }
    }

    /**
     * 绑定
     * @param i 端口号
     */
    public void bind(int i) {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(i));

            // new的线程注册到那个selector上
            nextSelectorV3(server);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取需要注册的selector
     */
    public void nextSelectorV3(Channel c) {

        try {
            if(c instanceof  ServerSocketChannel){
                SelectorThread st = next();  //listen 选择了 boss组中的一个线程后，要更新这个线程的work组
                st.lbq.put(c);
                st.setWorker(stg);
                st.selector.wakeup();
            }else {
                SelectorThread st = nextV3();  //在 main线程种，取到堆里的selectorThread对象

                //1,通过队列传递数据 消息
                st.lbq.add(c);
                //2,通过打断阻塞，让对应的线程去自己在打断后完成注册selector
                st.selector.wakeup();

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    //无论 serversocket  socket  都复用这个方法
    private SelectorThread next() {
        int index = xid.incrementAndGet() % sts.length;  //轮询就会很尴尬，倾斜
        return sts[index];
    }

    private SelectorThread nextV3() {
        int index = xid.incrementAndGet() % stg.sts.length;  //动用worker的线程分配
        return stg.sts[index];
    }

}
