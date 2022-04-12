package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.interfaces.GameEngine;
import view.MainFrame;

public class DealPlayerController implements ActionListener{
	
	private GameEngine engine;
	private MainFrame frame;

	public DealPlayerController(GameEngine engine, MainFrame frame) {
		this.engine = engine;
		this.frame = frame;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String playerID = frame.getToolbar().getSelectedPlayerID();
		String playerName = frame.getToolbar().getSelectedPlayerName();
		
		if(frame.getCardpanel().getPlayerCardsLists().containsKey(playerID))
		{
			frame.getCardpanel().getPlayerCardsLists().get(playerID).clear();
		}
		
		new Thread()
		{
			@Override
			public void run()
			{
				if(!(playerID.equals(" ")))
				{
					engine.dealPlayer(engine.getPlayer(playerID), 100);
				}
				frame.getCardpanel().callDealHouseIfAllPlayersHaveDealt();
			}
		}.start();
		
		frame.getStatusbar().getStatus().setText(String.format("%s has dealt.", playerName));
		frame.getToolbar().setAllButtons(false);
	}
}
