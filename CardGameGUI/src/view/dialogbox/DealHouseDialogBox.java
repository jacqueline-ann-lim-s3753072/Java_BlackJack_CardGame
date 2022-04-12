package view.dialogbox;

import javax.swing.JOptionPane;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import view.MainFrame;

@SuppressWarnings("serial")
public class DealHouseDialogBox extends JOptionPane{
	
	private GameEngine engine;
	private MainFrame frame;
	
	public DealHouseDialogBox(GameEngine engine, MainFrame frame) 
	{
		this.engine = engine;
		this.frame = frame;
	}

	public void automatedDealHouse()
	{
		int option = JOptionPane.showConfirmDialog (null, "Press OK to Continue.","The House Ready", JOptionPane.PLAIN_MESSAGE);
		String playerName = frame.getToolbar().getSelectedPlayerName();
		
		if(option == JOptionPane.OK_OPTION)
		{
			if(frame.getCardpanel().getPlayerCardsLists().containsKey(" "))
			{
				frame.getCardpanel().getPlayerCardsLists().get(" ").clear();
			}
			
			new Thread()
			{
				@Override
				public void run()
				{
					engine.dealHouse(100);
					playAgain();
					checkIfPlayerHasPoints();
					frame.getSummarypanel().checkIfHasPlayers(engine, frame);
				}
			}.start();
			
			frame.getStatusbar().getStatus().setText(String.format("%s has dealt.", playerName));
		}
	}
	
	private void checkIfPlayerHasPoints()
	{
		for(Player player: engine.getAllPlayers())
		{
			if(!(player.getPlayerId().equals(" ")) && player.getPoints() == 0)
			{
				frame.getCardpanel().getPlayerCardsLists().get(player.getPlayerId()).clear();
				frame.getSummarypanel().deleteRowFromSummaryPannel(player.getPlayerId());
				frame.getToolbar().getDropDownPlayersMenu().removeItem(String.format("ID: %s | Name: %s", player.getPlayerId(), player.getPlayerName()));
				engine.removePlayer(player);
			}
		}
	}
	
	private void playAgain()
	{
		int option = JOptionPane.showConfirmDialog (null, String.format("The House's result is %d.\nDo you want to play again?", frame.getCardpanel().getHouseResult()), "Game Over", JOptionPane.YES_NO_OPTION);
		
		if(option == JOptionPane.YES_OPTION)
		{
			for(Player player: engine.getAllPlayers())
			{
				player.setResult(0);
				frame.getSummarypanel().setBetToSummaryPannel(player.getPlayerId(), 0);
				frame.getSummarypanel().setResultToSummaryPannel(player.getPlayerId(), 0);
			}
		}
		else if(option == JOptionPane.NO_OPTION)
		{
			System.exit(0);
		}
	}	
}
