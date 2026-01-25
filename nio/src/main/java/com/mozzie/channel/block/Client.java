package com.mozzie.channel.block;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author : mozzie
 * @date : 2026/1/25 20:27
 */
public class Client {

    public static void main(String[] args) {
        try {
            try (SocketChannel clientChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 7000))) {
                System.out.println(clientChannel);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
