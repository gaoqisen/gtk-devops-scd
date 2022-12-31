package com.gaoqisen.io.net.rpcdemo.rpc.transport;

import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 连接池
 */
public class ClientPoll{

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