package br.com.ceunsp.projeto1.visao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ListaAmiguinhoMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("fxml/Lista_amiguinho.fxml"));
			
			Scene scene = new Scene(root, 550, 550);
			scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());
			
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("imagens/listaamiguinhored.png")));//imagem
			primaryStage.setScene(scene);
			primaryStage.setTitle("Lista de Amiguinho");
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
