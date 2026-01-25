package com.mozzie.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author : mozzie
 * @date : 2026/1/26 0:10
 */
public class WriteClient {
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 7000));

        // 读取数据
        int count = 0;
        while (true) {
            // 分配一个buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024);
            count += channel.read(buffer);
            System.out.println("read: " + count);
        }
    }
}
