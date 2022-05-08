package multiplexing.nonblocking.reactor;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/8 15:48
 * @description:
 */
public class SubReactor implements Closeable, Runnable {
    private final Selector selector;
    private static final int MAX_SELECTOR = 2;
    //创建4个sub reactor同时工作，每个reactor都有一个selector
    private static final ExecutorService POOL = Executors.newFixedThreadPool(MAX_SELECTOR);
    private static final SubReactor[] reactors = new SubReactor[MAX_SELECTOR];
    //轮询
    public static int selectedIndex = 0;

    static {
        for (int i = 0; i < MAX_SELECTOR; i++) {
            try {
                reactors[i] = new SubReactor();
                POOL.submit(reactors[i]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private SubReactor() throws IOException {
        selector = Selector.open();
    }

    @Override
    public void close() throws IOException {
        selector.close();
    }

    @Override
    public void run() {
        try {
            while (true) {
                int count = selector.select();
                System.out.println(Thread.currentThread().getName() + " >> 监听到 " + count + " 个事件");
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    this.dispatch(iterator.next());
                    iterator.remove();
                }
                //睡3s钟是为了防止register()之前selector阻塞在select()方法处
                Thread.sleep(3000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //轮询获取下一个selector
    public static Selector nextSelector() {
        Selector selector = reactors[selectedIndex].selector;
        selectedIndex = (selectedIndex + 1) % MAX_SELECTOR;
        return selector;
    }

    private void dispatch(SelectionKey key) {
        Object obj = key.attachment();
        if (obj instanceof Runnable) {
            ((Runnable) obj).run();
        }
    }
}
