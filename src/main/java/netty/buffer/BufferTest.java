package netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Arrays;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/13 14:57
 * @description:
 */
public class BufferTest {
    public static void main(String[] args) {
        //创建初始容量为10的ByteBuf，Unpooled是用于快速生成ByteBuf的工具类
        //readIndex=0,writeIndex=0
        ByteBuf buf = Unpooled.buffer(10);
        System.out.println("初始状态：" + Arrays.toString(buf.array()));

        //readIndex=0,writeIndex=4
        buf.writeInt(-888888888);
        System.out.println("写入int后：" + Arrays.toString(buf.array()));

        //不用flip()，直接读
        //readIndex=2,writeIndex=4
        buf.readShort();
        System.out.println("读取short后：" + Arrays.toString(buf.array()));

        //丢弃，将可读部分丢到最前面，并且读写指针也向前移动丢弃的距离
        //readIndex=0,writeIndex=2
        buf.discardReadBytes();
        System.out.println("丢弃后：" + Arrays.toString(buf.array()));

        //readIndex=0,writeIndex=0
        buf.clear();
        System.out.println("清空后：" + Arrays.toString(buf.array()));
    }
}
