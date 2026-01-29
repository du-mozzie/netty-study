package com.mozzie.eventloop;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * @author : mozzie
 * @date : 2026/1/30 1:55
 */
@Slf4j
public class TestEventLoop {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup(2); // IO 普通任务 定时任务
        // EventLoopGroup group = new DefaultEventLoop(); // 普通任务 定时任务
        
        // 使用一个链表保存 EventLoop
        log.debug(group.next().toString());
        log.debug(group.next().toString());
        log.debug(group.next().toString());
        log.debug(group.next().toString());
        
        // 创建一个普通任务
        group.next().execute(() -> log.debug("normal run"));
        
        // 创建一个定时任务
        group.next().scheduleAtFixedRate(() -> {
            log.debug("schedule run");
        }, 1, 1, java.util.concurrent.TimeUnit.SECONDS);
    }
}
