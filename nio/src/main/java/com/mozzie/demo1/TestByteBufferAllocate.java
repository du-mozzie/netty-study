package com.mozzie.demo1;

import java.nio.ByteBuffer;

/**
 * @author : mozzie
 * @date : 2026/1/21 0:12
 */
public class TestByteBufferAllocate {
    public static void main(String[] args) {
        System.out.println(ByteBuffer.allocate(10).getClass());
        System.out.println(ByteBuffer.allocateDirect(10).getClass());
        /**
         * class java.nio.HeapByteBuffer 堆内分配
         * class java.nio.DirectByteBuffer 直接内存分配 少一次拷贝过程
         */
    }
}
