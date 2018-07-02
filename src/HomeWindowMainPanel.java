
import javax.swing.*;
import java.sql.*;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.ClassNotFoundException;
import java.io.FileNotFoundException;
/***
 * 
 * Main Window Panel is responsible for holding an Array of component
 * and will implement serializable to save its state
 *
 */
public class HomeWindowMainPanel extends JPanel implements Serializable {
	// An array of JComponent
	private JComponent[] comp;
	// Collection of Business 
	private DefaultListModel<Business> model;
	// Declare the home main window
	private HomeWindowMain parentFrame;
	
	private SQLAccess sql;
	
	protected static Object currentSelection;

	/**
	 * Constructor takes on parameter
	 * @param parentFrame set the frame using HomeMainWindow class
	 */
	public HomeWindowMainPanel(HomeWindowMain parentFrame, SQLAccess sql) {
		this.sql = sql;
		// initialize the collection of business
		model = new DefaultListModel<Business>();
		// initialize the array of JComponent
		comp = new JComponent[10];
		// Set lay out for the main window panel
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		// Set the frame using the setting in Home Main Window
		this.parentFrame = parentFrame;
		addJList();
		// invoke buttonPanel method
		buttonPanel();
		// invoke the addListener method
		addListeners();
		
	}

	/**
	 * Method to add the the list of Business
	 * Setting the Border for the JList  and make it is list Scroll
	 */
	private void addJList() {
		GridBagConstraints c = new GridBagConstraints();
		JList<Business> j = new JList<>(model);
		JScrollPane listScroll = new JScrollPane(j);
		int vericalPolicy = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;
		listScroll.setVerticalScrollBarPolicy(vericalPolicy);
		j.setSelectedIndex(0);
		j.setBorder(LineBorder.createGrayLineBorder());
		ArrayList<Business> businesses = sql.createBusiness();
		sql.addRooms();
		sql.addAlarms();
		for(Business b : businesses) {
			model.addElement(b);
		}
		this.add(listScroll, c);
		comp[0] = j;
	}

	/**
	 * Method is adding the all the Button to the main window 
	 * First initialize it and put it into an array
	 * Afterward add each and individual to the panel
	 */
	private void buttonPanel() {
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setSize(200, 300);
		JButton add = new JButton("     +     ");
		JButton remove = new JButton("     -     ");
		JButton load = new JButton(" Load ");
		JButton select = new JButton(" Select ");
		JButton save = new JButton(" Save ");
		JButton exit = new JButton(" Exit ");
		JButton logAnalysis = new JButton(" Log Analyzer");
		comp[1] = add;
		comp[2] = remove;
		comp[3] = load;
		comp[4] = select;
		comp[5] = save;
		comp[6] = exit;
		comp[8] = logAnalysis;
		//buttonPanel.add(add);
		//buttonPanel.add(remove);
		//buttonPanel.add(load);
		buttonPanel.add(select);
		//buttonPanel.add(save);
		//buttonPanel.add(logAnalysis);
		buttonPanel.add(exit);
		this.add(buttonPanel);
		//this.add(textInputPanel());
	}

	/**
	 * A blank panel will display business name after entering it
	 * 
	 * @return the text JPanel that contains the business name and the text field
	 */
	private JPanel textInputPanel() {
		JPanel p = new JPanel(new FlowLayout());
		JTextField name = new JTextField(30);
		JLabel l = new JLabel("Business Name: ");
		comp[7] = name;
		p.add(l);
		p.add(name);
		return p;
	}

	/**
	 * Method to add all of the listeners will be used in the main window
	 * It contains load, save, add, delete, select, exit, and load buttons.
	 */
	private void addListeners() {
		// LOAD BUTTON -> Will retrieve information from DB
		JButton loadButton = (JButton) comp[3];
		loadButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JList loadNewList = (JList) comp[0];
					File currentDir = new File(System.getProperty("user.dir"));
					JFileChooser loadFile = new JFileChooser();
					loadFile.setCurrentDirectory(currentDir);
					int file = loadFile.showOpenDialog(parentFrame);

					FileInputStream loadList = new FileInputStream(loadFile.getSelectedFile());
					ObjectInputStream loadListObj = new ObjectInputStream(loadList);
					DefaultListModel<Business> loadedModel = (DefaultListModel<Business>) loadListObj.readObject();
					loadNewList.setModel(loadedModel);
					model = (DefaultListModel<Business>) loadNewList.getModel();
					comp[0] = loadNewList;
					parentFrame.repaint();
				} catch (FileNotFoundException zz) {
					System.out.println(zz);
				} catch (IOException zzz) {
					System.out.println(zzz);
				} catch (ClassNotFoundException zas) {
					System.out.println(zas);
				}
			}
		});

		// SAVE BUTTON -> Will become Send  to DB later 
		JButton saveButton = (JButton) comp[5];
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JList list5 = (JList) comp[0];
					//JOptionPane fileName = new JOptionPane();
					String fileN = JOptionPane.showInputDialog(parentFrame, "Enter a name to save your file as",
							"Save File As", JOptionPane.PLAIN_MESSAGE);
					FileOutputStream out = new FileOutputStream(fileN + ".BusinessNetwork");
					ObjectOutputStream outObj = new ObjectOutputStream(out);
					outObj.writeObject(list5.getModel());
					outObj.close();
				} catch (FileNotFoundException r) {
					System.out.println(r);
				} catch (IOException a) {
					System.out.println(a);
				}
			}
		});
		// ADD BUTTON -> Will be deleted since it has no functionality in this project
		// But will be taking into consideration
		JButton addButton = (JButton) comp[1];
		/*addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField text = (JTextField) comp[7];
				Business busi = new Business(text.getText(), null);
				model.addElement(busi);
				try {
					PrintWriter write = new PrintWriter(busi.getName() + ".SecurityLog");
					PrintWriter writeParseable = new PrintWriter(busi.getName() + ".SecurityLogParser");
					write.write("<b style=\"font:20;\">SECURITY LOG</b>" + " for "
							+ "<span style=\"font:16;color:	rgb(255,165,0);text-decoration:underline;\">"
							+ busi.getName() + "</span>"); 
															
					write.close();
					String businessLogFileString =  busi.getName()
							+ ".SecurityLog"; 
												
					File businessLogFileFile = new File(businessLogFileString); 
					String logParseFileString =  busi.getName()
							+ ".Printable";
					File logParseFile = new File(logParseFileString);
					busi.setLogFile(businessLogFileFile); 

					busi.setParseableLogFile(logParseFile);
				} catch (FileNotFoundException k) {
					System.out.println(k);
				}
				text.setText("");
			}
		});*/

		// DELETE BUTTON
		JButton minusButton = (JButton) comp[2];
		minusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JList list = (JList) comp[0];
				int selectedVal = list.getSelectedIndex();
				model.removeElementAt(selectedVal);
			}
		});

		// SELECT BUTTON
		JButton selectButton = (JButton) comp[4];
		selectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JList list = (JList) comp[0];
				currentSelection = list.getSelectedValue();
				if (currentSelection != null) {
					try {
						SystemConstruction win = new SystemConstruction((Business) currentSelection, parentFrame, sql);
						String currentlySelected = list.getSelectedValue().toString();
						System.out.println(currentlySelected);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		// EXIT BUTTON
		JButton exitButton = (JButton) comp[6];
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// LOG ANALYSIS
		JButton logButton = (JButton) comp[8];
		logButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JList list = (JList) comp[0];
				Object currentSelection = list.getSelectedValue();
				if (currentSelection != null) {
					try {
						LogAnalyzer logScreen = new LogAnalyzer(parentFrame, (Business) currentSelection);
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (BadLocationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});

	}
}
