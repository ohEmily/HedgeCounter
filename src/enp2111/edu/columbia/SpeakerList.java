package enp2111.edu.columbia;

/**
 * Aggregation class for Speaker objects. Important for getting a specific
 * speaker from the list of speakers.
 */

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class SpeakerList
{
	private HashMap<String, Speaker> speakerList;
	
	private int numberOfSpeakers;
	private int numberOfHedgedTurns;
	private int numberOfTurns;
	private int numberOfSentences;
	private int numberOfHedgedSentences;
	private int numberOfPropHedges;
	private int numberOfRelHedges;
	
	/**
	 * Default constructor. Initialize empty speakerList.
	 */
	public SpeakerList()
	{
		speakerList = new HashMap<String, Speaker>();
	
		numberOfSpeakers = -1; // start at -1 to make up for "unassigned" values
		numberOfHedgedTurns = 0;
		numberOfTurns = 0;
		numberOfSentences = 0;
		numberOfHedgedSentences = 0;
		numberOfPropHedges = 0;
		numberOfRelHedges = 0;
	}
	
	/**
	 * Allows creating a new speaker list with the names of the speakers
	 * already in the system, if desired.
	 * @param speakerNames
	 */
	public SpeakerList(String ... speakerNames)
	{
		speakerList = new HashMap<String, Speaker>();
		for (int i = 0; i < speakerNames.length; i++)
		{
			speakerList.put(speakerNames[i], (new Speaker(speakerNames[i])));
		}
	}
	
	/**
	 * Adds the speaker to the list if (s)he is not in the list; if (s)he is
	 * already in it, removes the previous copy and adds the new one.
	 * @param newSpeaker
	 */
	public SpeakerList add(Speaker newSpeaker)
	{
		if (speakerList.get(newSpeaker.getSpeakerName()) != null)
			
		speakerList.put(newSpeaker.getSpeakerName(), newSpeaker);
		return this;
	}
	
	/** Returns the speaker if a speaker with this name already exists, and
	 * returns a new Speaker object with the name given if the speaker doesn't
	 * yet exist.
	 * */
	public Speaker getSpeaker(String speakerName)
	{
		Speaker speaker;
		if ((speaker = speakerList.get(speakerName)) != null)
		{
			return speaker;
		}
		speakerList.put(speakerName, new Speaker(speakerName));
		return speakerList.get(speakerName);
	}
	
	/**
	 * Outputs description of each speaker in String form in the order in
	 * which the speakers were added to the SpeakerList. Then outputs the metrics
	 * Anna asked for.
	 */
	public String toString()
	{
		collectMetrics();
		
		// get all speaker stats
		String returnVal = "Speaker List:\n";
		Iterator<Entry<String, Speaker>> it = speakerList.entrySet().iterator();
	    while (it.hasNext())
	    {
	    	Map.Entry<String, Speaker> speaker = (Map.Entry<String, Speaker>)it.next();
	        returnVal += speaker.toString();
	        numberOfSpeakers++;
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		returnVal += "\nNumber of speakers: " + numberOfSpeakers + "\n" +
				"Number of turns: " + numberOfTurns + "\n" +
				"Number of turns containing hedges: " + numberOfHedgedTurns + "\n" +
				"Percentage of turns containing hedges:" + (100 * numberOfHedgedTurns / numberOfTurns) + "%\n" +
				"Number of sentences containing hedges: " + numberOfHedgedSentences + "\n" +
				"Number of sentences: " + numberOfSentences + "\n" +
				"Percentage of sentences containing hedges: " + (100 * numberOfHedgedSentences / numberOfTurns) + "%\n" +
				"Number of relational hedges: " + numberOfRelHedges + "\n" +
				"Number of propositional hedges: " + numberOfPropHedges + "\n" +
				"Number of hedges: " + (numberOfPropHedges + numberOfRelHedges) + "\n"
				;
		
	    return returnVal;
	}
	
	/**
	 * Collects all the key metrics from the speaker list.
	 */
	private void collectMetrics()
	{
		Iterator<Entry<String, Speaker>> it = speakerList.entrySet().iterator();
		while (it.hasNext())
	    {
	        Map.Entry<String, Speaker> pairs = (Map.Entry<String, Speaker>)it.next();
	        numberOfSpeakers++;
	        numberOfSentences += pairs.getValue().getNumberOfSentences();
	        numberOfHedgedSentences += pairs.getValue().getNumberOfHedgedSentences();
	        numberOfTurns += pairs.getValue().getNumberOfTurns();
	        numberOfHedgedTurns += pairs.getValue().getNumberOfHedgedTurns();
	        numberOfPropHedges += pairs.getValue().getNumberOfPropHedges();
	        numberOfRelHedges += pairs.getValue().getNumberOfRelHedges();
	    }
	}
}