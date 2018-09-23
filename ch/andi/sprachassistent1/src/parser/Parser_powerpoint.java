package parser;

import bgFunc.MyParser;
import bgFunc.Processes;
import execute.OpenProgram;
import feedback.AlertController;
import jna.office.PowerpointControl;

public class Parser_powerpoint implements BaseParser {

	public void parse(String input, String tag) {

		PowerpointControl powerpointControl = null;

		try {
			powerpointControl = new PowerpointControl();
			if (tag.equals("fontSize")) {
				powerpointControl.setTextSize(MyParser.extractIntFromText(input));
			} else if (tag.equals("fontSize2")) {
				if (input.contains("vergr�sser")) {
					powerpointControl.increaseTextSize();
				} else if (input.contains("verkleiner")) {
					powerpointControl.decreaseTextSize();
				}
			} else if (tag.equals("textProperties")) {
				if (input.contains("fett")) {
					powerpointControl.setTextBoldState(!input.contains("nicht"));
				} else if (input.contains("kursiv")) {
					powerpointControl.setTextItalicState(!input.contains("nicht"));
				} else if (input.contains("unterstrichen") || input.contains("unterstreiche")) {
					powerpointControl.setTextUnderlineState(!input.contains("nicht"));
				}
			} else if (tag.equals("color")) {
				String[][] colors = { { "automatisch", "0" }, { "schwarz", "0" }, { "blau", "16711425" }, { "hellgr�n", "65025" },
						{ "dunkelblau", "8388480" }, { "dunkelrot", "128" }, { "dunkelgelb", "32768" }, { "grau", "8421248" }, { "gr�n", "32640" },
						{ "pink", "16711680" }, { "rot", "255" }, { "t�rkis", "16776450" }, { "violett", "8388608" }, { "weiss", "16776705" },
						{ "gelb", "65280" } };
				if ((input.contains("f�rbe") || input.contains("mache")) && input.contains("text")) {
					for (int i = 0; i < colors.length; i++) {
						if (input.matches(".*\\b" + colors[i][0] + "\\b.*")) {
							powerpointControl.setTextColor(Integer.parseInt(colors[i][1]));
							break;
						}
					}
				} else if ((input.contains("f�rbe") || input.contains("mache")) && input.contains("hintergrund")
						|| (input.contains("markiere") && input.contains("text"))) {
					AlertController.showErrorDialog("Funktion nicht verf�gbar", "Es ist leider nicht m�glich, in PowerPoint automatisch Text zu markieren. Bitte f�hren Sie die Funktion manuell aus.");
				}
			} else if (tag.equals("folie")) {
				if (input.contains("neue")) {
					powerpointControl.newSlide();
				} else if (input.contains("n�chste")) {
					powerpointControl.nextSlide();
				} else if (input.contains("vorherige")) {
					powerpointControl.previousSlide();
				} else if (input.contains("ersten")) {
					powerpointControl.firstSlide();
				} else if (input.contains("letzten")) {
					powerpointControl.lastSlide();
				}
			} else if (tag.equals("folieZahl")) {
				powerpointControl.goToSlide(MyParser.extractIntFromText(input));
			} else if (tag.equals("pr�sentation")) {
				if (input.startsWith("�ffne") || input.startsWith("beginne")) {
					powerpointControl.startSlideShow();
				} else if (input.startsWith("schliesse")) {
					powerpointControl.stopSlideShow();
				}
			} else if (input.startsWith("erstelle")) {
				if (input.contains("dokument") || input.contains("datei")) {
					powerpointControl.newDocument();
				}
			} else if (input.startsWith("�ffne")) {
				if (input.contains("dokument") || input.contains("datei")) {
					powerpointControl.openDocument();
				} else if (input.contains("neues fenster")) {
					OpenProgram.open(Processes.getPowerPointPath());
				}
			} else if (input.startsWith("speicher")) {
				if (input.contains("unter") || input.contains("als")) {
					powerpointControl.saveAs();
				} else {
					powerpointControl.saveDocument();
				}
			}
		} finally {
			powerpointControl.disposeFactory();
		}
	}
}
