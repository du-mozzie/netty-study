package com.mozzie.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * @author : mozzie
 * @date : 2026/1/26 0:06
 */
public class WriteServer {

    // 一次性发送大量数据效率低下, 会存在等待写buffer的情况, 需要分多次发送
    // public static void main(String[] args) throws IOException {
    //     ServerSocketChannel ssc = ServerSocketChannel.open();
    //     ssc.socket().bind(new java.net.InetSocketAddress(7000));
    //     Selector selector = Selector.open();
    //     ssc.configureBlocking(false);
    //     ssc.register(selector, SelectionKey.OP_ACCEPT);
    //
    //     while (true) {
    //         selector.select();
    //         Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
    //         while (iterator.hasNext()) {
    //             SelectionKey key = iterator.next();
    //             if (key.isAcceptable()) {
    //                 SocketChannel channel = ssc.accept();
    //                 channel.configureBlocking(false);
    //
    //                 // 向客户端发送大量数据
    //                 StringBuilder builder = new StringBuilder();
    //                 for (int i = 0; i < 30000000; i++) {
    //                     builder.append("a");
    //                 }
    //                 ByteBuffer buffer = Charset.defaultCharset().encode(builder.toString());
    //                 while (buffer.hasRemaining()) {
    //                     int write = channel.write(buffer);
    //                     System.out.println("write: " + write);
    //                 }
    //             }
    //             iterator.remove();
    //         }
    //     }
    // }

    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new java.net.InetSocketAddress(7000));
        Selector selector = Selector.open();
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                // 获取到一个事件
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    SocketChannel channel = ssc.accept();
                    channel.configureBlocking(false);
                    SelectionKey scKey = channel.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);

                    // 向客户端发送大量数据
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < 30000000; i++) {
                        builder.append("a");
                    }
                    ByteBuffer buffer = Charset.defaultCharset().encode(builder.toString());
                    int write = channel.write(buffer);
                    System.out.println("write: " + write);
                    // 第一次写完如果还有数据, 则注册写事件 分多次写
                    if (buffer.hasRemaining()) {
                        scKey.interestOps(scKey.interestOps() | SelectionKey.OP_WRITE);
                        scKey.attach(buffer);
                    }
                }else if (key.isWritable()) {
                    // 监听写事件，分多次写
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel channel = (SocketChannel) key.channel();
                    int write = channel.write(buffer);
                    System.out.println("write: " + write);
                    if (!buffer.hasRemaining()) {
                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);
                    }
                }
                iterator.remove();
            }
        }
    }
}