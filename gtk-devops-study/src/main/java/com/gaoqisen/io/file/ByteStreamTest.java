package com.gaoqisen.io.file;

import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ByteStreamTest {


    /**
     * 读
     * InputStream: 输入流抽象类，常用方法:
     * read()  读取字节，返回下一个字节
     * skip()  跳过指定个数字节
     * available()  返回可读字节数
     * close()  关闭字节数量
     *
     * 写
     * OutputStream: 输出流抽象类，常用方法:
     * write()  写入字符
     * flush()  刷新数据，将缓冲区数据写入磁盘
     * close()  关闭
     */
    public static void main(String[] args) throws Exception {
        String path = "/Users/gaoqisen/Desktop/test.txt";
        String path1 = "/Users/gaoqisen/Desktop/test1.txt";

        // 用来创建文件对象，但是无法读取文件内容
        File file = new File(path);

        // 直接操作字节
        byteArrayInputStream();
        byteArrayOutputStream(path);

        // 操作文件读写
        fileOutputStream();
        fileInputStream();

        // 线程管道读写
        pipedOutputStream();
        pipedInputStream();

        // 对象数据读写（各种基本数据类型）FilterOutputStream
        dataOutputStream();
        dataInputStream();

        // 缓冲读写(利用缓冲区减少磁盘的flush) FilterOutputStream
        bufferedOutputStream(path);
        bufferedInputStream(path);

        // 对象读写（序列化）
        objectOutputStream(path);
        objectInputStream(path);

        // 合并多个流,只有input
        dequenceInputStream(path, path1);

        // 字节流转换为字符流
        outputStreamWriter(path);
        inputStreamWriter(path);
    }




    @Test
    public void ZipFiles() {
        try {
            InputStream in = new FileInputStream("/Users/gaoqisen/Desktop/test.txt");

            ByteArrayOutputStream byteArrayInputStream = getByteArrayOutputStream(in);

            FileOutputStream outputStream = new FileOutputStream("/Users/gaoqisen/Desktop/test123.zip");
            outputStream.write(byteArrayInputStream.toByteArray());

            System.out.println("压缩完成.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private static void dequenceInputStream(String path, String path1) throws IOException {
        SequenceInputStream inputStream = new SequenceInputStream(new FileInputStream(path), new FileInputStream(path1));
        int i = 0;
        while ((i = inputStream.read()) != -1) {
            System.out.print((char) i);
        }
    }

    private static void inputStreamWriter(String path) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(path);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        int i = 0;
        while ((i = inputStreamReader.read()) != -1) {
            System.out.print((char)i);
        }
    }

    private static void outputStreamWriter(String path) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(path));
        outputStreamWriter.write("adsfasdf测试");
        outputStreamWriter.close();
    }

    private static void objectInputStream(String path) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(path));
        Object s = objectInputStream.readObject();
        System.out.println("读取的数据: " + s);
    }

    private static void objectOutputStream(String path) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject("asdf测试");

        byteArrayOutputStream.close();
        byteArrayOutputStream.writeTo(new FileOutputStream(path));
    }

    private static void byteArrayOutputStream(String path) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write("123测试了".getBytes());
        byteArrayOutputStream.writeTo(new FileOutputStream(path));
        byteArrayOutputStream.close();
    }

    private static void bufferedInputStream(String path) throws IOException {
        InputStream inputStream = new FileInputStream(path);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        int i = 0;
        byte[] buf = new byte[1024];
        while ((i = bufferedInputStream.read(buf)) != -1) {
            System.out.println(new String(buf, 0, i));
        }
    }

    private static void bufferedOutputStream(String path) throws IOException {
        OutputStream outputStream = new FileOutputStream(path);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
        bufferedOutputStream.write("我就是一个测试".getBytes(StandardCharsets.UTF_8));
        bufferedOutputStream.close();
    }

    private static void dataInputStream() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("/Users/gaoqisen/Desktop/test.txt");
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        boolean b = dataInputStream.readBoolean();
        System.out.println("boolean: " + b);
        int i = dataInputStream.readInt();
        System.out.println("int: " + i);
        float v = dataInputStream.readFloat();
        System.out.println("float: " + v);
    }

    private static void dataOutputStream() throws IOException {
        FileOutputStream fileInputStream = new FileOutputStream("/Users/gaoqisen/Desktop/test.txt");
        DataOutputStream dataOutputStream = new DataOutputStream(fileInputStream);
        dataOutputStream.writeInt(123);
        dataOutputStream.writeBoolean(true);
        dataOutputStream.writeFloat(12.3f);
        dataOutputStream.close();
    }


    private static void pipedInputStream() {
        new Thread(() -> {
            try {
                int i = 0;
                System.out.println("管道输入流获取数据");
                while ((i = pipedInputStream.read()) != -1) {
                    System.out.print((char) i);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    private static void pipedOutputStream() {
        new Thread(() -> {
            try {
                System.out.println("管道输出流，写入数据");
                pipedOutputStream.write("test".getBytes());
                pipedOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private static void fileOutputStream() throws IOException {
        OutputStream outputStream = new FileOutputStream("/Users/gaoqisen/Desktop/test.txt");
        outputStream.write("test ok ok!".getBytes());
        outputStream.close();
    }

    private static void fileInputStream() throws IOException {
        InputStream inputStream = new FileInputStream("/Users/gaoqisen/Desktop/test.txt");

        // 缓冲区
        byte[] data = new byte[inputStream.available()];
        int read = -1;

        /**
         * 读取数据（文件读到最后一个位置没有数据可读后就返回-1）
         * 为什么读取字节后返回int:
         *   因为返回-1表示文件结束了，如果返回字节的话，就无法区分数据是-1还是文件结束。
         *   int类型占4字节，高三位全部是0，只使用最后一个字节。
         *   文件结束4字节表示-1是32个1，文件中的-1表示是24个0和8个1，这样就区分开了
         */
        while ((read = inputStream.read(data)) != -1) {
            System.out.println(new String(data, 0, read));
        }
    }

    private static void byteArrayInputStream() throws IOException {
        String str = "1234561";
        InputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes());
        int len = -1;

        // 缓存区
        byte[] flush = new byte[10];

        // 读取数据
        while ((len = byteArrayInputStream.read(flush)) != -1) {
            System.out.println(new String(flush, 0, len));
        }
    }




    /**    初始化属性   */

    private static final PipedOutputStream pipedOutputStream = new PipedOutputStream();
    private static PipedInputStream pipedInputStream = null;

    static {
        try {
            pipedInputStream = new PipedInputStream(pipedOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ByteStreamTest() throws IOException {
    }



    /**
     * 内存压缩zip
     *
     * @param in
     * @return
     * @throws IOException
     */
    private static ByteArrayOutputStream getByteArrayOutputStream(InputStream in) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream out = new ZipOutputStream(outputStream);
        out.putNextEntry(new ZipEntry("123.txt"));

        int len;
        byte[] buf = new byte[1024];
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }

        out.closeEntry();
        in.close();
        out.close();
        return outputStream;
    }
}
