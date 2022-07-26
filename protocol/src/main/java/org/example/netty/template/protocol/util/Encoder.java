package org.example.netty.template.protocol.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * @author jiangyang
 * @date 2022/7/22 14:40
 */
public class Encoder {

    private Encoder() {
    }

    public static byte[] short2Bytes(short x) {
        return short2Bytes(x, ByteOrder.BIG_ENDIAN);
    }

    public static byte[] short2BytesLE(short x) {
        return short2Bytes(x, ByteOrder.LITTLE_ENDIAN);
    }

    public static byte[] int2Bytes(int x) {
        return int2Bytes(x, ByteOrder.BIG_ENDIAN);
    }

    public static byte[] int2BytesLE(int x) {
        return int2Bytes(x, ByteOrder.LITTLE_ENDIAN);
    }

    public static byte[] double2Bytes(double x) {
        return double2Bytes(x, ByteOrder.BIG_ENDIAN);
    }

    public static byte[] double2BytesLE(double x) {
        return double2Bytes(x, ByteOrder.LITTLE_ENDIAN);
    }

    public static byte[] long2Bytes(long x) {
        return long2Bytes(x, ByteOrder.BIG_ENDIAN);
    }

    public static byte[] long2BytesLE(long x) {
        return long2Bytes(x, ByteOrder.LITTLE_ENDIAN);
    }

    private static byte[] short2Bytes(short x, ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(byteOrder);
        buffer.putShort(x);
        return buffer.array();
    }

    private static byte[] int2Bytes(int x, ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.order(byteOrder);
        buffer.putInt(x);
        return buffer.array();
    }

    private static byte[] double2Bytes(double x, ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(byteOrder);
        buffer.putDouble(x);
        return buffer.array();
    }

    private static byte[] long2Bytes(long x, ByteOrder byteOrder) {
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.order(byteOrder);
        buffer.putLong(x);
        return buffer.array();
    }

}
