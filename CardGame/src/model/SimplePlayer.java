package model;

import model.interfaces.Player;

public class SimplePlayer implements Player{
	
	private String id;
	private String playerName;
	private int initialPoints;
	private int bet;
	private int result;

	public SimplePlayer(String id, String playerName, int initialPoints) 
	{
		this.id = id;
		this.playerName = playerName;
		this.initialPoints = initialPoints;
	}

	@Override
	public String getPlayerName() 
	{
		return playerName;
	}

	@Override
	public void setPlayerName(String playerName) 
	{
		this.playerName = playerName;
	}

	@Override
	public int getPoints() 
	{
		return initialPoints;
	}

	@Override
	public void setPoints(int points)
	{
		this.initialPoints = points;
	}

	@Override
	public String getPlayerId()
	{
		return id;
	}

	@Override
	public boolean setBet(int bet) 
	{
		if (bet >= 0 && this.initialPoints >= bet) 
		{
			this.bet = bet;
			return true;
		}
		return false;
	}

	@Override
	public int getBet() 
	{
		return bet;
	}

	@Override
	public void resetBet() 
	{
		this.bet = 0;
	}

	@Override
	public int getResult() 
	{
		return result;
	}

	@Override
	public void setResult(int result) 
	{
		this.result = result; 
	}

	@Override
	public boolean equals(Player player)
	{
		if(id.equals(player.getPlayerId()))
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Player) 
		{
			Player player = (Player) obj;
			return id.equals(player.getPlayerId());
		}		
		return false;
	}

	@Override
	public int hashCode()
	{
		return id.hashCode();
	}

	@Override
	public int compareTo(Player player) 
	{
		return id.compareTo(player.getPlayerId());
	}
	
	@Override
	public String toString() 
	{
		return String.format("Player: id=%s, name=%s, bet=%d, points=%d, RESULT .. %d", this.id, this.playerName, this.bet, this.initialPoints, this.result);
	}

}
