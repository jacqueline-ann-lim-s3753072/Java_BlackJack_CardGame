package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.MainFrame;

public class PullDownMenuController implements ActionListener{
	
	private MainFrame frame;
	
	public PullDownMenuController(MainFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getActionCommand().equals("Add Player"))
		{
			frame.getToolbar().getAddPlayerButton().doClick();
		}
		else if(e.getActionCommand().equals("Remove Player"))
		{
			frame.getToolbar().getRemovePlayerButton().doClick();
		}
		else if(e.getActionCommand().equals("Exit"))
		{
			System.exit(0);
		}
	}
}
