package org.example.udpconnection.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

import javafx.application.Platform;
import org.example.udpconnection.controller.ChatController;


public class Receiver extends Thread {
	private DatagramSocket socket;

	private boolean stop = false;

	@Override
	public void run() {
		while (!stop) {
			try {
				DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
				this.socket.receive(packet);

				String message = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8).trim();

				// Procesar el mensaje en el hilo de la UI utilizando Platform.runLater()
				// Llamado al método del controlador para procesar el mensaje
				Platform.runLater(() -> ChatController.getInstance().appendMessage(message));

			} catch (SocketException e) {
				stop = true;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public void setPort(DatagramSocket socket) {
		this.socket = socket;
	}

	public DatagramSocket getSocket() {
		return socket;
	}

}
