package org.example.netty.template.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import org.example.netty.template.protocol.handler.PacketCodecHandler;
import org.example.netty.template.protocol.handler.PingHandler;
import org.example.netty.template.protocol.handler.SplitHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @author jiangyang
 * @date 2022/7/22 11:21
 */
public class Server {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    private static final int PORT = 8899;

    private EventLoopGroup boss;
    private EventLoopGroup worker;

    public void start() {
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap
                .group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new SplitHandler());
                        socketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                super.channelRead(ctx, msg);
                            }
                        });
                                socketChannel.pipeline().addLast(PacketCodecHandler.INSTANCE);
                        socketChannel.pipeline().addLast(PingHandler.INSTANCE);
                    }
                });
        try {
            ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();
            LOG.info("StartServer|Server启动|{}", PORT);
            channelFuture.channel().closeFuture().addListener((ChannelFutureListener) future -> destroy());
        } catch (Exception e) {
            destroy();
            LOG.error("StartServer|Server启动出错", e);
        }

    }

    private void destroy() {
        Optional.ofNullable(boss).ifPresent(EventExecutorGroup::shutdownGracefully);
        Optional.ofNullable(worker).ifPresent(EventExecutorGroup::shutdownGracefully);
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

}
