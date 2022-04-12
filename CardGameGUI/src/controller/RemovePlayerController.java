package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.interfaces.GameEngine;
import view.MainFrame;
import view.dialogbox.RemovePlayerDialogBox;

public class RemovePlayerController implements ActionListener{
	
	private GameEngine engine;
	private MainFrame frame;
	private RemovePlayerDialogBox dialogbox = new RemovePlayerDialogBox();

	public RemovePlayerController(GameEngine engine, MainFrame frame)
	{
		this.engine = engine;
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {	
		String removePlayer = dialogbox.getRemovePlayerName(engine);
		
		if(!(removePlayer.equals("")))
		{
			String playerID = removePlayer.substring(0, removePlayer.indexOf("-")).trim();
			String playerName = removePlayer.substring(removePlayer.indexOf("-") + 1).trim();
			
			if(frame.getCardpanel().getPlayerCardsLists().containsKey(playerID))
			{
				frame.getCardpanel().getPlayerCardsLists().get(playerID).clear();
			}
			
			frame.getSummarypanel().deleteRowFromSummaryPannel(playerID);
			frame.getToolbar().getDropDownPlayersMenu().removeItem(String.format("ID: %s | Name: %s", playerID, playerName));
			frame.getStatusbar().getStatus().setText(String.format("%s has been removed.", playerName));
			engine.removePlayer(engine.getPlayer(playerID));
		}
		frame.getToolbar().disabledIfEmpty(engine.getAllPlayers().size() > 0);
		frame.getSummarypanel().checkIfAllPlayersHaveDealt(engine, frame);
		
		if(frame.getSummarypanel().checkIfHasPlayers(engine, frame) && frame.getSummarypanel().checkIfAllPlayersHaveDealt(engine, frame))
		{
			frame.getCardpanel().callDealHouseIfAllPlayersHaveDealt();
		}
	}
}
