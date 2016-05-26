package br.com.ceunsp.projeto1.visao;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ListagemEmprestimoMain extends Application{
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("fxml/Listagem_emprestimo.fxml"));
			
			Scene scene = new Scene(root, 650, 350);
			scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Listagem de revistas");
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
