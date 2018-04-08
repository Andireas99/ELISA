package commands;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import main.Main;

public class CommandParser {

	public CommandParser() {
		
	}
	
	public void parse(String input) {
		System.out.println("(CommandParser.parse) input: "+input);
		
		if("<unk>".equals(input)) {
			//System.out.println("recognized unknown input");
			return;
		}
		if("stopp".equals(input)) {
			//TODO nachfragen
			System.out.println("recognized stopp");
			Main.quit = true;
			return;
		}
		
		input = removePoliteness(input);
		
		if(input.startsWith("k�nntest du") || input.startsWith("k�nnten sie")) {
			input = restructureInputAsCommand(input);
		}
		
		String firstWord = input.split(" ")[0];
		Class<?> cls;
		try {
			cls = Class.forName("commands.Parser_"+firstWord);
			Constructor<?> constr = cls.getConstructor();
			Object instance = constr.newInstance();
			cls.getMethod("parse", String.class).invoke(instance, input);
		} catch (ClassNotFoundException e) {
			System.err.println("ERROR: Command \""+firstWord+"\" couldn't be found!");
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}
	
	private String removePoliteness(String input) {
		input = input.replaceAll("entschuldigung", ""); 
		input = input.replaceAll("bitte", ""); 
		input = input.replaceAll("danke", ""); 
		input = input.replaceAll("vielen dank", ""); 
		
		input = removeMultipleSpaces(input);
		return input;
	}
	
	private String removeMultipleSpaces(String input) {
		input = input.trim().replaceAll(" +", " ");
		return input;
	}
	
	private String restructureInputAsCommand(String input) {
		String[] parts = input.split(" ");
		String lastWord = parts[parts.length-1];
		input = input.substring(0, input.length()-lastWord.length()-1);
		
		if(input.startsWith("k�nntest du"))
			input = input.replace("k�nntest du", lastWord.substring(0, lastWord.length()-1));
		else if(input.startsWith("k�nnten sie")) 
			input = input.replace("k�nnten sie", lastWord.substring(0, lastWord.length()-1));
		
		return input;
	}
	
	//debug
	public static void main(String[] args) {
		CommandParser cp = new CommandParser();
		String in = "�ffne ein neues Fenster mit firefox".toLowerCase();
		cp.parse(in);
		
	}

}
