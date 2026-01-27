package com.mozzie.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author duyj
 * @date 2026/1/27 18:05
 */
public class MultiThreadClient {
    public static void main(String[] args) {
        try {
            try (SocketChannel clientChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 7000))) {
                System.out.println(clientChannel);
                clientChannel.write(ByteBuffer.wrap("hello12356789abcdefg!!!\nworld\n".getBytes()));
                System.in.read();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}