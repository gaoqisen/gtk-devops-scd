package com.gaoqisen.reference;

import java.lang.ref.WeakReference;

public class WeaksReferences {
    public static void main(String[] args) {
        // 弱引用
        WeakReference<M> m = new WeakReference<>(new M());

        System.out.println(m.get());
        System.gc(); // gc回收时直接回收弱引用
        System.out.println(m.get());
    }
}
