package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/17 15:27
 * @description:
 */
public class Client {
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        //配置客户端
        bootstrap
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel
                                .pipeline()
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        ByteBuf buf = (ByteBuf) msg;
                                        //这条消息是线程池中的线程发的
                                        System.out.println(Thread.currentThread().getName() + ">>收到服务器发回的消息：" + buf.toString(StandardCharsets.UTF_8));
                                    }
                                });
                    }
                });
        //下面这个操作是异步的，调用之后会继续执行之后的代码。
        Channel channel = bootstrap.connect("localhost", 8080).channel();

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                //这条消息是main发的
                System.out.println(Thread.currentThread().getName() + ">>请输入要发送给服务端的内容：");
                String line = scanner.nextLine();
                if (line.isEmpty()) continue;
                channel.writeAndFlush(Unpooled.wrappedBuffer(line.getBytes()));
            }
        }
    }
}
