

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * Putting every panel in the Constructor GUI together
 *
 */
public class SystemConstructionPanelJList extends JPanel {
	private Border blackline;
	private JFrame topFrame;
	private JPanel alarmsPanel;
	private Business currentBusiness;
	private JButton createRoomButton;
	private JButton deleteRoomButton;
	private JButton editRoomButton;
	private JButton triggerButton;
	private JButton backButton;
	private JScrollPane listPane;
	private DefaultListModel<Room> listModel;
	private JList<Room> list;
	//private JTextField addRoomTextInput;
	private JTextPane consoleTextArea;
	private File currentBusinessLogFile;
	private String currentlySelectedRoomText;
	private String businessNeading;
	private HomeWindowMainPanel hw;
	private SQLAccess sql;
	private SystemConstructionPanelInput inPanel;

	@SuppressWarnings("unchecked")
	/**
	 * The constructor of this class
	 */
	public SystemConstructionPanelJList(JPanel alarms, Business bus, SystemConstructionPanelInput inPanel,
			SystemConstructionConsole conPanel, JFrame fra, SQLAccess sql) throws IOException {
		this.sql = sql;
		this.inPanel = inPanel;
		this.setLayout(new FlowLayout());
		topFrame = fra; 
		alarmsPanel = alarms; 
		triggerButton = new JButton ("Trigger Alarm");
		alarms.add(triggerButton);		
		currentBusiness = bus; 
		blackline = BorderFactory.createLineBorder(Color.decode("#00BFFF")); 
		this.setBorder(BorderFactory.createTitledBorder(blackline, "Building List"));//
		makeJList(); 
		JViewport consolePane = (JViewport) ((JScrollPane) conPanel.getPanel().getComponent(0)).getViewport(); 
		consoleTextArea = (JTextPane) consolePane.getView();						
		//addRoomTextInput = (JTextField) inPanel.getComponent(0); 
		listPane = (JScrollPane) this.getComponent(0); 
		list = (JList<Room>) listPane.getViewport().getComponent(0); 	
		listModel = (DefaultListModel<Room>) list.getModel(); 	
		JPanel buttonPanel = (JPanel) this.getComponent(1); 
		//createRoomButton = (JButton) buttonPanel.getComponent(0); 		
		//deleteRoomButton = (JButton) buttonPanel.getComponent(1); 
		backButton = (JButton) buttonPanel.getComponent(0); 														
		//editRoomButton = (JButton) buttonPanel.getComponent(2); 
		currentBusinessLogFile = currentBusiness.getFile();	
		currentlySelectedRoomText = " ";
		businessNeading = "<b style=\"font:20;\">SECURITY LOG</b>" + " for "
				+ "<span style=\"font:16;color:	rgb(255,165,0);text-decoration:underline;\">"
				+ currentBusiness.getName() + "</span>";
		addListeners(); 
		loadRoomList();
		
	}

	/**
	 * Creating the buttons for add room -- delete room etc
	 * 
	 */
	private void makeJList() {
		DefaultListModel<Room> model = new DefaultListModel<Room>();
		JList<Room> list = new JList<Room>(model);
		JScrollPane listScroll = new JScrollPane(list);
		listScroll.setPreferredSize(new Dimension(290, 160));

		listScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.add(listScroll);
		//JButton create = new JButton("Add Room");
		//JButton delete = new JButton("Delete Room");
		JButton back = new JButton("Back");
		//JButton edit = new JButton("Edit Room");
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		//buttonPanel.add(create);
		//buttonPanel.add(delete);
		//buttonPanel.add(edit);
		buttonPanel.add(back);
		this.add(buttonPanel);
	}
	
	/**
	 *  A method to write and save the log of the trigger button
	 */
	public String wordSearch(String s, PrintWriter bw) throws IOException {
		BufferedReader r = new BufferedReader(new FileReader(currentBusinessLogFile));
		String currentLine;
		String text = "";
		String newString = "";
		while((currentLine = r.readLine())!=null){
			if(currentLine.contains(currentlySelectedRoomText)){
				while((newString = r.readLine())!=null){				
					if(newString.contains(s)){					
						String k = newString.replace(s, s + " TRIGGERED" );										
						String formattedString = k.substring(k.indexOf(s),s.length()+14);
						text=k;
						r.close();					
						return "<br> ->"+formattedString + "<br> \n";
					}																			
				}				
			}		
		}
		r.close();
		return "";	
	}


	/**
	 * 
	 * Creating action listerner for every buttons in the Contructor GUI
	 */
	private void addListeners() throws IOException {
		// DELETE BUTTON
		/*deleteRoomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedInd = list.getSelectedIndex();
				Room currentRoom = listModel.getElementAt(selectedInd);
				try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(currentBusinessLogFile, true))))
				{
					String statusText = "";

					statusText += "<html><p><b>ROOM : </b>" + currentRoom.getRoomName() + "</p>";
					statusText += "<p><b>RISK LEVEL : </b>" + currentRoom.getRiskLevel() + "</p>";
					statusText += "<p><b>ALARMS : </b></p>";
					statusText += InformationDisplay.getAlarmNames(currentRoom.getAlarms()) + "\n";
					
					writer.write("\n<br><br><b>*ROOM DELETION*</b>\n");
					writer.write(statusText);
				}
				catch (Exception e1)
				{
					e1.printStackTrace();
				}
				listModel.removeElementAt(selectedInd);
				currentBusiness.getRooms().remove(currentRoom);
			}
		});*/
			
		// UPDATE CONSOLE THROUGH JLIST SELECTIONS
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				Room currentRoom = list.getSelectedValue();
				//System.out.println("Currently selected room: " + currentlySelectedRoomText );				
				if (currentRoom != null) {
					currentlySelectedRoomText = currentRoom.getRoomName();
					String statusText = "";
					statusText += "<html><p><b>ROOM : </b>" + currentRoom.getRoomName() + "</p>";
					//statusText += "<p><b>RISK LEVEL : </b>" + currentRoom.getRiskLevel() + "</p>";
					statusText += "<p><b>ALARMS : </b></p>";
					statusText += InformationDisplay.getAlarmNames(currentRoom.getAlarms()) + "\n";
					consoleTextArea.setText(
						"<b>CURRENT BUSINESS : </b>" + currentBusiness.getName() + "\n" + statusText + "</html>");
					inPanel.model.clear();
					for(Alarm a : currentRoom.getAlarms()) {
						inPanel.model.addElement(a);
					}
				}
			}
		});
		
		
		// BACK BUTTON
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				topFrame.dispose();
			}
		});
		
	
		// EDIT ROOM BUTTON
		/*editRoomButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Room currentRoom = list.getSelectedValue();
				ArrayList<Alarm> alarms = createSelectedAlarmsArray();
				String riskLevel = createRiskStringFromPanel();
				currentRoom.setAlarms(alarms);
				currentRoom.setRiskLevel(riskLevel);
				try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(currentBusinessLogFile, true))))
				{
					String statusText = "";

					statusText += "<html><p><b>ROOM : </b>" + currentRoom.getRoomName() + "</p>";
					statusText += "<p><b>RISK LEVEL : </b>" + currentRoom.getRiskLevel() + "</p>";
					statusText += "<p><b>ALARMS : </b></p>";
					statusText += InformationDisplay.getAlarmNames(alarms) + "\n";
					
					writer.write("\n<br><br><b>*ROOM EDIT*</b>\n");
					writer.write(statusText);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		});*/
		
		//TRIGGER BUTTON
		//That included writing log to the file
		triggerButton.addActionListener(new ActionListener()
		{	
			
			//PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(currentBusinessLogFile,true)));
			
			public void actionPerformed(ActionEvent e)
			{
				String alarmTriggeredString = "";
				String alarmName = "";
				Alarm trigAlarm = inPanel.list.getSelectedValue();
				sql.logAlarm(trigAlarm, currentBusiness);
				alarmName = trigAlarm.getName();
				popUp(trigAlarm.goesOff());
				
				alarmTriggeredString = "<br><br><br><br>WARNING!!!<br> " + currentlySelectedRoomText + " room alarms have been TRIPPED\n" + alarmTriggeredString;
				alarmTriggeredString = "<span style=font:16;color:red;text-decoration:underline;>" + alarmTriggeredString + "</span>";
				
				Penguin p = new Penguin();
				String user = p.getUser();
				String pass = p.getPassword();
				String businessContact = "";
				Properties props = new Properties();
				Room ro = list.getSelectedValue();
				String roomName = ro.getRoomName();
				Business bus = (Business) hw.currentSelection;
				String busCurrentSelect = bus.checkRoom(roomName);
				if (bus.getName().equals(busCurrentSelect))
				{
					businessContact = bus.getContactI();
				}

				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class",
						"javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");

				Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() 
						{
							protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(user, pass);
						}
					});

				try 
				{
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(user));
					message.setRecipients(Message.RecipientType.TO,
							InternetAddress.parse(businessContact));
					message.setSubject("PENGUIN EMERGENCY ALERT");
					message.setText( alarmName + " alarm has been triggered in " + currentlySelectedRoomText );

					Transport.send(message);

				} 
				catch (MessagingException me) 
				{
					throw new RuntimeException(me);
				}
									
			/*	
				try{
				if(LogAnalyzer.saveCounter>0){
					String s1;
					String goodText = "";						
					BufferedReader r = new BufferedReader(new FileReader(currentBusinessLogFile));
					try {
						while((s1=r.readLine())!=null){
							System.out.println(s1);
							if(s1.contains("</html>")){
								System.out.println("Found </html> tag");

							}
							else if(s1.contains("<body>")){
								System.out.println("Found <body> tag");
							}
							else if(s1.contains("</body>")){
								System.out.println("Found </body> tag");
							}
							else if(s1.contains("<html>")){
								System.out.println("Found <html> tag");
							}
							else{
								goodText+= "\n" + s1;
							}
							
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					
					PrintWriter write2 = new PrintWriter(new BufferedWriter(new FileWriter(currentBusinessLogFile, false)));
					write2.write("<html>"+goodText + alarmTriggeredString + "</html>");
					write2.close();
				
			}	
				else{
					
					BufferedReader r = new BufferedReader(new FileReader(currentBusinessLogFile));
					PrintWriter write2 = new PrintWriter(new BufferedWriter(new FileWriter(currentBusinessLogFile, true)));
					write2.write(alarmTriggeredString);
					write2.close();
				}
				}
				catch(IOException r){
					
				}*/
			}

		});
	
	
		// ADD ROOM BUTTON
		/*createRoomButton.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				try{
				
			
				Alarm[] alarms = createSelectedAlarmsArray(); 
				String riskLevel = createRiskStringFromPanel();
				Room newRoom = new Room(addRoomTextInput.getText(), alarms, riskLevel); 
				currentBusiness.getRooms().add(newRoom); 													
				listModel.addElement(newRoom); 
				String statusText = "";

				statusText += "<p><p><b>ROOM : </b>" + newRoom.getRoomName() + "</p>\n";				
				statusText += "<p><b>RISK LEVEL : </b>" + newRoom.getRiskLevel() + "</p>\n";
				statusText += "<p><b>ALARMS : </b></p>\n";
				statusText += InformationDisplay.getAlarmNames(alarms) + "\n";
				statusText += "<p><b>Creation Status </b></p>: <span style=\"color:Green\">Success</span> ";

				consoleTextArea.setText("<b>Current Business : </b>" + currentBusiness.getName() + "\n" + statusText);

				// Write info to the log file about this rooms creation
				
					String writeString = "<br><br><br><br><br><br><b>*ROOM CREATION*</b>\n";
					writeString += "<br><b>Room Name : </b>\n" + newRoom.getRoomName();
					writeString += "<br><b>Risk Level : </b>\n" + newRoom.getRiskLevel();
					writeString += "<br><b>Alarms : </b>\n" + InformationDisplay.getAlarmNames(alarms);
					writeString += "<b>Created on : </b>\n" + "<span style=\"color:rgb(0, 137, 178);font:14px;\">"
							+ InformationDisplay.dateOfCreationFormatted() + " at "
							+ InformationDisplay.timeOfCreationFormatted() + "</span><br><br><br><br><br><br><br></p>";
					writeString += "\n\n";
					//File currentFile = currentBusiness.getFile();
					//PrintWriter write = new PrintWriter(new BufferedWriter(new FileWriter(currentBusinessLogFile, true)));
					if(LogAnalyzer.saveCounter>0){
						String s;
						String goodText = "";						
						BufferedReader r = new BufferedReader(new FileReader(currentBusinessLogFile));
						while((s=r.readLine())!=null){
							System.out.println(s);
							if(s.contains("</html>")){
								System.out.println("Found </html> tag");

							}
							else if(s.contains("<body>")){
								System.out.println("Found <body> tag");
							}
							else if(s.contains("</body>")){
								System.out.println("Found </body> tag");
							}
							else if(s.contains("<html>")){
								System.out.println("Found <html> tag");
							}
							else{
								goodText+= "\n" + s;
							}
							
						}
					
						System.out.println(LogAnalyzer.saveCounter + "SAVE COUNTER");
						PrintWriter write2 = new PrintWriter(new BufferedWriter(new FileWriter(currentBusinessLogFile, false)));
						write2.write("<html>"+goodText + writeString + "<br></html>");
						write2.close();
					}
					else{
					//write.write("\n\n");
					//write.write(writeString);
					//write.close();
					addRoomTextInput.setText("");
					}
				}
			
				 catch (FileNotFoundException c) {
					System.out.println(c);
				} catch (IOException io) {
					System.out.println(io);
				}
			}
		}); */
	}
	

	/**
	 *  iterate through risk panel and return string of the currently checked
	 *	@return risk level
	 **/
	/*private String createRiskStringFromPanel() {
		String riskLevel = "";
		JPanel panelArray2 = (JPanel) alarmsPanel.getComponent(1);
		for (Component c : panelArray2.getComponents()) {
			if (c instanceof JRadioButton) {
				if (((JRadioButton) c).isSelected()) {
					riskLevel = ((JRadioButton) c).getText();
				}
			}
		}
		return riskLevel;
	}*/

	/**
	 *  Create the alarms array for the current room to be built
	 * @return the alarms that built in the current room
	 */
	private ArrayList<Alarm> createSelectedAlarmsArray() {
		ArrayList<Alarm> alarms = new ArrayList<Alarm>();
		JPanel panelArray = (JPanel) alarmsPanel.getComponent(0);
		for (Component c : panelArray.getComponents()) {
			if (c instanceof JCheckBox) {
				if (((JCheckBox) c).isSelected()) {
					String alarmText = ((JCheckBox) c).getText();
					alarms.add(checkAlarm(alarmText));					
				}
			}
		}
		return alarms;
	}
	
	/** Method [to be used in createSelectedAlarmsArray()] checking the name of the the alarms
	  * If the box/boxes [ that is/are checked ] is matching the case/cases
	  *It will create that alarm
	  **/
	private Alarm checkAlarm(String i)
	{
		Alarm al = null;
		Room currentRoom = list.getSelectedValue();
		switch(i)
		{
			/*case "No Alarm":
				al = new NoAlarm ("No Alarm", 0,);
				break;
				*/
			case "Chemical":
				//al = currentRoom.getAlarms()
				break;
			case "Fire":
				al = new FireAlarm ("Fire Alarm", 4, currentRoom.getId());
				break;
			/*case "Window":
				al = new WindowAlarm ("Window Alarm", 1);
				break; */
			case"Invasion":
				al = new DoorAlarm ("Door Alarm", 1, currentRoom.getId());
				break;
			case "Flood":
				al = new FloodAlarm ("Flood Alarm", 3, currentRoom.getId());
				break;
			/*case "Zombie Apocalypse":
				al = new ZombieAlarm ("Zombie Apocalypse", 9999);
				break;*/
		}
		
		return al;
	} 
	
	/** Method [will be used in the trigger button] that passes a String
	  * Which will create a message dialog [ invoke the goesOff() method ] 
	  */
	public void popUp(String i)
	{
		JOptionPane.showMessageDialog(topFrame, i);
	}
	
	
	// Load list of rooms from current business upon opening
	private void loadRoomList() {
		for (Room r : currentBusiness.getRooms()) {
			listModel.addElement(r);
		}
	}
}
