package channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/3 16:35
 * @description:
 */
public class ChannelFileMap {
    public static void main(String[] args) throws IOException {
        try (RandomAccessFile f = new RandomAccessFile("src/main/resources/test2.txt", "rw");
             FileChannel channel = f.getChannel()) {

            //文件中写入"aaaaaaaaa"
            ByteBuffer buffer = ByteBuffer.wrap("aaaaaaaaa".getBytes());
            channel.write(buffer);

            channel.position(0);

            ByteBuffer buffer1 = ByteBuffer.allocate(20);
            channel.read(buffer1);
            buffer1.flip();
            System.out.println("before:" + new String(buffer1.array(), 0, buffer1.remaining()));
            //替换文件中的一部分
            MappedByteBuffer map = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);
            map.put("hello".getBytes());
            //同步
            map.force();

            channel.position(0);
            buffer1.clear();
            channel.read(buffer1);
            buffer1.flip();
            System.out.println("after:" + new String(buffer1.array(), 0, buffer1.remaining()));
        }
    }
}
