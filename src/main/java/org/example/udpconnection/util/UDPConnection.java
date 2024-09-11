package org.example.udpconnection.util;

import java.io.IOException;
import java.net.*;

public class UDPConnection {
	private DatagramSocket socket;
	private static UDPConnection instance;
	private int destinationPort;
	private String destinationIP;

	public static UDPConnection getInstance() {
		if (instance == null) {
			instance = new UDPConnection();
		}
		return instance;
	}

	public void setPorts(String destinationIP, int connectionPort, int destinationPort) throws SocketException {
		this.socket = new DatagramSocket(connectionPort);
		this.destinationPort = destinationPort;
		this.destinationIP = destinationIP;
	}

	public synchronized void startReceiving() throws SocketException {
		Receiver receiver = Receiver.getInstance();
		receiver.setPort(this.socket);
		receiver.start();
	}

	public void sendMessage(String message) throws IOException {
		Sender sender = new Sender(this.socket, this.destinationIP, this.destinationPort);
		sender.sendMessage(message);
	}
}
