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
            //这里需要注意，如果selector调用select()方法阻塞了
            //register()也会阻塞，所以在register()之前需要保证selector没有阻塞
            //实际上在register()之前调用wakeup()也可能阻塞，因为subReactor的运行速度太快
            //在register()之前又阻塞在select()处了
            channel.register(selector, SelectionKey.OP_READ, new Handler(channel));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
