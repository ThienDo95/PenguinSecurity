

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * Room for the business for this project
 *
 */
public class Room implements Serializable {
	private ArrayList<Alarm> alarms;
	private String roomName;
	private int id;
	
	/**
	 * 
	 * Constructor for the Room 
	 */
	public Room(String name, int id) {
		this.id = id;
		alarms = new ArrayList<Alarm>();
		roomName = name;
		

	}

	/**
	 * 
	 * Second constructor that has an array of alarm and risk
	 */
	public Room(String name, ArrayList<Alarm> alarms) {
		this.roomName = name;
		this.alarms = alarms;

	}

	/**
	 * 
	 * @return the array of the alarm
	 */
	protected ArrayList<Alarm> getAlarms() {
		return alarms;
	}

	/**
	 * 
	 * @param change the array of the alarms
	 */
	protected void setAlarms(ArrayList<Alarm> alarms) {
		this.alarms = alarms;
	}

	/**
	 * 
	 * @return String of room's name
	 */
	protected String getRoomName() {
		return roomName;
	}

	/**
	 * 
	 * @param roomName change the room name
	 */
	protected void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	/**
	 * 
	 * @param riskLevel change the risk level of the room
	 */


	/**
	 * @return String contains room name and risk level
	 */
	public String toString() {
		return roomName;
	}

	/**
	 * 
	 * @return risk level of the room
	 */

	
	public int getId() {
		return id;
	}

}
