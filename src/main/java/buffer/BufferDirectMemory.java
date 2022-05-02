package buffer;

import java.nio.ByteBuffer;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/2 15:00
 * @description:
 */
public class BufferDirectMemory {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(10);
        byteBuffer.put((byte) -1);
    }
}
