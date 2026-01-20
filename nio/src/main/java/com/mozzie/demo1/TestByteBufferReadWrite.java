package com.mozzie.demo1;

import java.nio.ByteBuffer;

import static com.mozzie.util.ByteBufferUtil.debugAll;

/**
 * @author : mozzie
 * @date : 2026/1/20 23:42
 */
public class TestByteBufferReadWrite {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 0x10);
        debugAll(buffer);
        buffer.put(new byte[]{0x20, 0x30, 0x40});
        debugAll(buffer);
        buffer.flip();
        debugAll(buffer);
        System.out.println(buffer.get());
        debugAll(buffer);
        buffer.compact();
        debugAll(buffer);
    }
}
