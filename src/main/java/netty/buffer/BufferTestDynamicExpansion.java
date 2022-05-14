package netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/13 15:16
 * @description:
 */
public class BufferTestDynamicExpansion {
    public static void main(String[] args) {
        ByteBuf buf = Unpooled.buffer(4);
        System.out.println("扩容之前：" + buf.capacity());//4
        buf.writeCharSequence("abcdefg", StandardCharsets.UTF_8);
        System.out.println("扩容之后：" + buf.capacity());//64
    }
}
