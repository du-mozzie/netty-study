package com.mozzie.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author : mozzie
 * @date : 2026/1/25 21:37
 */
public class Client {
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