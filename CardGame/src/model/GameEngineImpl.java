package model;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine{
	
	private Collection <GameEngineCallback> callback = new HashSet<GameEngineCallback>();
	private Map <String, Player> players = new HashMap<String, Player>();
	private Deque <PlayingCard> deck = new LinkedList<PlayingCard>();
	private int houseScore = 0; //made this variable to access it to both dealHouseCallback and dealHouse methods
	
	//for executing sleep() function to pause the thread in milliseconds when dealing cards
	private void wait(int delay)
	{ 		
		try 
		{
			Thread.sleep(delay);
		}
		catch(InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	//This method is to execute all the callback methods when dealing the PLAYER
	private boolean dealPlayerCallback(Player player, PlayingCard card, int delay)
	{
		player.setResult(player.getResult() + card.getScore());
		if(player.getResult() >= GameEngine.BUST_LEVEL) 
		{
			if(player.getResult() > GameEngine.BUST_LEVEL)
			{
				for(GameEngineCallback callback: callback) 
				{
					callback.bustCard(player, card, this);
				}
				wait(delay);
				player.setResult(player.getResult() - card.getScore());
			}
			else //when the final result is EXACTLY 42
			{
				for(GameEngineCallback callback: callback) 
				{
					callback.nextCard(player, card, this);
				}
				wait(delay);
			}
			for(GameEngineCallback callback: callback) 
			{
				callback.result(player, player.getResult(), this);
			}
			wait(delay);
			return false;
		}	
		for(GameEngineCallback callback: callback) 
		{
			callback.nextCard(player, card, this);
		}
		wait(delay);
		return true;
	}
	
	//This method is to execute all the callback methods when dealing the HOUSE
	private boolean dealHouseCallback(PlayingCard card, int delay)
	{		
		houseScore = houseScore + card.getScore(); 
		if(houseScore >= GameEngine.BUST_LEVEL) 
		{
			if(houseScore > GameEngine.BUST_LEVEL) 
			{
				for(GameEngineCallback callback: callback) 
				{
					callback.houseBustCard(card, this);
				}
				wait(delay);
				houseScore = houseScore - card.getScore(); 
			}
			else //when the final result is EXACTLY 42
			{
				for(GameEngineCallback callback: callback) 
				{
					callback.nextHouseCard(card, this);
				}
				wait(delay);
			}
			for(Player played: getAllPlayers()) 
			{
				if(!(played.getPlayerId().equals(" ")) && played.getBet() == 0) //A player with a bet of 0 CANNOT play the game
				{
					removePlayer(played); //gets eliminated hence it will not be displayed in the result
				}
				applyWinLoss(played, houseScore);
			}	
			for(GameEngineCallback callback: callback) 
			{
				callback.houseResult(houseScore, this);
			}
			return false;
		}	
		for(GameEngineCallback callback: callback) 
		{
			callback.nextHouseCard(card, this);
		}
		wait(delay);
		return true;
	}
	
	@Override
	public void dealPlayer(Player player, int delay) throws IllegalArgumentException 
	{
		if(delay < 0 || delay > 1000)
		{
			throw new IllegalArgumentException();
		}
		// checks if players (HashMap) IS NOT empty, then if player is null (for dealHouse)
		//, or player's bet should be greater than 0 after SETTING THE BET on placeBet (for dealPlayer)
		while(!(players.isEmpty()) && (player == null || player.getBet() > 0)) 
		{
			if(deck.isEmpty()) 
			{
				deck = getShuffledHalfDeck();
			}
				PlayingCard card = deck.pop();	
				//separate into two methods instead of using several if-else statements in one method
				if(player != null) 
				{
					if(dealPlayerCallback(player, card, delay) == false)
						break;
				}
				else //the player is NULL only if it calls from the DealHouse
				{				
					if(dealHouseCallback(card, delay) == false)
						break;
				}
		}
	}

	@Override
	public void dealHouse(int delay) throws IllegalArgumentException 
	{
		//since the dealPlayer and dealHouse shares the same codes, especially for drawing cards (PlayingCard)
		dealPlayer(null, delay); 
		for(Player player: getAllPlayers()) //execute after houseResult (Reset the bet and result for the next game)
		{
			player.resetBet();
			this.houseScore = 0;
		}
	}

	@Override
	public void applyWinLoss(Player player, int houseResult) 
	{
		if(player.getResult() < houseResult) 
		{
			player.setPoints(player.getPoints() - player.getBet());
		}
		else if(player.getResult() > houseResult)
		{
			player.setPoints(player.getPoints() + player.getBet());
		}
	}

	@Override
	public void addPlayer(Player player) 
	{
		if(player.getPlayerId() != null)
		{
			if(players.containsKey(player.getPlayerId())) 
			{
				players.replace(player.getPlayerId(), player);
			}
			else 
			{
				players.put(player.getPlayerId(), player);
			}
		}
	}

	@Override
	public Player getPlayer(String id) 
	{
		if(players.containsKey(id)) 
		{
			return players.get(id);
		}
		return null;
	}

	@Override
	public boolean removePlayer(Player player) 
	{
		if(players.containsKey(player.getPlayerId())) 
		{
			players.remove(player.getPlayerId());
			return true;
		}
		return false;
	}

	@Override
	public boolean placeBet(Player player, int bet) 
	{
		if(player.getPlayerId() != null)
		{
			player.setBet(bet);
			return true;
		}
		return false;
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) 
	{
		if(gameEngineCallback != null || !(callback.contains(gameEngineCallback))) 
		{
			callback.add(gameEngineCallback);
		}
	}

	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) 
	{
		if(callback.contains(gameEngineCallback)) 
		{
			callback.remove(gameEngineCallback);
			return true;
		}		
		return false;
	}

	@Override
	public Collection<Player> getAllPlayers() 
	{
		List<Player> playersById = new LinkedList<Player>(players.values()); //shallow copy
		Collections.sort(playersById);
		return new TreeSet<Player>(playersById); 
	}

	@Override
	public Deque<PlayingCard> getShuffledHalfDeck() 
	{
		for(PlayingCard.Suit suit: PlayingCard.Suit.values()) 
		{
			for(PlayingCard.Value value: PlayingCard.Value.values()) 
			{
				deck.add(new PlayingCardImpl(suit, value));
			}
		}
		List <PlayingCard> shuffledDeck = new LinkedList<PlayingCard>(deck);
		Collections.shuffle(shuffledDeck);
		return new LinkedList<PlayingCard>(shuffledDeck);
	}
}
