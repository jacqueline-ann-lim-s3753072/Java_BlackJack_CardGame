package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.MainFrame;
import view.dialogbox.AddPlayerDialogBox;

public class AddPlayerController implements ActionListener{
	
	private GameEngine engine;
	private MainFrame frame;
	private AddPlayerDialogBox dialogbox = new AddPlayerDialogBox();
	
	public AddPlayerController(GameEngine engine, MainFrame frame) 
	{
		this.engine = engine;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		String ID = dialogbox.getPlayerID();
		String playerName = dialogbox.getPlayerName();
		int initialPoints = dialogbox.getInitialPoints();
		Player newPlayer = new SimplePlayer(ID, playerName, initialPoints);
		
		if(initialPoints > 0) 
		{
			if(frame.getSummarypanel().getPlayerMap(engine).containsKey(ID))
			{
				frame.getSummarypanel().deleteRowFromSummaryPannel(ID);
				frame.getToolbar().getDropDownPlayersMenu().removeItem(String.format("ID: %s | Name: %s", ID, engine.getPlayer(ID).getPlayerName()));
			}
			engine.addPlayer(newPlayer);
			frame.getToolbar().disabledIfEmpty(engine.getAllPlayers().size() > 0);
			frame.getToolbar().getDropDownPlayersMenu().addItem(String.format("ID: %s | Name: %s", ID, playerName));
			frame.getToolbar().getDropDownPlayersMenu().setSelectedItem(String.format("ID: %s | Name: %s", ID, playerName));
			frame.getSummarypanel().addRowToSummaryPannel(newPlayer);
			frame.getStatusbar().getStatus().setText(String.format("%s has been added.", playerName));
		}
	}
}
