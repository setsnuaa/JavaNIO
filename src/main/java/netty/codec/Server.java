package netty.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Arrays;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/17 19:26
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
                        socketChannel.pipeline()
                                .addLast(new LengthFieldBasedFrameDecoder(1024, 0, 4))
                                .addLast(new StringDecoder())
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        //经过上一级的String解码器后，msg就是String类型了
                                        System.out.println(msg);
                                        ctx.channel().writeAndFlush("已收到");
                                    }
                                })
                                .addLast(new StringEncoder());
                    }
                });

        bootstrap.bind(8080);
    }
}
