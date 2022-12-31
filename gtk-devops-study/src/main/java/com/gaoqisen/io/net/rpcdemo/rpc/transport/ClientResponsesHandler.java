package com.gaoqisen.io.net.rpcdemo.rpc.transport;

import com.gaoqisen.io.net.rpcdemo.rpc.ResponseMappingCallback;
import com.gaoqisen.io.net.rpcdemo.rpc.protocol.PackageMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientResponsesHandler extends ChannelInboundHandlerAdapter {

    /**
     * 消费端
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PackageMsg res = (PackageMsg) msg;
        ResponseMappingCallback.runCallRack(res);

    }
}
