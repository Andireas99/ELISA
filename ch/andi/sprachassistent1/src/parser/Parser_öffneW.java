package parser;

import bgFunc.MyParser;
import bgFunc.MyPaths;
import execute.OpenWebsite;
import gui.AlertController;

public class Parser_�ffneW implements BaseParser{

	public void parse(String input, String tag) {
		System.out.println("(Parser_�ffneW.parse) input: " + input);
		String[] words = input.split(" ");

		String args = input.substring(words[0].length() + 1, input.length());
		String websiteName = MyParser.getContainedWebsiteName(args);

		String url = MyPaths.getURLOfKnownWebsite(websiteName);

		if (url != null) {
			OpenWebsite.open(url);
		} else {
			AlertController.showErrorDialog("Datei Fehler", "Die URL der Webseite \""+websiteName+"\" konnte nicht gefunden werden. Bitte �ndern Sie den Eintrag in den Einstellungen. ");
			//System.err.println("DEBUG: URL of website " + websiteName + " couldn't be found!");
		}
	}
}
