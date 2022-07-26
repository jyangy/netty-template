package org.example.netty.template.protocol.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.example.netty.template.protocol.constant.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 *
 * |0xff 0xff 0xff 0xff|command|length|data|
 *
 * @author jiangyang
 * @date 2022/7/22 15:32
 */
public class SplitHandler extends LengthFieldBasedFrameDecoder {

    private static final Logger LOG = LoggerFactory.getLogger(SplitHandler.class);

    private static final int MAX_FRAME_LENGTH = Integer.MAX_VALUE;
    private static final int LENGTH_FIELD_LENGTH = 2;
    private static final int LENGTH_FIELD_OFFSET = 5;

    public SplitHandler() {
        super(MAX_FRAME_LENGTH, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        byte[] header = new byte[4];
        in.getBytes(in.readerIndex(), header);
        if (!Arrays.equals(Protocol.HEADER, header)) {
            ctx.channel().close();
            LOG.error("decode|非法的帧头");
            return null;
        }
        return super.decode(ctx, in);
    }
}
