module org.example.udpconnection {
	requires javafx.controls;
	requires javafx.fxml;


	opens org.example.udpconnection to javafx.fxml;
	exports org.example.udpconnection;
	exports org.example.udpconnection.controller;
	opens org.example.udpconnection.controller to javafx.fxml;
}