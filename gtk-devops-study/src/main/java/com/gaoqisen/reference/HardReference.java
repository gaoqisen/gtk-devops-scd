package com.gaoqisen.reference;

public class HardReference {


    public static void main(String[] args) throws Exception{
        // M写finalize方法，在垃圾回收的时候会被调用. 垃圾回收的线程和main主线程那个先执行，不可控。
        M m = new M();  // 强引用
        m = null;
        System.gc();  // 显示调用垃圾回收
        System.out.println(m);  // 输出 null
        System.in.read();  // 阻塞main线程，给垃圾回收线程时间去执行. 如果重写了finalize方法的话，就会执行该方法
    }

}
class M{
    @Override
    protected void finalize(){
        System.out.println("垃圾回收开始回收了");
    }
}