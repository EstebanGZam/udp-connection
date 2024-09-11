package org.example.udpconnection.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.udpconnection.util.UDPConnection;

import java.io.IOException;

public class ChatController {
	private final UDPConnection connection = UDPConnection.getInstance();

	@FXML
	private TextField destinationIP; // Campo para la IP de destino

	@FXML
	private TextField sourcePort; // Campo para el puerto de origen

	@FXML
	private TextField destinationPort; // Campo para el puerto de destino

	@FXML
	private TextField messageInput; // Campo para escribir el mensaje

	@FXML
	private TextArea messageArea; // Área de mensajes recibidos

	public void handleSendMessage(ActionEvent actionEvent) {
		try {
			String message = "";
			while (message.isEmpty()) {
				message = messageInput.getText();
			}
			connection.sendMessage(message);
			messageArea.appendText("You: " + message + "\n");
			messageInput.clear();
		} catch (IOException e) {
			messageArea.appendText(e.getMessage() + "\n");
		}
	}

	public void handleEstablishConnection(ActionEvent actionEvent) {
		try {
			// Obtiene los valores de los campos
			String ip = destinationIP.getText();
			int srcPort = Integer.parseInt(sourcePort.getText());
			int destPort = Integer.parseInt(destinationPort.getText());
			connection.setPorts(ip, srcPort, destPort);
			connection.startReceiving();
			messageArea.appendText("Connection established successfully!\n");
		} catch (Exception e) {
			messageArea.appendText("Establish connection failed: " + e.getMessage() + "\n");
			messageArea.appendText("Please, try again\n");
		}
	}

}
