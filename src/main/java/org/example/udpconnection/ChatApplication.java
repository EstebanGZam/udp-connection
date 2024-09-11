package org.example.udpconnection;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.udpconnection.controller.ChatController;

import java.io.IOException;

public class ChatApplication extends Application {

	@Override
	public void start(Stage stage) throws IOException {
		FXMLLoader loader = renderView("chat-view.fxml");
		ChatController controller = loader.getController();
		controller.setStageAndSetupListeners(stage);
		controller.initialize();
	}

	public static FXMLLoader renderView(String fxml) throws IOException {
		FXMLLoader fxmlLoader;
		fxmlLoader = new FXMLLoader(
				ChatApplication.class.getResource(fxml)
		);

		Parent parent = fxmlLoader.load();
		Scene scene = new Scene(parent);
		Stage stage = new Stage();
		stage.setTitle("UDP Chat");

		// Establecer un tamaño fijo para la ventana
		stage.setScene(scene);
		stage.setResizable(false);  // Evitar que el usuario redimensione la ventana

		stage.show();

		return fxmlLoader;
	}

	public static void main(String[] args) {
		launch();
	}
}