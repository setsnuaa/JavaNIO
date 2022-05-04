package channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Date;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/4 14:36
 * @description:
 */
public class ChannelFileLock {
    public static void main(String[] args) throws IOException, InterruptedException {
        try (RandomAccessFile f = new RandomAccessFile("src/main/resources/test2.txt", "rw");
             FileChannel channel = f.getChannel()) {
            System.out.println(new Date() + " 正在获取锁");
            //锁文件的某一段，false代表独占锁
            FileLock lock = channel.lock(0, 5, false);
            System.out.println(new Date() + " 已获取锁");
            Thread.sleep(2000);
            System.out.println(new Date() + " 释放锁");
            //释放
            lock.release();
        }
    }
}
