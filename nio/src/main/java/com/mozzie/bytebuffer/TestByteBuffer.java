package com.mozzie.bytebuffer;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 首次 测试
 * <p>
 * 内容：
 * <p>
 * 1. allocate 分配内存
 * <p>
 * 2. read 从文件中 读取
 * <p>
 * 3. flip 切换读模式
 * <p>
 * 4. get  获取字节 并转字符 打印
 * <p>
 * 5. compact 或 clear 调整 position 和 limit 进行 写模式
 *
 * @author : mozzie
 * @date : 2026/1/20 1:45
 */
@Slf4j
public class TestByteBuffer {

    /**
     * 注意：文件可能 很大， 缓冲区不能跟随文件的大小 设置的很大
     */
    public static void main(String[] args) {
        // 创建一个文件输入流
        try (FileChannel fileChannel = new FileInputStream("nio/file/TestByteBuffer.txt").getChannel()) {
            // 创建一个输入缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(5);
            while (true) {
                // 从输入流读取数据到缓冲区
                int len = fileChannel.read(buffer);
                log.debug("此次 读取到的字节长度是 {}", len);
                if (len == -1) {
                    break;
                }
                // 切换至 读模式 [ position指针指向开头， limit指向写入的最后位置 (内存长度) ]
                buffer.flip();
                // 遍历缓冲区, 检查是否还有数据没有读取完
                while (buffer.hasRemaining()) {
                    System.out.println((char) buffer.get());
                }
                buffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
