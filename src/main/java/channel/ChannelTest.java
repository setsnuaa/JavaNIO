package channel;

import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/3 14:59
 * @description:
 */
public class ChannelTest {
    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        ReadableByteChannel channel = Channels.newChannel(System.in);
        //通过channel将数据写到buffer中
        while (true) {
            //数据：终端输入->channel->buffer
            channel.read(buffer);
            System.out.println(buffer);
            //读之前先flip，flip有点像只读取写到buffer的部分
            buffer.flip();
            System.out.println(new String(buffer.array(), 0, buffer.remaining()));
            buffer.clear();
        }
    }
}
