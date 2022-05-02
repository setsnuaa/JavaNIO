package buffer;

import java.nio.IntBuffer;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/2 14:21
 * @description:
 */
public class BufferReadOnly {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.wrap(new int[]{1, 2, 3});
        IntBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }
    }
}
