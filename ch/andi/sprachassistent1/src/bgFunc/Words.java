package bgFunc;

public class Words {

	public Words() {
		// TODO Auto-generated constructor stub
	}
	
	public static String decode(String input) {
		input = input.replace("%ue%", "�");
		input = input.replace("%oe%", "�");
		input = input.replace("%ae%", "�");
		return input;
	}
	
	public static String encode(String input) {
		input = input.replace("�", "%ue%");
		input = input.replace("�", "%oe%");
		input = input.replace("�", "%ae%");
		return input;
	}

}
