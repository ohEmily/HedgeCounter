package enp2111.edu.columbia;

import java.util.LinkedList;


/**
 * Used to aggregate metrics for each speaker.
 */

public class Speaker 
{
	private String speakerName;
	private int numberOfWords;
	private int numberOfHedges;
	private LinkedList<String> hedgesUsed;

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
		numberOfHedges = 0;
	}
	
	/**
	 * Outputs the speaker name and all relevant metrics.
	 */
	public String toString()
	{
		return speakerName + ":\n" + "number of words spoken: "
				+ numberOfWords + " number of hedges used: " + 
				numberOfHedges + "\n";
	}
	
	public void incrementWords()
	{
		numberOfWords++;
	}
	
	public void incrementHedges()
	{
		numberOfHedges++;
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