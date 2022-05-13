package multiplexing.nonblocking.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/7 12:42
 * @description:
 */
public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
             Selector selector = Selector.open()) {
            serverSocketChannel.bind(new InetSocketAddress(8080));
            //使用selector需要采用非阻塞方式
            //因为事件就绪后也可能阻塞
            //比如读就绪，但是读的时候出现错误校验和，发生阻塞
            serverSocketChannel.configureBlocking(false);
            //将selector注册到serverSocketChannel中
            //第二个参数是监听的事件，只有发生对应事件才进行选择，不是所有Channel都支持以下事件
            //SelectionKey.OP_CONNECT --- 连接就绪事件
            //SelectionKey.OP_ACCEPT --- 接收连接事件
            //SelectionKey.OP_READ --- 读就绪事件
            //SelectionKey.OP_WRITE --- 写就绪事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                //每次选择都可能出现多个就绪事件，没有事件发生会暂时阻塞
                int count = selector.select();
                System.out.println("监听到 " + count + " 个事件");
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        SocketChannel channel = serverSocketChannel.accept();
                        System.out.println("客户端已连接，IP地址为：" + channel.getRemoteAddress());
                        //和客户端的连接建好了，将选择器注册到这个连接中，监听就绪事件
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(128);
                        if (channel.read(buffer) < 0) {
                            System.out.println("客户端断开连接，IP地址为：" + channel.getRemoteAddress());
                            channel.close();
                            continue;
                        }
                        buffer.flip();
                        System.out.println("接收到客户端数据：" + new String(buffer.array(), 0, buffer.remaining()));

                        channel.write(ByteBuffer.wrap("服务端已收到数据".getBytes()));
                    }
                    iterator.remove();
                }
            }
        }
    }
}
