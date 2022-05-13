package multiplexing.nonblocking.reactor;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/8 14:56
 * @description:主Reactor只负责accept事件
 */
public class Reactor implements Closeable, Runnable {
    private final ServerSocketChannel serverSocketChannel;
    private final Selector selector;

    public Reactor() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        selector = Selector.open();
    }

    @Override
    public void close() throws IOException {
        serverSocketChannel.close();
        selector.close();
    }

    @Override
    public void run() {
        try {
            serverSocketChannel.bind(new InetSocketAddress(8080));

            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, new Acceptor(serverSocketChannel));

            while (true) {
                int count = selector.select();
                System.out.println("监听到 " + count + " 个事件");
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    this.dispatch(iterator.next());
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void dispatch(SelectionKey key) {
        //之前注册的时候事件和事件对应的处理对象绑定在一起
        //用attachment()方法获得处理事件的对象
        Object att = key.attachment();
        if (att instanceof Runnable) {
            ((Runnable) att).run();
            System.out.println("Acceptor done");
        }
    }
}
