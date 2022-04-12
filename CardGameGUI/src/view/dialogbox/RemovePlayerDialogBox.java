package view.dialogbox;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import model.interfaces.GameEngine;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class RemovePlayerDialogBox extends JOptionPane{
	
	private String selectedRemovePlayer;
	
	private void validateCondition(String option)
	{
		if(option == null)
		{
			this.selectedRemovePlayer = "";
		}
	}

	public String getRemovePlayerName(GameEngine engine)
	{
		String[] choices = new String[engine.getAllPlayers().size()];
		ArrayList<Player> playerList = new ArrayList<Player>(engine.getAllPlayers());		
		
		for(int i = 0; i < playerList.size(); i++)
		{
			if(!(playerList.get(i).getPlayerId().equals(" ")))
			{
				String playerID_Name = String.format("%s - %s", playerList.get(i).getPlayerId(), playerList.get(i).getPlayerName());
				choices[i] = playerID_Name;
			}
		}
		
		selectedRemovePlayer = (String) JOptionPane.showInputDialog(this, "Select the player (ID - Name):", "Remove a Player", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);		
		validateCondition(selectedRemovePlayer);
		return selectedRemovePlayer;	
	}
}
