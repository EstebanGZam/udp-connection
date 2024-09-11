package org.example.udpconnection.util;

import java.io.IOException;
import java.net.*;

public class UDPConnection {
	private DatagramSocket socket;
	private static UDPConnection instance;
	private int destinationPort;
	private String destinationIP;
	private Receiver receiver;


	public static UDPConnection getInstance() {
		if (instance == null) {
			instance = new UDPConnection();
		}
		return instance;
	}

	public void setPorts(String destinationIP, int connectionPort, int destinationPort) throws SocketException {
		if (this.socket != null && !this.socket.isClosed()) this.socket.close();
		this.socket = new DatagramSocket(connectionPort);
		this.destinationPort = destinationPort;
		this.destinationIP = destinationIP;
	}

	// Detener la recepción de mensajes
	public synchronized void stopReceiving() {
		if (this.socket != null && !this.socket.isClosed()) {
			this.socket.close(); // Esto detendrá el socket y, por lo tanto, el hilo que lo usa
		}
		if (this.receiver != null) {
			closeReceiverProcess();
		}
	}

	// Empezar la recepción de mensajes
	public synchronized void startReceiving() {
		System.out.println(this.receiver);
		this.receiver = new Receiver();
		this.receiver.setPort(this.socket);
		this.receiver.start();
	}

	public void sendMessage(String message) throws IOException {
		validateConnection();
		Sender sender = new Sender(this.socket, this.destinationIP, this.destinationPort);
		sender.sendMessage(message);
	}

	public boolean isConnectionOpen() {
		return this.receiver != null;
	}

	private void validateConnection() throws IOException {
		if (this.socket == null || this.socket.isClosed() || this.destinationIP == null || this.destinationPort == 0) {
			throw new IOException("A connection has not yet been established.");
		}
	}

	private void closeReceiverProcess() {
		this.receiver.getSocket().close();
		this.receiver.interrupt();
	}

	public int getDestinationPort() {
		return destinationPort;
	}

}
