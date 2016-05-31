package br.com.ceunsp.projeto1.visao;

import java.io.IOException;

import br.com.ceunsp.projeto1.controller.CadastroCaixaController;
import br.com.ceunsp.projeto1.modelo.Caixa;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CadastroCaixaMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("fxml/Cadastro_caixa.fxml"));
			
			Scene scene = new Scene(root, 420, 350);
			scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());

			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("imagens/caixavermelha.png")));//imagem

			primaryStage.setScene(scene);
			primaryStage.setTitle("Cadastro Caixa");
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

	public void abrirCadastroRevista(Caixa revista) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Cadastro_caixa.fxml"));
		
			Parent root = loader.load();

			Scene scene = new Scene(root, 420, 350);
			scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());
			Stage primaryStage = new Stage();
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("imagens/caixavermelha.png")));// imagem

			primaryStage.setScene(scene);
			primaryStage.setTitle("Cadastro de Caixas");
			primaryStage.setMaximized(false);
			primaryStage.setResizable(false);

			// Define a pessoa no controller.
			CadastroCaixaController controller = loader.getController();
			controller.setCaixa(revista);
			
			primaryStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}