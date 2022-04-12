package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.interfaces.GameEngine;
import view.MainFrame;
import view.dialogbox.PlaceBetDialogBox;

public class PlaceBetController implements ActionListener{

	private GameEngine engine;
	private MainFrame frame;
	private PlaceBetDialogBox dialogbox = new PlaceBetDialogBox();

	public PlaceBetController(GameEngine engine, MainFrame frame)
	{
		this.engine = engine;
		this.frame = frame;		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {	
		String playerID = frame.getToolbar().getSelectedPlayerID();
		String playerName = frame.getToolbar().getSelectedPlayerName();
		int bet = dialogbox.getBet(engine, playerID); 
		
		if(bet > 0)
		{
			engine.placeBet(engine.getPlayer(playerID), bet);
			frame.getToolbar().disabledIfNoPlacedBet(engine.getPlayer(playerID).getBet() > 0);
			frame.getSummarypanel().setBetToSummaryPannel(playerID, bet);
			frame.getStatusbar().getStatus().setText(String.format("%s has placed bet to %d.", playerName, bet));
		}
	}
}
