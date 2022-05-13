package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/13 15:33
 * @description:
 */
public class BufferPooled {
    public static void main(String[] args) {
        PooledByteBufAllocator allocator = PooledByteBufAllocator.DEFAULT;
        ByteBuf buffer1 = allocator.directBuffer(4);
        buffer1.writeChar('a');
        System.out.println(buffer1.readChar());
        buffer1.release();//释放缓冲区

        ByteBuf buffer2 = allocator.directBuffer(4);
        System.out.println(buffer1 == buffer2);//true，说明是同一个内存地址
    }
}
