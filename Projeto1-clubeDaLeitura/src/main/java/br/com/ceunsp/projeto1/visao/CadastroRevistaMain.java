package br.com.ceunsp.projeto1.visao;

import java.io.IOException;

import br.com.ceunsp.projeto1.controller.CadastroRevistaController;
import br.com.ceunsp.projeto1.modelo.Revista;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CadastroRevistaMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("fxml/Cadastro_revista.fxml"));

			Scene scene = new Scene(root, 420, 350);
			scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("imagens/cadastrored.png")));// imagem

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

	public void abrirCadastroRevista(Revista revista) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Cadastro_revista.fxml"));
		
			Parent root = loader.load();

			Scene scene = new Scene(root, 420, 350);
			scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());
			Stage primaryStage = new Stage();
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("imagens/cadastrored.png")));// imagem

			primaryStage.setScene(scene);
			primaryStage.setTitle("Cadastro de revistas");
			primaryStage.setMaximized(false);
			primaryStage.setResizable(false);

			// Define a pessoa no controller.
			CadastroRevistaController controller = loader.getController();
			controller.setRevista(revista);
			
			primaryStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
