package multiplexing.blocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/6 12:12
 * @description:阻塞IO
 */
public class Server {
    public static void main(String[] args) {
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open()) {
            //绑定8080端口
            serverChannel.bind(new InetSocketAddress(8080));
            //调用accept()方法，阻塞等待新的连接
            SocketChannel socket = serverChannel.accept();
            //channel可以两端通信，也能获取两端信息
            System.out.println("客户端以连接，IP地址为：" + socket.getRemoteAddress());

            //使用缓冲区接受数据
            ByteBuffer buffer = ByteBuffer.allocate(128);
            socket.read(buffer);
            buffer.flip();
            System.out.println("接收客户端数据：" + new String(buffer.array(), 0, buffer.remaining()));

            //写数据
            socket.write(ByteBuffer.wrap("服务端已收到数据".getBytes()));

            //关闭socket
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
