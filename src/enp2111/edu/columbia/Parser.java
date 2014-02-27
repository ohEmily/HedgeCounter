package enp2111.edu.columbia;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Assumes that each new speaker is separated by an empty line.
 * Assumes that words (contiguous characters surrounded by whitespace)
 * cannot contain numerical digits (i.e. speakers would say "one" not "1").
 * 
 * Based on: http://www.javapractices.com/topic/TopicAction.do?Id=87
 */

public class Parser
{
	private final Path filePath;
	private final static Charset ENCODING = StandardCharsets.UTF_8;  
	public static final String NEW_SPEAKER_IDENTIFIER = "Subj_";
	
	private SpeakerList speakerList;
	
	/**
   	Constructor.
   	@param fileName full name of an existing, readable file.
	 */
	public Parser(String fileName) throws IOException
	{
		filePath = Paths.get(fileName);
		speakerList = new SpeakerList();
	}
  
	/** Creates a HashMap, which is added to the masterList of hashmaps and
	 * replaced by a new empty HashMap every time a new speaker starts speaking. */
	public final void parseByLine() throws IOException 
	{
		Speaker speaker;
		try (Scanner scanner =  new Scanner(filePath, ENCODING.name()))
		{
			while (scanner.hasNextLine()) // loop until EOF
			{
				String nextLine = scanner.nextLine();
				speaker = speakerList.getSpeaker(findSpeakerName(nextLine));
				if (nextLine.length() != 0)
				{
					speaker = findHedgesInLine(nextLine, speaker);
					speakerList = speakerList.add(speaker);
				}
			}
		}
	}
  
	/** 
   	 * Go through this line and return currentSpeaker with the number of 
   	 * hedges associated with currentSpeaker udated.
	 */
	public Speaker findHedgesInLine(String aLine, Speaker currentSpeaker)
	{
		//use a second Scanner to parse the content of each line 
		Scanner scanner = new Scanner(aLine);
		scanner.useDelimiter("\\s+");
		while (scanner.hasNext())
		{
			String thisWord = scanner.next();
			// skip any word containing a digit; assumed to be e.g. timestamp
			while (thisWord.matches(".*\\d.*"))
				thisWord = scanner.next();
			// fo
			currentSpeaker.incrementWords();
			if (isHedge(thisWord))
			{
				currentSpeaker.incrementHedges();
				currentSpeaker.addToHedgeList(thisWord);
			}
//			System.out.print(thisWord + " "); // Testing
		}
		scanner.close();
		return currentSpeaker;
	}
  
	/** 
	 * Returns true if the word is found in the hedge list, 
	 * false if it is not.
	 * @param aWord
	 * @return
	 */
	private boolean isHedge(String aWord)
	{
		for (int i = 0; i < Tester.POSSIBLE_HEDGE_LIST.length; i++)
		{
			if (aWord.contains(Tester.POSSIBLE_HEDGE_LIST[i]))
				return true;
		}
		return false;
	}
	
	/** 
   	 * Gets the name of the speaker identified in this line.
	 */
	private String findSpeakerName(String aLine)
	{
		//use a second Scanner to parse the content of each line 
		Scanner scanner = new Scanner(aLine);
		scanner.useDelimiter("\\s+");
		while (scanner.hasNext())
		{
			String nextWord = scanner.next();
			if (nextWord.contains(NEW_SPEAKER_IDENTIFIER)) // i.e. "Subj_"
			{
				scanner.close();
				nextWord = nextWord.substring(0, (nextWord.length() - 1));
				return nextWord;
			}
		}
		scanner.close();
		return "unassigned";
	}
	
	/**
	 * Returns each speaker's name and metrics.
	 */
	public String toString()
	{
		return speakerList.toString();
	}
} 