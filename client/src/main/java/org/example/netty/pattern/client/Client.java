package org.example.netty.pattern.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.example.netty.template.protocol.handler.PacketCodecHandler;
import org.example.netty.template.protocol.handler.PongHandler;
import org.example.netty.template.protocol.handler.SplitHandler;
import org.example.netty.template.protocol.packet.impl.PingPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author jiangyang
 * @date 2022/7/22 14:43
 */
public class Client {

    private static final Logger LOG = LoggerFactory.getLogger(Client.class);

    private static final int MAX_RETRY = 3;

    public void start() {
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new SplitHandler());
                        socketChannel.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        socketChannel.pipeline().addLast(PongHandler.INSTANCE);
                    }
                });
        connect(bootstrap, "127.0.0.1", 8899, MAX_RETRY);
    }

    private void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                LOG.info("connect|客户端链接成功|{},{}", host, port);
                ((ChannelFuture) future).channel().writeAndFlush(new PingPacket(System.currentTimeMillis()));
            } else if (retry == 0) {
                LOG.info("connect|客户端链接失败|{},{},{}", host, port, MAX_RETRY);
            } else {
                int order = (MAX_RETRY - retry) + 1;
                LOG.info("connect|客户端链接重试|{}", order);
                int delay = order << 1;
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit.SECONDS);
            }
        });
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.start();
    }

}
