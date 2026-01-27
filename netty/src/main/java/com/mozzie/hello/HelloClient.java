package com.mozzie.hello;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author : mozzie
 * @date : 2026/1/28 0:01
 */
public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        // 客户端启动器
        new Bootstrap()
                // 添加EventLoopGroup
                .group(new NioEventLoopGroup())
                // 客户端通道
                .channel(NioSocketChannel.class)
                // 添加处理器
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel sc) {
                        // 编码器 负责将String转换为ByteBuf
                        sc.pipeline().addLast(new StringEncoder());
                    }
                })
                // 连接到服务器
                .connect("localhost", 7000)
                // 等待连接完成
                .sync()
                // 获取通道
                .channel()
                // 发送数据
                .writeAndFlush("你好Netty!!");
    }
}
