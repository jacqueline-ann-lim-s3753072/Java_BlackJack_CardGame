package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.interfaces.GameEngine;
import view.MainFrame;

public class DropDownComboBoxController implements ActionListener{
	private GameEngine engine;
	private MainFrame frame;

	public DropDownComboBoxController(GameEngine engine, MainFrame frame) 
	{
		this.engine = engine;
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String playerID = frame.getToolbar().getSelectedPlayerID();  
		String playerName = frame.getToolbar().getSelectedPlayerName();
        
        if(playerID != null) 
        {    		
        	if(!(playerID.equals(" ")) && engine.getPlayer(playerID).getResult() == 0) //If the player has NOT dealt yet
        	{
        		frame.getToolbar().setAllButtons(true); 
        		frame.getToolbar().disabledIfNoPlacedBet(engine.getPlayer(playerID).getBet() > 0);
        	}
        	else
        	{
            	frame.getToolbar().setAllButtons(false); 
        	}
        	frame.getSummarypanel().checkIfHasPlayers(engine, frame);
        	frame.getCardpanel().updatePlayerCardsLists(playerID, null); 
        	frame.getStatusbar().getStatus().setText(String.format("%s is selected.", playerName));
        }
	}
}
