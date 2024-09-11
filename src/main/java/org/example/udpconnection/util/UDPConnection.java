package org.example.udpconnection.util;

import java.io.IOException;
import java.net.*;

public class UDPConnection {
	private DatagramSocket socket;
	private static UDPConnection instance;
	private int destinationPort;
	private String destinationIP;

	//	Scanner scanner = new Scanner(System.in);


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

	public synchronized void startConnection() {
		try {
			boolean exit = false;
			Receiver receiver = Receiver.getInstance();
			receiver.setPort(this.socket);

			receiver.start();

			while (!exit) {
				Sender sender = new Sender(this.socket, this.destinationIP, this.destinationPort);
				String message = "???";
				exit = message.equalsIgnoreCase("exit");
				if (exit) {
					receiver.setStop(true);
					receiver.getSocket().close();
				} else {
					sender.sendMessage(message);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
