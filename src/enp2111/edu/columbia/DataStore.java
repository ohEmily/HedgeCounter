package enp2111.edu.columbia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;

public class DataStore 
{
	// TODO multiword hedges
	private static LinkedList<String> potential_hRels, potential_hProps, 
			potential_hRel_multiwords, potential_hProp_multiwords;
	private static final String hedgeDelimeter = "\\t";
	
	/** Constructor */
	public DataStore(String list_hRel_filepath, String list_hProp_filepath, 
			String list_hRel_multiword_filepath, 
			String list_hProp_multiword_filepath)
	{		
		potential_hRels = new LinkedList<String>();
		potential_hProps = new LinkedList<String>();
		potential_hRel_multiwords = new LinkedList<String>();
		potential_hProp_multiwords = new LinkedList<String>();
		
		setArray(list_hRel_filepath, potential_hRels);
		setArray(list_hProp_filepath, potential_hProps);
		setArray(list_hRel_multiword_filepath, potential_hRel_multiwords);
		setArray(list_hProp_multiword_filepath, potential_hProp_multiwords);
		
		// Testing if arrays fill properly
		System.out.println(toString());
	}
	
	/**
	 * Fill the array with the words or phrases in the file, separating each
	 * hedge by hedgeDelimeter.
	 * @param list_filepath the file which contains the list of hedges
	 * @param emptyArray the array to be filled
	 */
	private static void setArray(String list_filepath, LinkedList<String> emptyArray)
	{		
		Path filePath = Paths.get(new File("").getAbsolutePath().concat("\\" + list_filepath));
		System.out.println(filePath.toString());
		
		try
		{
			Scanner scanner = new Scanner(Paths.get(list_filepath), Tester.ENCODING.name());
			scanner.useDelimiter(hedgeDelimeter);

			while (scanner.hasNext())
				emptyArray.add(scanner.next());
			scanner.close();
			
		} 
		catch (IOException e)
		{
			System.out.println("File encoding error. Text files should be UTF-8.");
			e.printStackTrace();
		}	
	}
	
	/** for testing: print each array */
	public String toString()
	{
		String all = "";
		for (String each : potential_hRels)
			all += each;
		for (String each : potential_hProps)
			all += each;
		for (String each : potential_hRel_multiwords)
			all += each + "\n";
		for (String each : potential_hProp_multiwords)
			all += each  + "\n";
		return all;
	}
	
	public static LinkedList<String> getPotentialhRels()
	{
		return potential_hRels;
	}
	
	public static LinkedList<String> getPotentialhProps()
	{
		return potential_hProps;
	}
	
	public static LinkedList<String> getPotentialhRelMultiwords()
	{
		return potential_hRel_multiwords;
	}
	
	public static LinkedList<String> getPotentialhPropMultiwords()
	{
		return potential_hProp_multiwords;
	}
}
