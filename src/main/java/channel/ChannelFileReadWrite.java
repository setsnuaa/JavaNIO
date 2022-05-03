package channel;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/3 15:42
 * @description:
 */
public class ChannelFileReadWrite {
    public static void main(String[] args) throws IOException {
        //既可以写也可以读
        try (RandomAccessFile dataStream = new RandomAccessFile("src/main/resources/test.txt", "rw");
             FileChannel channel = dataStream.getChannel()) {

            //write:buffer->file
            ByteBuffer buffer = ByteBuffer.wrap("hello world".getBytes());
            channel.write(buffer);

            //position相当于channel下一次操作的字节的位置，在这个例子中也相当于文件的字节位置
            //刚才将buffer中的数据写入文件，position也往后移动了
            channel.position(0);

            //read:file->buffer
            ByteBuffer buffer1 = ByteBuffer.allocate(40);
            channel.read(buffer1);
            buffer1.flip();
            System.out.println(new String(buffer1.array(), 0, buffer1.remaining()));
        }
    }
}
