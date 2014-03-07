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
	private int numberOfPropHedges;
	private int numberOfRelHedges;
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
		numberOfPropHedges = 0;
		numberOfRelHedges = 0;
		numberOfHedges = 0;
	}
	
	/**
	 * Registers the hedge, if it is a hedge.
	 * @param possibleHedge
	 */
	public boolean considerHedge(String possibleHedge)
	{
		for (int i = 0; i < Tester.POTENTIAL_PROP_HEDGES.length; i++)
		{
			if (possibleHedge.contains(Tester.POTENTIAL_PROP_HEDGES[i]))
			{
				numberOfPropHedges++;
				numberOfHedges++;
				addToHedgeList(possibleHedge);
				return true;
			}
		}
		for (int i = 0; i < Tester.POTENTIAL_REL_HEGES.length; i++)
		{
			if (possibleHedge.contains(Tester.POTENTIAL_REL_HEGES[i]))
			{
				numberOfRelHedges++;
				numberOfHedges++;
				addToHedgeList(possibleHedge);
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
				"total number of hedges used: " + numberOfHedges + "\n\t\t" + 
				"number of relational hedges: " + numberOfRelHedges + "\n\t\t" +
				"number of propositional hedges: " + numberOfPropHedges + "\n"
				;
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