package org.example.udpconnection.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.example.udpconnection.util.Sender;
import org.example.udpconnection.util.UDPConnection;

import java.io.IOException;
import java.net.SocketException;

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

	@FXML
	private Button sendButton; // Botón de enviar

	// Métdo para iniciar el socket y escuchar mensajes
	public void startConnection() throws SocketException {
		// Obtiene los valores de los campos
		String ip = destinationIP.getText();
		int srcPort = sourcePort.getText().isEmpty() ? 5000 : Integer.parseInt(sourcePort.getText());
		int destPort = destinationPort.getText().isEmpty() ? 5001 : Integer.parseInt(destinationPort.getText());
		connection.setPorts(ip, srcPort, destPort);
		connection.startConnection();
	}


}
