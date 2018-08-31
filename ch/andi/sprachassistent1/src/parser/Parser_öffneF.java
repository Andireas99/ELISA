package parser;

import bgFunc.MyParser;
import bgFunc.MyPaths;
import execute.OpenFile;
import feedback.AlertController;

public class Parser_�ffneF implements BaseParser{

	public void parse(String input, String tag) {
		System.out.println("(Parser_�ffneF.parse) input: " + input);
		String[] words = input.split(" ");

		String args = input.substring(words[0].length() + 1, input.length());
		String fileName = MyParser.getContainedFileName(args);

		String path = MyPaths.getPathOfKnownFile(fileName);

		if (path != null) {
			OpenFile.open(path);
		} else {
			AlertController.showErrorDialog("Datei Fehler", "Die Datei \""+fileName+"\" konnte nicht gefunden werden. Bitte �ndern Sie den Eintrag in den Einstellungen. ");
			//System.err.println("WARNING: Path of file " + fileName + " couldn't be found!");
		}
	}
}
