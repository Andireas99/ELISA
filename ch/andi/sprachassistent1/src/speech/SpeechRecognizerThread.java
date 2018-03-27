package speech;

import java.io.IOException;

import commands.CommandParser;
import edu.cmu.sphinx.api.SpeechResult;
import main.Main;

public class SpeechRecognizerThread implements Runnable {

	@Override
	public void run() {

		MyLiveRecognizer recognizer;
		CommandParser parser = new CommandParser();

		try {
			recognizer = new MyLiveRecognizer();
			recognizer.startRecognition(true);
			System.out.println("Recognizer is ready");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		while (!Main.quit) {
			SpeechResult result = recognizer.getResult();
			parser.parse(decode(result.getHypothesis()).toLowerCase());
		}
		recognizer.stopRecognition();
	}

	static String decode(String input) {
		input = input.replace("%ue%", "�");
		input = input.replace("%oe%", "�");
		input = input.replace("%ae%", "�");
		return input;
	}

}
