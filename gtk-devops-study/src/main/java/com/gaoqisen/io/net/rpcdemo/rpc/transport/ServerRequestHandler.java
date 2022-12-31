package com.gaoqisen.io.net.rpcdemo.rpc.transport;

import com.gaoqisen.io.net.SerDerUtil;
import com.gaoqisen.io.net.rpcdemo.rpc.Dispatcher;
import com.gaoqisen.io.net.rpcdemo.rpc.protocol.MyContent;
import com.gaoqisen.io.net.rpcdemo.rpc.protocol.MyHeader;
import com.gaoqisen.io.net.rpcdemo.rpc.protocol.PackageMsg;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;

public class ServerRequestHandler extends ChannelInboundHandlerAdapter {

    Dispatcher dis;

    public ServerRequestHandler(Dispatcher dis) {
        this.dis = dis;
    }

    /**
     * 提供端
     * 多线程会出现多个数据混合在一起的情况[head][body][head][body]..., 可能会出现body只有一半的情况
     * 总结： netty channel.read不能保证数据完整性。不是一次read处理一个message。前后的read能保证数据完整性

      用线程池里面的线程处理返回数据
      1. 直接在当前方法 处理io和返回。
      2. 使用netty自己的eventloop来处理业务以及返回
      3. 自己创建线程池

     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        PackageMsg requestPkg = (PackageMsg) msg;
        System.out.println("获取到的数据: " + requestPkg.getContent().getArgs()[0]);
        ctx.executor().execute(new Runnable(){
            //        ctx.executor().parent().next().execute(new Runnable() {

            @Override
            public void run() {
                Object res = null;

                // 利用反射调用方法获取返回值
                String serviceName = requestPkg.getContent().getName();
                String method = requestPkg.getContent().getMethodName();
                Object c = dis.get(serviceName);
                Class<?> aClass = c.getClass();
                try {
                    // 反射，性能最差的的方式/ javasist(dubbo对javasist进行来封装) 性能好
                    Method m = aClass.getMethod(method, requestPkg.getContent().getType());
                    res = m.invoke(c, requestPkg.getContent().getArgs());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                MyContent content = MyContent.builder().build();
                content.setRes(res);
                byte[] contentByte = SerDerUtil.ser(content);

                MyHeader resHeader = MyHeader.builder().build();
                resHeader.setFlag(0x14141424);
                resHeader.setDataLen(contentByte.length);
                resHeader.setRequestId(requestPkg.getHeader().getRequestId());
                byte[] headerByte = SerDerUtil.ser(resHeader);

                ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(headerByte.length + contentByte.length);
                byteBuf.writeBytes(headerByte);
                byteBuf.writeBytes(contentByte);

                // 响应客户端
                ctx.writeAndFlush(byteBuf);
            }
        });

    }


}