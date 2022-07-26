package org.example.netty.template.protocol.packet;

import io.netty.buffer.ByteBuf;

/**
 * @author jiangyang
 * @date 2022/7/22 10:56
 */
public interface Packet {

    byte getCommand();

    Packet of(ByteBuf byteBuf);

    byte[] toBytes();

}
