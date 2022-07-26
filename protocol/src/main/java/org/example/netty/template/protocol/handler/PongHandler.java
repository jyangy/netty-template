package org.example.netty.template.protocol.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.example.netty.template.protocol.packet.impl.PingPacket;
import org.example.netty.template.protocol.packet.impl.PongPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author jiangyang
 * @date 2022/7/22 15:44
 */
@ChannelHandler.Sharable
public class PongHandler extends SimpleChannelInboundHandler<PongPacket> {

    private static final Logger LOG = LoggerFactory.getLogger(PongHandler.class);

    public static final PongHandler INSTANCE = new PongHandler();

    private PongHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PongPacket pongPacket) throws Exception {
        LOG.info("PONG");
        channelHandlerContext.channel().writeAndFlush(new PingPacket(pongPacket.getTimestamp()));
    }
}
