package com.gaoqisen.io.net.rpcdemo.rpc.protocol;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class MyHeader implements Serializable {

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

    /**
     * 通过动态代理获取实现类
     */
    public static MyHeader createHeader(byte[] bytes) {
        // 标志位(类似sql的码表)
        int f = 0x14141414;
        long requestId = Math.abs(UUID.randomUUID().getLeastSignificantBits());
        return MyHeader.builder()
                .dataLen(bytes.length)
                .flag(f)
                .requestId(requestId).build();
    }

}