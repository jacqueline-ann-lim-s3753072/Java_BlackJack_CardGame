package view.dialogbox;

import javax.swing.JOptionPane;

import model.interfaces.GameEngine;

@SuppressWarnings("serial")
public class PlaceBetDialogBox extends JOptionPane{

	private int bet;
	
	private void validateCondition(String inputBet, int playerPoints) 
	{
		if(inputBet == null)
		{
			bet = 0;
		}
		else
		{
			try
			{
				int intBet = Integer.parseInt(inputBet.trim());
				checkValidBet(intBet, playerPoints);
			}
			catch(NumberFormatException e)
			{
				bet = 0;
				JOptionPane.showMessageDialog(this, "Invalid Bet. Please enter a numeric value.");
			}
		}
	}
	
	private void checkValidBet(int intBet, int playerPoints)
	{
		if(intBet <= playerPoints && intBet > 0)
		{
			this.bet = intBet;
		}
		else if(intBet > playerPoints)
		{
			bet = 0;
			JOptionPane.showMessageDialog(this, "Invalid Bet. You do not have enough points.");
		}	
		else
		{
			bet = 0;
			JOptionPane.showMessageDialog(this, "Invalid Bet. Your bet should be greater than 0.");
		}
	}

	public int getBet(GameEngine engine, String playerID)
	{
		String inputBet = JOptionPane.showInputDialog(this, String.format("Enter %s's bet:", engine.getPlayer(playerID).getPlayerName()), "Set the Player's Bet", JOptionPane.PLAIN_MESSAGE);	
		validateCondition(inputBet, engine.getPlayer(playerID).getPoints());
		return bet;
	}
}
