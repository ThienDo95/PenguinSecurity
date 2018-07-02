

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SystemConstructionPanelInput extends JPanel {
	private Border blackline;
	private FlowLayout lay;
	private JComponent[] comp;
	public JList<Alarm> list;
	public DefaultListModel<Alarm> model;

	public SystemConstructionPanelInput(Business business) {
		blackline = BorderFactory.createLineBorder(Color.decode("#00BFFF"));
		lay = new FlowLayout();
		comp = new JComponent[1];
		this.setLayout(lay);
		this.setBorder(BorderFactory.createTitledBorder(blackline, "Alarms"));
		this.setPreferredSize(new Dimension(200, 125));
		model = new DefaultListModel<Alarm>();
		list = new JList<Alarm>(model);
		JScrollPane listScroll = new JScrollPane(list);
		listScroll.setPreferredSize(new Dimension(290, 160));

		listScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				
				
			}
			
		});
		
		this.add(listScroll);
		
	}

	public JComponent[] getArray() {
		return comp;
	}

	protected JPanel getPanel() {
		return this;
	}

}