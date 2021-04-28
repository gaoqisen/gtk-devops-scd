package com.gaoqisen.classloader;

public class ClassLoaderDemo {

    public static void main(String[] args) {
        // 类加载器为AppClassLoader
        System.out.println("ClassLoaderDemo的类加载器为:" + ClassLoaderDemo.class.getClassLoader());
        // AppClassLoader的父类加载器为ExtClassLoader
        System.out.println("ClassLoaderDemo的父类加载器为:" + ClassLoaderDemo.class.getClassLoader().getParent());
        // ExtClassLoader的父类加载器为null，并不代表ExtClassLoader没有父类加载器,而是Bootstrap ClassLoader 。
        System.out.println("ClassLoaderDemo的祖父类加载器为:" + ClassLoaderDemo.class.getClassLoader().getParent().getParent());

    }

}
