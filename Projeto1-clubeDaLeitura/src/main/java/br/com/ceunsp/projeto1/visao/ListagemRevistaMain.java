package br.com.ceunsp.projeto1.visao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ListagemRevistaMain extends Application{
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("fxml/Listagem_revista.fxml"));


			Scene scene = new Scene(root,500,550);

			scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("imagens/listaRevistared.png")));//imagem
			primaryStage.setScene(scene);
			primaryStage.setTitle("Listagem de Revistas");
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
