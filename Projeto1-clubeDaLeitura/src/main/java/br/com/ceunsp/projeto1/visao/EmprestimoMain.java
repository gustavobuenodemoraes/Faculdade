package br.com.ceunsp.projeto1.visao;

import java.io.IOException;

import br.com.ceunsp.projeto1.controller.EmprestimoController;
import br.com.ceunsp.projeto1.modelo.Emprestimo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class EmprestimoMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("fxml/Emprestimo.fxml"));

			Scene scene = new Scene(root, 420, 350);
			scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());

			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("imagens/emprestimosred.png")));// imagem
			primaryStage.setScene(scene);
			primaryStage.setTitle("Emprestimo de revistas");
			primaryStage.setMaximized(false);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void abrirEmprestimo(Emprestimo emprestimo) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/Emprestimo.fxml"));

			Parent root = loader.load();

			Scene scene = new Scene(root, 420, 350);
			scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());
			Stage primaryStage = new Stage();
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("imagens/emprestimosred.png")));// imagem

			primaryStage.setScene(scene);
			primaryStage.setTitle("Emprestimo");
			primaryStage.setMaximized(false);
			primaryStage.setResizable(false);

			// Define a pessoa no controller.
			EmprestimoController controller = loader.getController();
			controller.setEmprestimo(emprestimo);

			primaryStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
