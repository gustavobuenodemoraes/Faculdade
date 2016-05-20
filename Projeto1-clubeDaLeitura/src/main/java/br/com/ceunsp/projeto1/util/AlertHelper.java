package br.com.ceunsp.projeto1.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertHelper {
	public static void ErrorAlert(String title, String erroHeaderText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(erroHeaderText);
		alert.show();
	}
	public static void InfoAlert(String title, String erroHeaderText, String erroContentText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(erroHeaderText);
		alert.setContentText(erroContentText);
		alert.show();
	}
	public static void ErrorAlert(String title, String erroHeaderText, String erroContentText) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(erroHeaderText);
		alert.setContentText(erroContentText);
		alert.show();
	}
	public static void InfoAlert(String title, String erroHeaderText) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(erroHeaderText);
		alert.show();
	}
}
