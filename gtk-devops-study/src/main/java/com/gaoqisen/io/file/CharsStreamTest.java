package com.gaoqisen.io.file;

import java.io.FileReader;
import java.io.FileWriter;

/**
 * 字符操作和字节操作一致
 */
public class CharsStreamTest {

    public static void main(String[] args) throws Exception{
        write();
        read();
    }

    /**
     * 读取数据
     */
    private static void read() throws Exception{
        FileReader fileReader = new FileReader("/Users/gaoqisen/Desktop/test.txt");
        int i = 0;
        while ((i = fileReader.read()) != -1) {
            // int型数值赋给char型变量时，只保留其最低8位，高位部分舍弃
            System.out.print((char) i);
        }
    }

    /**
     * 写入数据
     */
    private static void write() throws Exception{
        FileWriter fileWriter = new FileWriter("/Users/gaoqisen/Desktop/test.txt");
        fileWriter.write("rqwerqer\n");
        fileWriter.write("dqerqewr123");
        fileWriter.close();
    }
}
