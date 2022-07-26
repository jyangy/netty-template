package org.example.netty.template.protocol.constant;

/**
 * @author jiangyang
 * @date 2022/7/22 14:38
 */
public interface Protocol {

    byte[] HEADER = new byte[] {(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff};

}
