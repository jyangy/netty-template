package org.example.netty.template.protocol.packet.impl;

import io.netty.buffer.ByteBuf;
import org.example.netty.template.protocol.constant.Command;
import org.example.netty.template.protocol.packet.Packet;
import org.example.netty.template.protocol.util.Encoder;

/**
 * @author jiangyang
 * @date 2022/7/22 14:09
 */
public class PongPacket implements Packet {

    private long timestamp;

    public PongPacket() {
    }

    public PongPacket(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public byte getCommand() {
        return Command.PONG;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "PongPacket{" +
                "timestamp=" + timestamp +
                '}';
    }
}
