package com.gaoqisen.io.net.rpcdemo.rpc.protocol;

import lombok.Data;

@Data
public class PackageMsg {

    private MyHeader header;
    private MyContent content;

    public PackageMsg(MyHeader header, MyContent content) {
        this.header = header;
        this.content = content;
    }
}