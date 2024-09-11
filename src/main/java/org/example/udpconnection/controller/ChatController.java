package org.example.udpconnection.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.example.udpconnection.util.UDPConnection;

import java.io.IOException;

public class ChatController {
	private static ChatController instance;

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

	public static ChatController getInstance() {
		return instance;
	}

	public ChatController() {
		instance = this; // Esto permite obtener la instancia desde otras clases
	}

	// Método para agregar el mensaje recibido al área de texto
	public void appendMessage(String message) {
		messageArea.appendText(message + "\n");
	}

	public void initialize() {
		messageInput.setOnAction(_ -> handleSendMessage(null));

		sendButton.setOnAction(this::handleSendMessage);
	}

	// Método para cerrar todo cuando la ventana se cierra
	public void setStageAndSetupListeners(Stage stage) {
		stage.setOnCloseRequest((WindowEvent _) -> handleWindowClose());
	}

	private void handleWindowClose() {
		// Llamar al método que cierra todos los recursos y mata los hilos
		connection.stopReceiving();
		// Finalmente, cerrar la aplicación JavaFX
		Platform.exit();
	}


	public void handleSendMessage(ActionEvent actionEvent) {
		try {
			String message = "";
			while (message.isEmpty()) {
				message = messageInput.getText();
			}
			connection.sendMessage(System.getProperty("user.name") + ": " + message);
			messageArea.appendText("You: " + message + "\n");
			messageInput.clear();
		} catch (IOException e) {
			appendMessage(e.getMessage());
		}
	}

	public void handleEstablishConnection(ActionEvent actionEvent) {
		try {
			// Obtiene los valores de los campos
			String ip = destinationIP.getText();
			int srcPort = Integer.parseInt(sourcePort.getText());
			int destPort = Integer.parseInt(destinationPort.getText());
			if (this.connection.isConnectionOpen()) {
				this.connection.stopReceiving();
				appendMessage("Connection closed in port " + destPort + ".\n");
			}
			connection.setPorts(ip, srcPort, destPort);
			connection.startReceiving();
			messageArea.appendText("Connection established successfully!\n");
		} catch (Exception e) {
			messageArea.appendText("Establish connection failed: " + e.getMessage() + "\n");
			messageArea.appendText("Please, try again\n");
		}
	}

}
