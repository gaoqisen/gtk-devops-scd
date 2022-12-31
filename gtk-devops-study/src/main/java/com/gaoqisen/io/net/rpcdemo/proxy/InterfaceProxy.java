package com.gaoqisen.io.net.rpcdemo.proxy;
import com.gaoqisen.io.net.rpcdemo.rpc.Dispatcher;
import com.gaoqisen.io.net.rpcdemo.rpc.protocol.MyContent;
import com.gaoqisen.io.net.rpcdemo.rpc.transport.ClientFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.CompletableFuture;

public class InterfaceProxy {

    /**
     * 获取代理类
     *
     * 1. 缺少注册发现, Zk
     * 2. 考虑第一层负载面向provider
     * 3. consumer 线程池，面向service。并发就有木桶，倾斜
     *
     * serviceS
     *  ipA:port
     *      socket1
     *      socket2
     *  ipB:port
     *

     客户端调用逻辑：

     1. 通过代理类调用远程接口
     2. 利用Dispatcher存储所有实现类（provider进行注册）
     3. 代理类通过interfaceName获取实现类，存在则直接通过反射调用，不存在则进行远程调用（实现本地和远程都可以访问）
     4. 组装远程调用数据（方法名、接口名、参数类型、参数、…）
     5. 传输过程通过不同协议实现（http/rpc）,http可以分为url和netty
     6. 调用数据序列化为字节并封装header
     7. 从连接池获取连接（NioSocketChannel）
     8. 添加回调映射
     9. 封装butBuf
     10. 写入并刷新


     *
     * @param iInfo 代理class
     * @return 代理类
     */
    public static <T>T proxyGet(Class<T> iInfo) {
        ClassLoader loader = iInfo.getClassLoader();
        Class<?>[] method = {iInfo};

        Dispatcher dis = Dispatcher.getDis();
        Object o = Proxy.newProxyInstance(loader, method, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object o = dis.get(iInfo.getName());
                // rpc
                if(o == null) {
                    // 组装数据
                    String name = iInfo.getName();
                    String methodName = method.getName();
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    MyContent build = MyContent.builder().args(args).methodName(methodName)
                            .type(parameterTypes).name(name).build();
                    // 客户端数据传输
                    CompletableFuture<Object> completableFuture = ClientFactory.transport(build);
                    // 阻塞获取
                    return completableFuture.get();
                }

                // local, 可以做一些埋点
                else {
                    Class<?> aClass = o.getClass();
                    try {
                        // 反射，性能最差的的方式/ javasist(dubbo对javasist进行来封装) 性能好
                        Method m = aClass.getMethod(method.getName(), method.getParameterTypes());
                        return m.invoke(o, args);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                return  null;
            }
        });
        return (T)o;
    }

}
