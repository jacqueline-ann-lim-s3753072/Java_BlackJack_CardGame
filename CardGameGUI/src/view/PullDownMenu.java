package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.PullDownMenuController;

@SuppressWarnings("serial")
public class PullDownMenu extends JMenuBar{
	
	private JMenu menu = new JMenu("File");

	private JMenuItem addPlayer;
	private JMenuItem removePlayer;
	private JMenuItem exit;

	public PullDownMenu(MainFrame frame)
	{
		add(menu);	
		menuSetup();
		menu.add(addPlayer);
		menu.add(removePlayer);
		menu.add(exit);
		
		PullDownMenuController pullDownMenuController = new PullDownMenuController(frame);
		addPlayer.addActionListener(pullDownMenuController);
		removePlayer.addActionListener(pullDownMenuController);
		exit.addActionListener(pullDownMenuController);
	}
	
	private void menuSetup()
	{
		addPlayer = new JMenuItem("Add Player");
		removePlayer = new JMenuItem("Remove Player");
		exit = new JMenuItem("Exit");
	}
}
