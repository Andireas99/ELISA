package execute;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import gui.AlertController;

public class OpenWebsite {

	public static void open(String path) {
		Desktop dt = null;
		if (Desktop.isDesktopSupported()) {
			dt = Desktop.getDesktop();
		} else {
			AlertController.showErrorDialog("Systemfehler", "Diese Platform unterst�tzt das �ffnen von Webseiten durch Java nicht.");
			return;
		}

		try {
			dt.browse(new URI(path));
		} catch (URISyntaxException e1) {
			AlertController.showErrorDialog("URL Fehler", "Die URL \"" + path + "\" ist ung�ltig. Bitte �ndern Sie den Eintrag in den Einstellungen. ");
			e1.printStackTrace();
		} catch (IOException e1) {
			AlertController.showIOExceptionDialog("Lesen");
			e1.printStackTrace();
		}
	}
}
