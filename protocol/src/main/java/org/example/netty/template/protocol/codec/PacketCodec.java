package org.example.netty.template.protocol.codec;

import io.netty.buffer.ByteBuf;
import org.example.netty.template.protocol.constant.Command;
import org.example.netty.template.protocol.constant.Protocol;
import org.example.netty.template.protocol.packet.Packet;
import org.example.netty.template.protocol.packet.impl.PingPacket;
import org.example.netty.template.protocol.packet.impl.PongPacket;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangyang
 * @date 2022/7/22 11:07
 */
public class PacketCodec {

    public static final PacketCodec INSTANCE = new PacketCodec();
    private final Map<Byte, Class<? extends Packet>> packetMap;

    private PacketCodec() {
        packetMap = new HashMap<>();
        packetMap.put(Command.PING, PingPacket.class);
        packetMap.put(Command.PONG, PongPacket.class);
    }

    public Packet decode(ByteBuf byteBuf) {
        byteBuf.skipBytes(4);
        byte command = byteBuf.readByte();
        byteBuf.readShort();
        try {
            Packet packet = packetMap.get(command).newInstance();
            return packet.of(byteBuf);
        } catch (Exception e) {

        }
        return null;
    }

    public void encode(ByteBuf byteBuf, Packet packet) {
        byteBuf.writeBytes(Protocol.HEADER);
        byteBuf.writeByte(packet.getCommand());
        byte[] bytes = packet.toBytes();
        byteBuf.writeShort(bytes.length);
        byteBuf.writeBytes(bytes);
    }
}
