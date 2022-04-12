package view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

import controller.AddPlayerController;
import controller.DealPlayerController;
import controller.PlaceBetController;
import controller.RemovePlayerController;
import controller.ResetBetController;
import model.interfaces.GameEngine;

@SuppressWarnings("serial")
public class ToolBar extends JToolBar{
	
	private JButton addPlayerButton;
	private JButton removePlayerButton;
	private JButton placeBetButton;
	private JButton resetBetButton;
	private JButton dealPlayerButton;
	private JComboBox<String> dropDownPlayersMenu = new JComboBox<String>();
	
	public ToolBar(MainFrame frame, GameEngine engine) 
	{
		playerButtonsetup();
		add(addPlayerButton); 
		add(removePlayerButton);
		add(dropDownPlayersMenu);
		add(placeBetButton);
		add(resetBetButton);
		add(dealPlayerButton);
				
		disabledIfEmpty(engine.getAllPlayers().size() > 0);
		disabledIfNoPlacedBet(false);
				
		AddPlayerController addPlayerController = new AddPlayerController(engine, frame);
		addPlayerButton.addActionListener(addPlayerController);
		
		RemovePlayerController removePlayerController = new RemovePlayerController(engine, frame);
		removePlayerButton.addActionListener(removePlayerController);
		
		PlaceBetController placeBetController = new PlaceBetController(engine, frame);
		placeBetButton.addActionListener(placeBetController);
		
		ResetBetController resetBetController = new ResetBetController(engine, frame);
		resetBetButton.addActionListener(resetBetController);
		
		DealPlayerController dealPlayerController = new DealPlayerController(engine, frame);
		dealPlayerButton.addActionListener(dealPlayerController);
	}

	public void playerButtonsetup() 
	{
		addPlayerButton = new JButton("Add Player");
		removePlayerButton = new JButton("Remove Player");
		placeBetButton = new JButton("Place Bet");
		resetBetButton = new JButton("Reset Bet");
		dealPlayerButton = new JButton("Deal");
	}

	public void disabledIfEmpty(boolean condition)
	{
		removePlayerButton.setEnabled(condition);
		placeBetButton.setEnabled(condition);
	}
	
	public void setAllButtons(boolean condition) 
	{
		addPlayerButton.setEnabled(condition);
		removePlayerButton.setEnabled(condition);
		placeBetButton.setEnabled(condition);
		resetBetButton.setEnabled(condition);
		dealPlayerButton.setEnabled(condition);
	}

	public void	disabledIfNoPlacedBet(boolean condition) {
		resetBetButton.setEnabled(condition);
		dealPlayerButton.setEnabled(condition);
	}

	public JComboBox<String> getDropDownPlayersMenu() 
	{
		return dropDownPlayersMenu;
	}
	
	public JButton getAddPlayerButton() 
	{
		return addPlayerButton;
	}

	public JButton getRemovePlayerButton() 
	{
		return removePlayerButton;
	}

	public String getSelectedPlayerID()
	{
		String playerID = null;
        String selectedPlayer = (String) dropDownPlayersMenu.getSelectedItem();

        if(selectedPlayer == "The House")
        {
        	playerID = " ";
        }
        else if(selectedPlayer != null)
        {
            playerID = selectedPlayer.substring(selectedPlayer.indexOf(":", 0) + 1, selectedPlayer.indexOf("|")).trim();
        }
        return playerID;
	}
	
	public String getSelectedPlayerName()
	{
		String playerName = null;
		String selectedPlayer = (String) dropDownPlayersMenu.getSelectedItem();
		
		if(selectedPlayer == "The House")
		{
			playerName = selectedPlayer;
		}
		else if(selectedPlayer != null)
        {
			int indexOfName = selectedPlayer.indexOf("|");
            playerName = selectedPlayer.substring(selectedPlayer.indexOf(":", indexOfName) + 1).trim();
        }
		return playerName;
	}
}
