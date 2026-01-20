package com.mozzie.demo1;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * @author : mozzie
 * @date : 2026/1/21 0:31
 */
public class TestByteBuffer2String {
    public static void main(String[] args) throws UnsupportedEncodingException {
        ByteBuffer buffer1 = StandardCharsets.UTF_8.encode("你好");

        ByteBuffer buffer2 = ByteBuffer.allocate(10);
        buffer2.put("你好".getBytes(StandardCharsets.UTF_8));

        ByteBuffer buffer3 = ByteBuffer.wrap("你好".getBytes(StandardCharsets.UTF_8));

        System.out.println(StandardCharsets.UTF_8.decode(buffer1));
    }
}
