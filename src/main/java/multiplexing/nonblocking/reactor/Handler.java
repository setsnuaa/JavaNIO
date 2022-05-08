package multiplexing.nonblocking.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/8 14:46
 * @description:
 */
public class Handler implements Runnable {
    private final SocketChannel channel;
    private static final ExecutorService POOL = Executors.newFixedThreadPool(2);

    public Handler(SocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(128);
            channel.read(buffer);
            buffer.flip();
            //申请一个线程处理
            POOL.submit(() -> {
                try {
                    System.out.println("收到客户端数据：" + new String(buffer.array(), 0, buffer.remaining()));
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
