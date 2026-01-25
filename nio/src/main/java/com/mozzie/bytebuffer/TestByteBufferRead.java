package com.mozzie.bytebuffer;

import java.nio.ByteBuffer;

/**
 * @author : mozzie
 * @date : 2026/1/21 0:14
 */
public class TestByteBufferRead {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd'});
        buffer.flip();

        // 从头开始读
        // buffer.get(new byte[4]);
        // debugAll(buffer);
        // // 把position设回0，但不改变limit
        // buffer.rewind();
        // debugAll(buffer);

        // mark & reset
        System.out.println((char) buffer.get()); // a
        System.out.println((char) buffer.get()); // b
        // mark属性记录当前position
        buffer.mark();
        System.out.println((char) buffer.get()); // c
        System.out.println((char) buffer.get()); // d
        // 将position设回mark所记录的位置
        buffer.reset();
        System.out.println((char) buffer.get()); // c
        System.out.println((char) buffer.get()); // d
        
        // get(i) 读取第i个位置的值，position不会改变
        System.out.println((char) buffer.get(3)); // 0
    }
}
