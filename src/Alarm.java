

import java.io.Serializable;

public abstract class Alarm implements Serializable {
	
	// Name of the alarm
	private String name;
	// The level of priority of the alarm
	private int priority;
	private int id;
	
	/**
	 * Alarm constructor
	 * 
	 * @param name of the alarm
	 * @param priority level of the alarm
	 */
	public Alarm(String name, int priority, int id) {
		this.name = name;
		this.priority = priority;
		this.id = id;
	}

	
	/**
	 * 
	 * @return name of the alarm
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * 
	 * @param name set new name for the alarm
	 * 
	 */
	public void setName(String name) 
	{
		this.name = name;
	}

	/**
	 * 
	 * @return level of priority 
	 */
	public int getPriority() 
	{
		return priority;
	}

	/**
	 * 
	 * @param priority set new priority level
	 */
	public void setPriority(int priority) 
	{
		this.priority = priority;
	}
	
	public int getId() {
		return id;
	}
	
	/**
	 * An abstract method need to be used by the sub-class of Alarm
	 * @return a String contains the name of the alarm
	 * and the level of priority
	 */
	public abstract String goesOff();

	public String toString() {
		return name;
	}
}


