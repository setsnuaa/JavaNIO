package buffer;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/2 14:41
 * @description:
 */
public class BufferByteAndChar {
    public static void main(String[] args) {
        //ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        byteBuffer.put((byte) 127);
        byteBuffer.put((byte) -1);
        byteBuffer.put((byte) -1);
        byteBuffer.put((byte) -1);
        byteBuffer.flip();
        System.out.println("position=" + byteBuffer.position() + " limit=" + byteBuffer.limit());
        System.out.println(byteBuffer.getInt() + " Integer.MAX_VALUE=" + Integer.MAX_VALUE);

        //CharBuffer
        //这种方式底层存的字符串
        CharBuffer stringCharBuffer = CharBuffer.wrap("helloWorld");
        while (stringCharBuffer.hasRemaining()) {
            System.out.print(stringCharBuffer.get());
        }
        //这种方式底层是char[]
        CharBuffer charBuffer = CharBuffer.wrap(new char[]{'h', 'i'});
        System.out.println("\n" + Arrays.toString(charBuffer.array()));
    }
}
