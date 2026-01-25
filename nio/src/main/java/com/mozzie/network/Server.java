package com.mozzie.network;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author : mozzie
 * @date : 2026/1/25 21:37
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        // 创建一个selector
        Selector selector = Selector.open();
        // 使用open()方法打开一个ServerSocketChannel
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 绑定监听端口
        ssc.bind(new InetSocketAddress(7000));
        // 设置为非阻塞模式
        ssc.configureBlocking(false);
        // 把server的channel 注册到selector
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        List<SocketChannel> socketChannels = new ArrayList<>();
        while (true) {
            // select方法，没有事件发生，线程阻塞，有事件，线程才会恢复运行
            // select 在事件未处理时, 它不会阻塞, 事件发生后要么处理, 要么取消(cancel)
            selector.select();
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 这里移除的是事件, 如果一次消息没有接受完, 那么事件会再次触发
                iterator.remove();
                log.debug("key: {}", key);
                if (key.isAcceptable()) {
                    // 监听到连接事件
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    socketChannels.add(sc);
                    sc.configureBlocking(false);
                    // 把新的连接channel
                    SelectionKey scKey = sc.register(selector, SelectionKey.OP_READ);
                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    // attachment 附件，可以理解为与channel绑定的附件
                    scKey.attach(buffer);
                    log.debug("connected: {}", sc.getRemoteAddress());
                }
                if (key.isReadable()) {
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        // 处理read事件
                        int read = channel.read(buffer);
                        if (read == -1) {
                            // 处理客户端正常断开了连接
                            key.cancel();
                        } else if (read > 0) {
                            // 处理读取到的数据
                            split(buffer);
                            if (buffer.position() == buffer.limit()) {
                                // 缓冲区已满，需要扩容
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                                buffer.flip();
                                newBuffer.put(buffer);
                                key.attach(newBuffer);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        // 客户端断开了, 取消注册（从selector的keys集合中移除）
                        key.cancel();
                    }
                }
            }
        }
    }

    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            // 通过\n分割读取
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                // 把这条完整消息存入新的 ByteBuffer
                ByteBuffer target = ByteBuffer.allocate(length);
                // 将source中的数据读取到target中
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                target.flip();
                System.out.print("read: " + Charset.defaultCharset().decode(target));
            }
        }
        source.compact();
    }
}
