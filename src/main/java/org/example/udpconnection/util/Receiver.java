package org.example.udpconnection.util;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Receiver extends Thread {
	private DatagramSocket socket;
	private static Receiver instance;

	private boolean stop = false;

	private Receiver() {
	}

	public static Receiver getInstance() {
		if (instance == null) {
			instance = new Receiver();
		}
		return instance;
	}

	@Override
	public void run() {
		while (!stop) {
			try {
				DatagramPacket packet = new DatagramPacket(new byte[24], 24);
				this.socket.receive(packet);

				String message = new String(packet.getData()).trim();
//				System.out.println("Received> " + message);
				// Agrega el mensaje recibido al área de mensajes
				messageArea.appendText("Them: " + message + "\n");
			} catch (SocketException e) {
				if (stop) {
					System.out.println("Socket closed, stopping thread.");
					break;
				}
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void setPort(DatagramSocket socket) throws SocketException {
		this.socket = socket;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public DatagramSocket getSocket() {
		return socket;
	}

}
