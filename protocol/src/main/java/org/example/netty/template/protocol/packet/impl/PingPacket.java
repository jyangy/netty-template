package org.example.netty.template.protocol.packet.impl;

import io.netty.buffer.ByteBuf;
import org.example.netty.template.protocol.constant.Command;
import org.example.netty.template.protocol.packet.Packet;
import org.example.netty.template.protocol.util.Encoder;

/**
 * @author jiangyang
 * @date 2022/7/22 14:09
 */
public class PingPacket implements Packet {

    private long timestamp;

    public PingPacket() {
    }

    public PingPacket(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public byte getCommand() {
        return Command.PING;
    }

    @Override
    public Packet of(ByteBuf byteBuf) {
        this.timestamp = byteBuf.readLong();
        return this;
    }

    @Override
    public byte[] toBytes() {
        return Encoder.long2Bytes(timestamp);
    }

    @Override
    public String toString() {
        return "PingPacket{" +
                "timestamp=" + timestamp +
                '}';
    }
}
