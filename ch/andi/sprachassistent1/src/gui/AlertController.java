package gui;

import feedback.FeedbackController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertController {

	public static void showSprachErrorDialog(String sprache, String name) {
		
		new Thread(new FeedbackController(TrayIconController.ERROR_ICON, 10000)).start();
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText("Unbekannte Sprache");
		alert.setContentText("Die Sprache \"" + sprache + "\" des Programs \"" + name + "\" konnte nicht erkannt werden.");

		alert.showAndWait();
	}

	public static void showProgramPathErrorDialog(String name, String pfad) {
		
		new Thread(new FeedbackController(TrayIconController.ERROR_ICON, 10000)).start();
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText("Unbekannte Datei");
		alert.setContentText("Das Programm \"" + name + "\" konnte unter dem Pfad \"" + pfad + "\" nicht gefunden werden.");

		alert.showAndWait();
	}
	
	public static void showErrorDialog(String header, String content) {
		
		new Thread(new FeedbackController(TrayIconController.ERROR_ICON, 10000)).start();
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText(header);
		alert.setContentText(content);

		alert.showAndWait();
	}

}
