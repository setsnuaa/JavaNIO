package netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @name:
 * @author:pan.gefei
 * @date:2022/5/17 19:35
 * @description:
 */
public class DecoderTest extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        System.out.println("正在解码...");
        String line = byteBuf.toString(StandardCharsets.UTF_8);
        //一条消息可以被解码成多条消息，list会传递给流水线的下一个handler
        list.add(line);
        //list.add(line+"2");
        System.out.println("解码成功！");
    }
}
