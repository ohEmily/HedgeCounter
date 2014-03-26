package enp2111.edu.columbia;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Tester
{	
	private static String filename, potential_hRels, potential_hProps, potential_multiwords;
	
	public final static Charset ENCODING = StandardCharsets.UTF_8;  
	
	public static void main(String[] args) throws IOException
	{	
		System.out.println("Arguments for this program must be absolute paths for "
				+ "filename, \nthe hProps, the hRels, and the multiword text "
				+ "files. \n");
		filename = args[0];
		new DataStore(args[1], args[2], args[3]); // initialize data values
		
		Parser parser = new Parser(filename);
		parser.parseByLine();
		System.out.println(parser.toString());
	}
}
