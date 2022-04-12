package model;

import model.interfaces.PlayingCard;

public class PlayingCardImpl implements PlayingCard{
	
	private Suit suit;
	private Value value;
	private int score;
	
	public PlayingCardImpl(Suit suit, Value value) 
	{
		this.suit = suit;
		this.value = value;
	}
	
	@Override
	public Suit getSuit() 
	{
		return suit;
	}

	@Override
	public Value getValue() 
	{
		return value;
	}

	@Override
	public int getScore() 
	{
		switch(this.value) 
		{
			case EIGHT:
				score = 8;
				break;
			case NINE:
				score = 9;
				break;
			case TEN:
				score = 10;
				break;
			case JACK:
				score = 10;
				break;
			case QUEEN:
				score = 10;
				break;
			case KING:
				score = 10;
				break;
			case ACE:
				score = 11;
				break;
		}
		return score;
	}

	@Override
	public String toString() 
	{
		return String.format("Suit: " + this.suit + ", Value: " + this.value + ", Score: " + getScore());
	}

	@Override
	public boolean equals(PlayingCard card) 
	{
		if(suit.equals(card.getSuit()) && value.equals(card.getValue())) 
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if(obj instanceof PlayingCard) 
		{
			PlayingCard card = (PlayingCard) obj;
			return suit.equals(card.getSuit()) && value.equals(card.getValue());
		}		
		return false;
	}

	@Override
	public int hashCode() 
	{
		return suit.hashCode() + value.hashCode();
	}	

}
