package enp2111.edu.columbia;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Tester
{	
	public final static Charset ENCODING = StandardCharsets.UTF_8;  
	
	public static void main(String[] args) throws IOException
	{	
		System.out.println("Arguments for this program must be absolute paths for "
				+ "filename, \nthe hProps, the hRels, multiword hProps, and multiword "
				+ "hRels files. \n");
		
		new DataStore(args[1], args[2], args[3], args[4]); // initialize data values
		
		Parser parser = new Parser(args[0]);
		parser.parseByLine();
		System.out.println(parser.toString());
	}
}
