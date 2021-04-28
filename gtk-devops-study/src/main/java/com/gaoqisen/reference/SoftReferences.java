package com.gaoqisen.reference;

import java.lang.ref.SoftReference;

public class SoftReferences {

     public static void main(String[] args) throws Exception{
        // 在内存中，内存中开辟了一块10m的空间。m执行了SoftReference对象，SoftReference软引用指向了10m的内存数据
        SoftReference<byte[]> m = new SoftReference<byte[]>(new byte[1024*1024*10]);
        System.out.println (m.get());  // 获取对象地址

        System.gc();
        Thread.sleep(500);  // 给gc回收时间
        System.out.println(m.get());

        // 设置堆空间最大为20m的时候，前面m的软引用已经有10m的数据了。[-Xmx20M]
        // 这个时候在new一个硬引用的b有15m，这个时候堆空间不够20m，故gc会把软引用的数据清理掉。
        // 在输出m的对象地址就为空
        byte[] b = new byte[1024*1024*12];
        System.out.println(m.get());

        // 软引用特别适合作缓存
    }

}
