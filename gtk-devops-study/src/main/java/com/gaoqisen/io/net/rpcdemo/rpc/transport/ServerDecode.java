package com.gaoqisen.io.net.rpcdemo.rpc.transport;

import com.gaoqisen.io.net.rpcdemo.rpc.protocol.MyContent;
import com.gaoqisen.io.net.rpcdemo.rpc.protocol.MyHeader;
import com.gaoqisen.io.net.rpcdemo.rpc.protocol.PackageMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.List;

// 解决粘包，拆包问题
public class ServerDecode extends ByteToMessageDecoder {

    // 父类里存在channel read > bytebuf
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> list) throws Exception {
        // 长度不一样。path不一样的全限定名就不一样
        while(buf.readableBytes() >= 119) {
            byte[] bytes = new byte[119];
            // getBytes不会移动指针
            buf.getBytes(buf.readerIndex(), bytes);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            MyHeader header = (MyHeader)objectInputStream.readObject();
            System.out.println("server requestId: " + header.getRequestId());

            // 编码和解码都使用（通信协议）
            if(buf.readableBytes() - 119 >= header.getDataLen()) {
                // 指针移动到body开始的位置，处理指针。
                buf.readBytes(119);
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
