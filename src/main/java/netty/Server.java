package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.nio.charset.StandardCharsets;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/13 14:53
 * @description:
 */
public class Server {
    public static void main(String[] args) {
        //创建BossGroup和WorkerGroup
        EventLoopGroup bossGroup = new NioEventLoopGroup(), workerGroup = new NioEventLoopGroup();

        //启动引导
        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //获取流水线，处理客户端数据时，实际上是像流水线一样在处理
                        //这个流水线上可以有很多Handler
                        socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                System.out.println(Thread.currentThread().getName() + " >>接收到客户端发送的数据：" + buf.toString(StandardCharsets.UTF_8));
                                //通过上下文可以直接发送数据回去，注意要writeAndFlush才能让客户端立即收到
                                ctx.writeAndFlush(Unpooled.wrappedBuffer("服务端已收到".getBytes()));
                            }
                        });
                    }
                });

        bootstrap.bind(8080);
    }
}
