package commands;

public class CommandParser {

	public CommandParser() {
		
	}
	
	public void parse(String input) {
		System.out.println("parse: "+input);
		input = removePoliteness(input);
		
		if(input.startsWith("k�nntest du") || input.startsWith("k�nnten sie")) {
			input = restructureInputAsCommand(input);
		}
		
		switch(input.substring(0, input.indexOf(" "))) {
		case "�ffne":

			break;
		case "schliesse":
			break;
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
		String in = "K�nntest du ein Foto von diesem Bildschirm machen".toLowerCase();
		in = cp.removePoliteness(in);
		System.out.println(in);
		in = cp.restructureInputAsCommand(in);
		System.out.println(in);
		
	}

}
