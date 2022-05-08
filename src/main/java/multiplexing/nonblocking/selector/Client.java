package multiplexing.nonblocking.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/7 12:42
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        try (SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 8080))) {
            Scanner sc = new Scanner(System.in);
            System.out.println("已连接服务器");
            while (true) {
                System.out.println("请输入发送给服务器的内容：");
                String line = sc.nextLine();

                //向通道写数据
                channel.write(ByteBuffer.wrap(line.getBytes()));

                ByteBuffer buffer = ByteBuffer.allocate(128);
                //如果另一端不发送数据，会卡在read这里，所以是阻塞的
                channel.read(buffer);
                buffer.flip();
                System.out.println("收到服务器数据：" + new String(buffer.array(), 0, buffer.remaining()));
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
