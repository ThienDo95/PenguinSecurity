/**
 * 
 * Flood Alarm  class
 * Subclass of Alarm class
 *
 */
 

public class FloodAlarm extends Alarm 
{

	/**
	 * Constructor that takes two parameters
	 *  
	 * @param name of the alarm
	 * @param priority is the number of the alarm's priority
	 */
	public FloodAlarm(String name, int priority, int id) 
	{
		super(name, priority, id);
	}
	
	/**
	 * @return the name of Flood Alarm class
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
		alert = "Flood Alarm has been triggered";
		return alert;
	}	
}
