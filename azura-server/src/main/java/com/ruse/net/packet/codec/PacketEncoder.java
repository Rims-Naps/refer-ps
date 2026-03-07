package com.ruse.net.packet.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.ruse.net.packet.Packet;
import com.ruse.net.packet.Packet.PacketType;
import com.ruse.net.security.IsaacRandom;

/**
 * An implementation of netty's {@link OneToOneEncoder} to encode outgoing
 * packets.
 *
 * @author relex lawl
 */
public final class PacketEncoder extends OneToOneEncoder {

	/**
	 * The GamePacketEncoder constructor.
	 * 
	 * @param encoder The encoder used for the packets.
	 */
	public PacketEncoder(IsaacRandom encoder) {
		this.encoder = encoder;
	}

	/**
	 * The encoder used for incoming packets.
	 */
	private final IsaacRandom encoder;

	@Override
	protected Object encode(ChannelHandlerContext context, Channel channel, Object message) throws Exception {
		Packet packet = (Packet) message;
		int headerLength = 5;
		int packetLength = packet.getSize();
		if (packet.getOpcode() == -1) {
			return packet.getBuffer();
		}
		ChannelBuffer buffer = ChannelBuffers.buffer(headerLength + packetLength);
		buffer.writeByte((packet.getOpcode() + encoder.nextInt()) & 0xFF);
		buffer.writeInt(packetLength);
		buffer.writeBytes(packet.getBuffer());
		return buffer;
	}

}
