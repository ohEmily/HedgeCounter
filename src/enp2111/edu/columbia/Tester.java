package enp2111.edu.columbia;

import java.io.IOException;

public class Tester
{	
	// TODO multiword hedges
	// TODO make hedge list editable from outside (i.e. file I/O)
	public static final String[] POSSIBLE_HEDGE_LIST = {
		"think", "thought", "believe", "consider", "assume", "find", "feel", 
		"felt", "appear", "suppose", "guess", "suggest", "presume" 
	};
	
	public static final String FILENAME = "C:\\Users\\Emily Pakulski\\Google "
			+ "Drive\\Work\\Speech Lab\\NIST_20011115-1050_NONE_NONE.txt";
	
	public static void main(String[] args) throws IOException
	{
		Parser parser = new 
				Parser(FILENAME);
		parser.parseByLine();
		System.out.println(parser.toString());
	}
}
