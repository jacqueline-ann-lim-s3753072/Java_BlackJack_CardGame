package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

@SuppressWarnings("serial")
public class StatusBar extends JPanel{
	
	private JLabel status = new JLabel(" ", JLabel.CENTER); //default text
	
	public StatusBar() 
	{
		setLayout(new GridLayout(1,1));
		Border b = BorderFactory.createLineBorder(Color.BLACK);
		
		status.setBorder(b);
		status.setFont(new Font("SansSerif", Font.BOLD, 16));
		add(status);
	}

	public JLabel getStatus() {
		return status;
	}
	
}
