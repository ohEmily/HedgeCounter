package enp2111.edu.columbia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Assumes that each new speaker is separated by an empty line.
 * Assumes that words (contiguous characters surrounded by whitespace)
 * 
 * Based on: http://www.javapractices.com/topic/TopicAction.do?Id=87
 */

public class Parser
{
	public static final String NEW_SPEAKER_IDENTIFIER = "Subj_";
	
	private SpeakerList speakerList;
	
	private static final int TIMESTAMP_LENGTH = 11; // e.g. 0.150	10.450
	
	private final Path filePath;
	
	private BufferedWriter writer;
	
	/**
   	Constructor.
   	@param fileName full name of an existing, readable file.
	 */
	public Parser(String fileName) throws IOException
	{
		filePath = Paths.get(fileName);
		speakerList = new SpeakerList();
		
		setupOutputWriter();
	}
	
	////////////* METHODS CONCERNING WRITING TO OUTPUT FILE *////////////////
	/** 
	 * Creates a BufferedWriter with a file named after today's date to which
	 * the output of this program will write.
	 * Source: http://stackoverflow.com/questions/15754523/ */
	public void setupOutputWriter()
	{
	        try
	        {
	            //create a temporary file
	            String timeLog = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
	            File logFile = new File(timeLog);

	            // This will output the full path where the file will be written to...
	            System.out.println("Output file name: " + logFile.getCanonicalPath());

	            writer = new BufferedWriter(new FileWriter(logFile));
	        } 
	        catch (Exception e)
	        {
	            System.out.println("Error: failed to open output writer.\n");
	        	e.printStackTrace();
	        } 
	}
	
	public void closeOutputWriter()
	{
		try 
		{
            // Close the writer regardless of what happens...
            writer.close();
        } 
		catch (Exception e) 
		{
			System.out.println("Error: failed to close output writer.\n");
        }
	}
	
	public void writeToOutputFile(String output)
	{
		 try
		 {
			writer.write(output);
		 } 
		 catch (IOException e)
		 {
			System.out.println("Error: failed to write to file.\n");
			e.printStackTrace();
		}
	}
  
	//////////////* METHODS CONCERNING ACTUALLY PARSING THE FILE *//////////////////
	
	/** Creates a HashMap, which is added to the masterList of hashmaps and
	 * replaced by a new empty HashMap every time a new speaker starts speaking. */
	public final void parseByLine() throws IOException 
	{
		try (Scanner scanner = new Scanner(filePath, Tester.ENCODING.name()))
		{
			while (scanner.hasNextLine()) // loop until EOF
			{
				String currentLine;
				if ((currentLine = scanner.nextLine()).length() != 0)
				{
					findMultiwordHedgesInLine(currentLine);
					// add two new lines between each speaker in output file
					writeToOutputFile("\n\n"); 
				}
			}
		}
	}
  
	public void findMultiwordHedgesInLine(String currentLine)
	{
		Speaker currentSpeaker = speakerList.getSpeaker(findSpeakerName(currentLine));
		currentLine = currentLine.substring(TIMESTAMP_LENGTH, currentLine.length());
		currentSpeaker.containsMultiwordHedge(currentLine);
		currentSpeaker = parseByWord(currentLine, currentSpeaker);
		speakerList = speakerList.add(currentSpeaker);
	}
	
	/** 
   	 * Go through this line and return currentSpeaker with the number of 
   	 * hedges associated with currentSpeaker updated.
	 */
	public Speaker parseByWord(String aLine, Speaker currentSpeaker)
	{
		boolean hedgePresent = false;
		//use a second Scanner to parse the content of each line 
		Scanner scanner = new Scanner(aLine);
		scanner.useDelimiter("\\s+");
		while (scanner.hasNext())
		{
			String thisWord = scanner.next();
			currentSpeaker.incrementWords();
			if(currentSpeaker.isPropHedge(thisWord))
			{
				writeToOutputFile("<hProp>" + thisWord + "</hProp> ");
				hedgePresent = true;
			}
			else if(currentSpeaker.isRelHedge(thisWord))
			{
				writeToOutputFile("<hRel>" + thisWord + "</hRel> ");
				hedgePresent = true;
			}
			else
				writeToOutputFile(thisWord + " ");
			
			// check whether this word marks the end of a sentence/file
			if (thisWord.contains(".") || thisWord.contains("!") || thisWord.contains("?") || (!scanner.hasNext())) // if a period or end of paragraph
			{
				if (hedgePresent)
				{
					currentSpeaker.incrementHedgedSentence();
				}
				else
				{
					currentSpeaker.incrementUnhedgedSentence();
				}
			}
		}
		
		// after reached the end of the line, take note of whether this turn 
		// had a hedge or not
		if (hedgePresent)
		{
			currentSpeaker.incrementHedgedTurn();
		}
		else
		{
			currentSpeaker.incrementUnhedgedTurn();
		}
		
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