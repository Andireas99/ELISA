package speech;

import java.io.IOException;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.Microphone;

public class MyLiveRecognizer extends LiveSpeechRecognizer {

	public MyLiveRecognizer() throws IOException {
		super(myConfig());

	}

	public MyLiveRecognizer(Microphone mic) throws IOException {
		super(myConfig(), mic);

	}

	static Configuration myConfig() {
		Configuration config = new Configuration();
		config.setAcousticModelPath("file:sphinx_data_small\\model_parameters");
		config.setDictionaryPath("file:sphinx_data_small\\etc\\voxforge_small.dic");
		config.setGrammarPath("sphinx_data_small\\etc");
		config.setGrammarName("my_model");
		config.setUseGrammar(true);
		return config;
	}
}
