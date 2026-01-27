package com.mozzie.hello;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @author : mozzie
 * @date : 2026/1/28 0:00
 */
public class HelloServer {

    public static void main(String[] args) {
        // 启动器，负责组装Netty组件 启动服务器
        new ServerBootstrap()
                // BossEventLoop 和 WorkerEventLoop (selector, thread) group 组
                .group(new NioEventLoopGroup())
                // 选择服务器的ServerSocketChannel实现
                .channel(NioServerSocketChannel.class) // OIO BIO
                // boss 负责连接请求 worker(child) 负责读写 决定worker能执行哪些操作
                .childHandler(
                        // channel代表和客户端读写数据的通道 Initializer 初始化 负责添加handler
                        new ChannelInitializer<NioSocketChannel>() {
                            @Override
                            protected void initChannel(NioSocketChannel sc) {
                                // 添加具体的handler
                                // StringDecoder 解码器 负责将ByteBuf转换为String
                                sc.pipeline().addLast(new StringDecoder());
                                // ChannelInboundHandlerAdapter 适配器 负责处理业务逻辑
                                sc.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) {
                                        // 打印上一个handler处理后的结果
                                        System.out.println(msg);
                                    }
                                });
                            }
                        })
                // 绑定端口
                .bind(7000);
    }

}
