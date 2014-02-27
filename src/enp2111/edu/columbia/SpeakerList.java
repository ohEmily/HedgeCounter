package enp2111.edu.columbia;

/**
 * Aggregation class for Speaker objects. Important for getting a specific
 * speaker from the list of speakers.
 */

import java.util.ArrayList;

public class SpeakerList {
	private ArrayList<Speaker> speakerList;
	
	/**
	 * Default constructor. Initialize empty speakerList.
	 */
	public SpeakerList()
	{
		speakerList = new ArrayList<Speaker>();
	}
	
	/**
	 * Allows creating a new speaker list with the names of the speakers
	 * already in the system, if desired.
	 * @param speakerNames
	 */
	public SpeakerList(String ... speakerNames)
	{
		speakerList = new ArrayList<Speaker>();
		for (int i = 0; i < speakerNames.length; i++)
		{
			speakerList.add(new Speaker(speakerNames[i]));
		}
	}
	
	/**
	 * Adds the speaker to the list if (s)he is not in the list; if (s)he is
	 * already in it, removes the previous copy and adds the new one.
	 * @param newSpeaker
	 */
	public SpeakerList add(Speaker newSpeaker)
	{
		for (int i = 0; i < speakerList.size(); i++)
		{
			if (speakerList.get(i).getSpeakerName().equals(newSpeaker.getSpeakerName()))
			{
				System.out.println("Speaker already exists");
				speakerList.set(i, newSpeaker);
			}
		}
		speakerList.add(newSpeaker);
		return this;
	}
	
	/** Returns the speaker if a speaker with this name already exists, and
	 * returns a new Speaker object with the name given if the speaker doesn't
	 * yet exist.
	 * */
	public Speaker getSpeaker(String speakerName)
	{
		for (Speaker each : speakerList)
		{
			if (each.getSpeakerName().equals(speakerName))
				return each;
		}
		return new Speaker(speakerName);
	}
	
	/**
	 * Outputs description of each speaker in String form in the order in
	 * which the speakers were added to the SpeakerList.
	 */
	public String toString()
	{
		String returnVal = "Speaker List:\n";
		for (Speaker each : speakerList)
			returnVal += each.toString();
		returnVal += "\nNumber of speakers: " + speakerList.size() + "\n";
		return returnVal;
	}
}
