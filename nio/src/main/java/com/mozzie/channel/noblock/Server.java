package com.mozzie.channel.noblock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : mozzie
 * @date : 2026/1/25 20:27
 */
public class Server {

    public static void main(String[] args) {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            // 使用open()方法打开一个ServerSocketChannel
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            // 绑定监听端口
            serverSocketChannel.bind(new InetSocketAddress(7000));
            // 设置为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            List<SocketChannel> socketChannels = new ArrayList<>();
            ;
            while (true) {
                // 接收连接，返回一个SocketChannel
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    socketChannels.add(socketChannel);
                    socketChannel.configureBlocking(false);
                    // 处理连接
                    System.out.println("New connection: " + socketChannel.getRemoteAddress());
                }
                for (SocketChannel channel : socketChannels) {
                    // 处理read事件
                    int read = channel.read(buffer);
                    if (read > 0) {
                        // 处理读取到的数据
                        buffer.flip();
                        StringBuilder sb = new StringBuilder();
                        while (buffer.hasRemaining()) {
                            sb.append((char) buffer.get());
                        }
                        System.out.println("Received: " + sb);
                        buffer.clear();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
