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
 * 
 * Based on: http://www.javapractices.com/topic/TopicAction.do?Id=87
 */

public class Parser
{
	private final Path filePath;
	public static final String NEW_SPEAKER_IDENTIFIER = "Subj_";
	
	private SpeakerList speakerList;
	
	private static final int TIMESTAMP_LENGTH = 11; // e.g. 0.150	10.450
	
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
//		Speaker speaker;
		try (Scanner scanner = new Scanner(filePath, Tester.ENCODING.name()))
		{
			while (scanner.hasNextLine()) // loop until EOF
			{
				String currentLine;
				if ((currentLine = scanner.nextLine()).length() != 0)
				{
					findMultiwordHedgesInLine(currentLine);
					
				}
			}
		}
	}
  
	public void findMultiwordHedgesInLine(String currentLine)
	{
		Speaker currentSpeaker = speakerList.getSpeaker(findSpeakerName(currentLine));
		currentLine = currentLine.substring(TIMESTAMP_LENGTH, currentLine.length());
		currentSpeaker.containsMultiwordHedge(currentLine);
		currentSpeaker = findHedgesInLine(currentLine, currentSpeaker);
		speakerList = speakerList.add(currentSpeaker);
	}
	
	public final void parseByWord(String line)
	{
		
	}
	
	/** 
   	 * Go through this line and return currentSpeaker with the number of 
   	 * hedges associated with currentSpeaker udated.
	 */
	public Speaker findHedgesInLine(String aLine, Speaker currentSpeaker)
	{
		boolean hedgePresent = false;
		//use a second Scanner to parse the content of each line 
		Scanner scanner = new Scanner(aLine);
		scanner.useDelimiter("\\s+");
		while (scanner.hasNext())
		{
			String thisWord = scanner.next();
			currentSpeaker.incrementWords();
			if(currentSpeaker.isHedge(thisWord))
				hedgePresent = true;
			
			if (thisWord.contains(".") || (!scanner.hasNext())) // if a period or end of paragraph
			{
				if (hedgePresent)
					currentSpeaker.incrementHedgedSentence();
				else
					currentSpeaker.incrementUnhedgedSentence();
			}
		}
		// after reached the end of the line
		if (hedgePresent)
			currentSpeaker.incrementHedgedTurn();
		else
			currentSpeaker.incrementUnhedgedTurn();

		scanner.close();
		return currentSpeaker;
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