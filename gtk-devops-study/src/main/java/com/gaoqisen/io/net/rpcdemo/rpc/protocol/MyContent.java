package com.gaoqisen.io.net.rpcdemo.rpc.protocol;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class MyContent implements Serializable {

    String name;
    String methodName;
    Class<?>[] type;
    Object[] args;

    // 返回数据
    Object res;

}