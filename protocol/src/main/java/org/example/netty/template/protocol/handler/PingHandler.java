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
 * @date 2022/7/22 15:05
 */
@ChannelHandler.Sharable
public class PingHandler extends SimpleChannelInboundHandler<PingPacket> {

    private static final Logger LOG = LoggerFactory.getLogger(PingHandler.class);

    public static final PingHandler INSTANCE = new PingHandler();

    private PingHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, PingPacket pingPacket) throws Exception {
        LOG.info("PING");
        channelHandlerContext.channel().writeAndFlush(new PongPacket(pingPacket.getTimestamp()));
    }
}
