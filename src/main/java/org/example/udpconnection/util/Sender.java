package org.example.udpconnection.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Sender {
	private final DatagramSocket socket;
	private final int destinationPort;
	private final InetAddress destinationAddress;

	public Sender(DatagramSocket socket, String destinationAddress, int destinationPort) throws UnknownHostException {
		this.socket = socket;
		this.destinationPort = destinationPort;
		this.destinationAddress = InetAddress.getByName(destinationAddress);
	}

	public void sendMessage(String message) throws IOException {
		sendDatagram(message, this.destinationAddress, this.destinationPort);
	}

	private void sendDatagram(String message, InetAddress destinationAddress, int destinationPort) throws IOException {
		DatagramPacket packet = new DatagramPacket(message.getBytes(StandardCharsets.UTF_8), message.length(), destinationAddress, destinationPort);

		socket.send(packet);
	}

}

