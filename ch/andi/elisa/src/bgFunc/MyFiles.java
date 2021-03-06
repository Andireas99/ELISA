package bgFunc;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import feedback.AlertController;

public class MyFiles {

	public final static String PROGRAMS_PATH = System.getProperty("user.home") + "\\.ELISA\\data\\programsPath.txt";
	public final static String AUTO_PROGRAMS_PATH = System.getProperty("user.home") + "\\.ELISA\\data\\autoProgramsPath.txt";
	public final static String REMOVED_PROGRAMS_PATHS = System.getProperty("user.home")
			+ "\\.ELISA\\data\\removedProgramsPath.txt";
	public final static String FILES_PATH = System.getProperty("user.home") + "\\.ELISA\\data\\filesPath.txt";
	public final static String WEBSITES_PATH = System.getProperty("user.home") + "\\.ELISA\\data\\websitesPath.txt";
	public final static String GRAM_FILE = System.getProperty("user.home") + "\\.ELISA\\sphinx\\EntryNames.gram";
	public final static String DICT_FILE = System.getProperty("user.home") + "\\.ELISA\\sphinx\\voxforge_small.dic";
	public final static String SETTINGS_FILE = System.getProperty("user.home") + "\\.ELISA\\data\\settings.txt";

	public static String[] getFileContent(String path) {
		List<String> list = new ArrayList<>();

		try {
			list.addAll(Files.readAllLines(Paths.get(path), Charset.forName("ISO-8859-1")));
		} catch (IOException e) {
			AlertController.showIOExceptionDialog("Lesen");
			e.printStackTrace();
		}

		String[] out = (String[]) list.toArray(new String[list.size()]);
		return out;
	}

	public static void writeFile(List<String> lines, String path) {

		if (sameAsInFile(lines, path)) {
			return;
		}

		Path file = Paths.get(path);
		try {
			Files.write(file, lines, Charset.forName("ISO-8859-1"));
		} catch (IOException e) {
			AlertController.showIOExceptionDialog("Speichern");
			e.printStackTrace();
		}
	}

	private static boolean sameAsInFile(List<String> lines, String path) {
		List<String> fileLines = Arrays.asList(getFileContent(path));
		if (fileLines.size() == lines.size()) {
			for (int i = 0; i < lines.size(); i++) {
				if (!lines.get(i).equals(fileLines.get(i))) {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	public static void addNewLineToFile(String path, String line) {
		ArrayList<String> lines = new ArrayList<>();
		lines.addAll(Arrays.asList(getFileContent(path)));
		lines.add(line);
		writeFile(lines, path);
	}

	public static String[] getAllNames(String path) {
		String[] in = getFileContent(path);
		String[] out = new String[in.length];
		for (int i = 0; i < in.length; i++) {
			out[i] = in[i].split("\\|")[0];
		}
		return out;
	}

	public static String[] getAllPaths(String path) {
		String[] in = getFileContent(path);
		String[] out = new String[in.length];
		for (int i = 0; i < in.length; i++) {
			out[i] = in[i].split("\\|")[1];
		}
		return out;
	}

	public static boolean replaceOnceInFile(String filePath, String oldString, String newString) {

		String[] lines = getFileContent(filePath);

		int matchCount = 0;

		for (int i = 0; i < lines.length; i++) {
			if (lines[i].matches(".*" + oldString + ".*")) {
				lines[i] = lines[i].replaceFirst(oldString, newString);
				matchCount++;
			}
		}

		if (matchCount != 1) {
			return false;
		}

		writeFile(Arrays.asList(lines), filePath);
		return true;
	}

	public static boolean removeLineFromDict(String name) {

		ArrayList<String> lines = new ArrayList<>(Arrays.asList(getFileContent(MyFiles.DICT_FILE)));

		int matchCount = 0;

		for (int i = 0; i < lines.size(); i++) {
			if (lines.get(i).matches(name + " .*")) {
				lines.remove(i);
				matchCount++;
			}
		}

		if (matchCount != 1) {
			return false;
		}

		writeFile(lines, MyFiles.DICT_FILE);

		return true;
	}

	public static boolean replaceEntryInGram(String entryType, String oldName, String newName) {
		if (oldName == null) {
			addEntryToGram(entryType, new String[] { "_" + newName.replace(" ", "_") });
			return true;
		}
		if (removeEntryFromGram(entryType, oldName)) {
			addEntryToGram(entryType, new String[] { "_" + newName.replace(" ", "_") });
			return true;
		}
		return false;
	}

	public static boolean replaceEntryInDict(String oldName, String newName, String sprache) {
		String pronounciation = Words.getPhonemes(sprache, newName);
		if (oldName == null) {
			MyFiles.addEntriesToDict(new String[] { "_" + newName.replace(" ", "_") }, new String[] { pronounciation });
			return true;
		}
		if (MyFiles.replaceOnceInFile(MyFiles.DICT_FILE, "^_?" + oldName.replace(" ", "_") + " .*",
				"_" + newName.replace(" ", "_") + " " + pronounciation)) {
			return true;
		}
		AlertController.showErrorDialog("Fehler", "Der Eintrag " + oldName
				+ " konnte nicht genau einmal im W�rterbuch gefunden werden.\r\nBitte setzen Sie ELISA in den Einstellungen zur�ck.");
		return false;
	}

	public static void addEntryToGram(String entryType, String[] programNames) {
		String[] lines = MyFiles.getFileContent(GRAM_FILE);
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].startsWith("<" + entryType + ">")) {
				for (int j = 0; j < programNames.length; j++) {
					if (lines[i].matches("<" + entryType + ">\\s*=\\s*<VOID>\\s*;")) {
						lines[i] = lines[i].replace("<VOID>", programNames[j]);
					} else {
						lines[i] = lines[i].replace(";", " | " + programNames[j] + ";");
					}
				}
			}
		}

		MyFiles.writeFile(Arrays.asList(lines), GRAM_FILE);
	}

	public static void addEntriesToDict(String[] entryNames, String[] entryPronounciation) {
		List<String> dictLines = new ArrayList<>();
		dictLines.addAll(Arrays.asList(MyFiles.getFileContent(DICT_FILE)));

		for (int i = 0; i < entryNames.length; i++) {
			dictLines.add(entryNames[i] + " " + entryPronounciation[i]);
		}

		dictLines.sort(null);

		MyFiles.writeFile(dictLines, DICT_FILE);
	}

	public static boolean removeEntryFromGram(String entryType, String name) {
		String[] lines = MyFiles.getFileContent(GRAM_FILE);
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].startsWith("<" + entryType + ">")) {
				if (lines[i].matches(".*\\b_?" + name + "\\b.*")) {
					lines[i] = lines[i].replaceFirst("\\b_?" + name + "\\b", "");
					lines[i] = lines[i].replaceAll("=\\s*\\|", "=").replaceAll("\\|\\s*\\|", "|").replaceAll("\\|\\s*;", ";")
							.replaceAll("=\\s*;", "= <VOID>;").replaceAll(" +", " ");
				} else {
					AlertController.showErrorDialog("Fehler", "Der Eintrag " + name
							+ " konnte nicht im Grammatikmodell gefunden werden.\r\nBitte setzen Sie ELISA in den Einstellungen zur�ck.");
					return false;
				}
				break;
			}
		}

		MyFiles.writeFile(Arrays.asList(lines), GRAM_FILE);
		return true;
	}
}
