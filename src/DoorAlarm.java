/**
 * 
 * Door Alarm  class
 * Subclass of Alarm class
 *
 */

public class DoorAlarm extends Alarm
{

	/**
	 * Constructor that takes two parameters
	 *  
	 * @param name of the alarm
	 * @param priority is the number of the alarm's priority
	 */
	public DoorAlarm(String name, int priority, int id) 
	{
		super(name, priority, id);
		
	}

   /**
	* @return the name of Door Alarm class
	*/
		 
	public String getName() 
	{
		return super.getName();
	}
	
	/**
	 * @return a string of message telling the name of which alarm is going off
	 */
	public String goesOff() 
	{
		String alert;
		alert = "Invasion Alarm has been triggered";

		return alert;
	}

}
