package multiplexing.nonblocking.reactor;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/8 14:49
 * @description:
 */
public class Acceptor implements Runnable {
    private final ServerSocketChannel serverSocketChannel;

    public Acceptor(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    public void run() {
        try {
            SocketChannel channel = serverSocketChannel.accept();
            System.out.println("客户端已连接，IP地址为：" + channel.getRemoteAddress());

            channel.configureBlocking(false);
            //当读事件就绪时，reactor分发的时候调用Handler处理读事件
            Selector selector = SubReactor.nextSelector();
            selector.wakeup();
            channel.register(selector, SelectionKey.OP_READ, new Handler(channel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
