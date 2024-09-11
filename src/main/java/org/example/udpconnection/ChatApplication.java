package org.example.udpconnection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import org.example.udpconnection.controller.ChatController;

public class ChatApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Cargar la interfaz desde el archivo FXML
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/udpconnection/chat-view.fxml"));
		Parent root = loader.load();

		// Obtener el controlador
		ChatController controller = loader.getController();

		// Iniciar el socket para recibir mensajes
		controller.startConnection();

		// Crear y mostrar la ventana
		primaryStage.setTitle("Chat");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
