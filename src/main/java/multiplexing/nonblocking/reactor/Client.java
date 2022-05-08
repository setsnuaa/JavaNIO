package multiplexing.nonblocking.reactor;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/8 15:22
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
