

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javafx.*;

/**
 * 
 * GUI displaying the title for the main project
 *
 */
public class HomeWindowMainTitle extends JPanel {

	public HomeWindowMainTitle() {
		this.setLayout(new FlowLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY, 4, true));
		makeTitle();
	}

/**
 * Displaying the title for Security
 */
	private void makeTitle() {
		JLabel title = new JLabel(
				"<html>Welcome to Penguin Security Systems Inc <br> Keeping you safe since 1996.</html>");
		title.setFont(new Font("Monospaced", Font.PLAIN, 25));
		title.setForeground(Color.BLACK);
		this.add(title);
	}

}
