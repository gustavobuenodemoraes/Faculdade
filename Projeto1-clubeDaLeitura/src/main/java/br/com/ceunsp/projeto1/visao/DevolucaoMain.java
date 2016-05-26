package br.com.ceunsp.projeto1.visao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DevolucaoMain extends Application{
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("fxml/Devolucao.fxml"));
			
			Scene scene = new Scene(root, 420, 250);
			scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Cadastro de revistas");
			primaryStage.setMaximized(false);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
