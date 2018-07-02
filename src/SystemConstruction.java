

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
/**
 * 
 * GUI of construction of a room by adding alamrs and risks to it
 *
 */
public class SystemConstruction {
	private GridBagLayout grid;
	private JComponent[] comp;
	private Business busi;
	private JPanel radioButtons;
	private JPanel alarmPanel;
	private SystemConstructionPanelInput inputPanel;
	private SystemConstructionPanelJList list;
	private SystemConstructionConsole console;
	private JFrame frame;

	/**
	 * 
	 * Constructor for construction GUI
	 * 
	 * 
	 */
	public SystemConstruction(Business busi, HomeWindowMain parentFrame, SQLAccess sql) throws IOException {
		frame = new JFrame("Room Construction");
		frame.setTitle("Room Construction");
		frame.setLayout(new GridLayout(2, 2));

		this.busi = busi;
		comp = new JComponent[10];

		alarmPanel = alarmConstruction();
		inputPanel = new SystemConstructionPanelInput(busi);
		console = new SystemConstructionConsole();
		list = new SystemConstructionPanelJList(alarmPanel, busi, inputPanel, console, frame, sql);

		alarmConstruction();

		JComponent[] consoleArray = console.getArray();
		JTextPane txtArea = (JTextPane) consoleArray[0];
		txtArea.setText("<html><b>CURRENT BUSINESS : </b>" + busi.getName() + "</html>");

		frame.add(alarmPanel);
		frame.add(console);
		frame.add(list);
		frame.add(inputPanel);

		frame.setLocation(parentFrame.getLocation());
		frame.setPreferredSize(new Dimension(800, 600));
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * 
	 * Creating the panel that has the Alarm panel and Risk Panel
	 */
	private JPanel alarmConstruction() {
		JPanel p = new JPanel();
		Border b = BorderFactory.createLineBorder(Color.decode("#00BFFF"), 1, true);
		p.setBorder(BorderFactory.createTitledBorder(b, "Trigger Alarm"));
		SystemConstructionPanelAlarms alarmsPanel = new SystemConstructionPanelAlarms();
		SystemConstructionPanelRisk riskPanel = new SystemConstructionPanelRisk();
		p.add(alarmsPanel);
		p.add(riskPanel);
		return p;
	}

}
