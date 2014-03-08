package enp2111.edu.columbia;

import java.io.File;
import java.io.IOException;

public class Tester
{	
	public static void main(String[] args) throws IOException
	{
		System.out.println("This program will run with the default demo text \n"
				+ "file if you didn't enter any arguments.\n\n");
		String filename = "";
		if (args.length == 0)
			filename = (new File("").getAbsolutePath()).concat("\\demo_data.txt");
		else
			filename = args[0];
		
		Parser parser = new Parser(filename);
		parser.parseByLine();
		System.out.println(parser.toString());
	}
	
	// TODO multiword hedges
	// TODO make hedge list editable from outside (i.e. file I/O)
	public static final String[] POTENTIAL_HEDGES = {
		"think", "thought", "believe", "consider", "assume", "find", "feel", 
		"felt", "appear", "suppose", "guess", "suggest", "presume" 
	};
	
	public static final String[] POTENTIAL_REL_HEGES = {
		"think", "thought", "believe", "consider", "assume", "understand", "find",
		"feel", "don't know", "appear", "seem", "suggest" 
	};
	
	public static final String[] POTENTIAL_PROP_HEDGES = {
		"generally", "often", "rarely", "sometimes", "frequently", "occasionally",
		"seldom", "usually", "most", "several", "approximately", "apparently", 
		"virtually", "basically", "roughly", "somewhat", "somehow", "partially"
	};
	
	public static final String[] HEDGE_LABELS = { "<\\hProp>", "<\\hRel>" };
}
