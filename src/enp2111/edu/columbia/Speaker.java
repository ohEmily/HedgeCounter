package enp2111.edu.columbia;

import java.util.ArrayList;
import java.util.LinkedList;


/**
 * Used to aggregate metrics for each speaker.
 */

public class Speaker 
{
	private String speakerName;
	private int numberOfWords;
	private int numberOfPropHedges;
	private int numberOfRelHedges;
	private LinkedList<String> hedgesUsed;
	
	private LinkedList<Boolean> sentences; // true if had hedge(s)
	private LinkedList<Boolean> turns; // true if had hedge(s)

	/** Default constructor is made private because a speaker should have
	 * a name. */
	@SuppressWarnings("unused")
	private Speaker(){}
	
	/**
	 * Creates a new speaker with the name passed.
	 * @param speakerName
	 */
	public Speaker(String speakerName)
	{
		this.speakerName = speakerName;
		hedgesUsed = new LinkedList<String>();
		numberOfWords = 0;
		numberOfPropHedges = 0;
		numberOfRelHedges = 0;
		sentences = new LinkedList<Boolean>();
		turns = new LinkedList<Boolean>();
	}
	
	/**
	 * Registers the hedge, if it is a hedge.
	 * @param possibleHedge
	 */
	public boolean isHedge(String possibleHedge)
	{
		if (isPropHedge(possibleHedge) || isRelHedge(possibleHedge))
			return true;
		return false;
	}

	public boolean isPropHedge(String possibleHedge)
	{
		LinkedList<String> potentialHProps = DataStore.getPotentialhProps();
		if (potentialHProps.contains(possibleHedge))
		{
			numberOfPropHedges++;
			addToHedgeList(possibleHedge);
			return true;
		}
		return false;
	}
	
	public boolean isRelHedge(String possibleHedge)
	{
		LinkedList<String> potentialHRels = DataStore.getPotentialhRels();
		if (potentialHRels.contains(possibleHedge))
		{
			numberOfRelHedges++;
			addToHedgeList(possibleHedge);
			return true;
		}

		return false;
	}

	
	public boolean containsMultiwordHedge(String possibleSentence)
	{
		if (hasMultiwordRelHedge(possibleSentence) || hasMultiwordPropHedge(possibleSentence))
			return true;
		return false;
	}
	
	public boolean hasMultiwordRelHedge(String possibleSentenceHedge)
	{
		for (String each : DataStore.getPotentialhRelMultiwords())
		{
			if (possibleSentenceHedge.contains(each))
			{
				numberOfRelHedges++;
				addToHedgeList(possibleSentenceHedge);
				return true;
			}
		}
		return false;
	}
	
	
	public boolean hasMultiwordPropHedge(String possibleSentenceHedge)
	{
		for (String each : DataStore.getPotentialhPropMultiwords())
		{
			if (possibleSentenceHedge.contains(each))
			{
				numberOfPropHedges++;
				addToHedgeList(possibleSentenceHedge);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Outputs the speaker name and all relevant metrics.
	 */
	public String toString()
	{
		return 
				speakerName + ":\n\t" + 
				"number of words spoken: "+ numberOfWords + "\n\t" +
				"total number of hedges used: " + (numberOfRelHedges + numberOfPropHedges) + "\n\t\t" + 
				"number of relational hedges: " + numberOfRelHedges + "\n\t\t" +
				"number of propositional hedges: " + numberOfPropHedges + "\n\t" +
				"number of sentences " + sentences.size() + "\n\t" +
				"number of turns: " + turns.size() + "\n"
				;
	}
	
	public void incrementHedgedTurn()
	{
		turns.add(true);
	}
	
	public void incrementUnhedgedTurn()
	{
		turns.add(false);
	}
	
	public void incrementHedgedSentence()
	{
		sentences.add(true);
	}
	
	public void incrementUnhedgedSentence()
	{
		sentences.add(false);
	}
	
	public int getNumberOfSentences()
	{
		return sentences.size();
	}
	
	public int getNumberOfTurns()
	{
		return turns.size();
	}
	
	public int getNumberOfPropHedges()
	{
		return numberOfPropHedges;
	}
	
	public int getNumberOfRelHedges()
	{
		return numberOfRelHedges;
	}
	
	/**
	 * @return the number of turns that have hedges in them
	 */
	public int getNumberOfHedgedSentences()
	{
		int numberOfHedgedSentences = 0;
		for (boolean each : sentences)
			if (each)
				numberOfHedgedSentences++;
		return numberOfHedgedSentences;
	}

	/**
	 * @return the number of turns that have hedges in them
	 */
	public int getNumberOfHedgedTurns()
	{
		int numberOfHedgedTurns = 0;
		for (boolean each : turns)
			if (each)
				numberOfHedgedTurns++;
		return numberOfHedgedTurns;
	}
	
	public void incrementWords()
	{
		numberOfWords++;
	}
	
	public void addToHedgeList(String hedge)
	{
		hedgesUsed.add(hedge);
	}
	
	public String getSpeakerName()
	{
		return speakerName;
	}
}