package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JPanel;

import controller.CardResizeController;
import controller.DropDownComboBoxController;
import model.SimplePlayer;
import model.interfaces.GameEngine;
import model.interfaces.PlayingCard;
import view.dialogbox.DealHouseDialogBox;

@SuppressWarnings("serial")
public class CardPanel extends JPanel{
	private final String path = "img//";
	private final int x_cardFrame = 20;
	private final int y_cardFrame = 35;
	private MainFrame frame;
	private GameEngine engine;
	private Color color;
	private Image imageSuit;
	private String imageValue;
	private LinkedList<PlayingCard> cards  = new LinkedList<PlayingCard>();
	private Map<String, LinkedList<PlayingCard>> playerCardsLists = new HashMap<String, LinkedList<PlayingCard>>();
	private DealHouseDialogBox dealHouseDialogBox;
	
	
	public CardPanel(MainFrame frame, GameEngine engine) 
	{
		this.frame = frame;
		this.engine = engine;
		this.dealHouseDialogBox = new DealHouseDialogBox(engine, frame);
		
		DropDownComboBoxController dropDownComboBoxController = new DropDownComboBoxController(engine, frame);
		frame.getToolbar().getDropDownPlayersMenu().addActionListener(dropDownComboBoxController);
		
		CardResizeController cardResizeController = new CardResizeController(engine, frame);
		frame.addComponentListener(cardResizeController);
	}
	
	public void updatePlayerCardsLists(String playerID, PlayingCard card) 
	{
		cards = new LinkedList<PlayingCard>();
		
		if(playerCardsLists.containsKey(playerID))
		{
			cards = playerCardsLists.get(playerID);
		}
		
		if(card != null)
		{
			cards.add(card);
			playerCardsLists.put(playerID, cards);
		}
		
		if(playerID.equals(frame.getToolbar().getSelectedPlayerID()))
		{
			repaint();
		}
	}
	
	public void cardResize(String playerID)
	{
		updatePlayerCardsLists(playerID, null);
	}
	
	public void callDealHouseIfAllPlayersHaveDealt()
	{
		if(frame.getSummarypanel().checkIfAllPlayersHaveDealt(engine, frame) == true)
		{
			if(!(frame.getCardpanel().getPlayerCardsLists().containsKey(" ")))
			{
				engine.addPlayer(new SimplePlayer(" ", "The House", 10));
				engine.placeBet(engine.getPlayer(" "), 10);
				frame.getToolbar().getDropDownPlayersMenu().addItem("The House");
			}
			frame.getToolbar().getDropDownPlayersMenu().setSelectedItem("The House");
			dealHouseDialogBox.automatedDealHouse();
		}
	}
	
	public int getHouseResult() 
	{
		int houseResult = 0;
		cards = playerCardsLists.get(" ");
		for(int i = 0; i < 4; i++)
		{
			houseResult += cards.get(i).getScore();
		}
		if(houseResult > 42)
		{
			houseResult = houseResult - cards.get(cards.size() - 1).getScore();
		}
		return houseResult;
	}

	public Map<String, LinkedList<PlayingCard>> getPlayerCardsLists() 
	{
		return playerCardsLists;
	}

	private Image cardSuitImage(String suit)
	{
		if(suit == "CLUBS")
		{
			imageSuit = Toolkit.getDefaultToolkit().getImage(path + "clubs.png");
		}
		else if(suit == "DIAMONDS")
		{
			imageSuit = Toolkit.getDefaultToolkit().getImage(path + "diamonds.png");
		}
		else if(suit == "SPADES")
		{
			imageSuit = Toolkit.getDefaultToolkit().getImage(path + "spades.png");
		}
		else if(suit == "HEARTS")
		{
			imageSuit = Toolkit.getDefaultToolkit().getImage(path + "hearts.png");
		}
		return imageSuit;
	}
	
	private Color cardColor(String suit)
	{
		if(suit == "CLUBS" || suit == "SPADES")
		{
			color = Color.BLACK;
		}
		else if(suit == "DIAMONDS" || suit == "HEARTS")
		{
			color = Color.RED;
		}
		return color;
	}
	
	private String cardValue(String value)
	{
		if(value == "EIGHT")
		{
			imageValue = "8";
		}
		else if(value == "NINE")
		{
			imageValue = "9";
		}
		else
		{
			imageValue = value.substring(0,1);
		}
		return imageValue;
	}	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int width_CardFrame = frame.getPanel().getWidth()/7; 
		int height_CardFrame = frame.getPanel().getHeight()/2 - 35; 
		int space = width_CardFrame + 20;
		int x_suitIcon = x_cardFrame + frame.getPanel().getWidth()/20 + 6; //76
		int y_suitIcon = y_cardFrame + frame.getPanel().getHeight()/5 - 7;
		int x_valueText1 = x_cardFrame + frame.getPanel().getWidth()/96 + 2; 
		int y_valueText1 =  y_cardFrame + frame.getPanel().getHeight()/14; 
		int x_valueText2 = x_cardFrame + frame.getPanel().getWidth()/10 + 12; 
		int y_valueText2 = y_cardFrame + frame.getPanel().getHeight()/2 - 43; 
		
		for(int i = 0; i < cards.size(); i++)
		{
			g.setColor(Color.WHITE);
			if(i == 4)
			{
				g.setColor(Color.LIGHT_GRAY);
			}
			g.fillRoundRect(x_cardFrame + space*i, y_cardFrame, width_CardFrame, height_CardFrame, 30, 30);
			g.drawImage(cardSuitImage(cards.get(i).getSuit().toString()), x_suitIcon + space*i, y_suitIcon,  width_CardFrame/5, height_CardFrame/6, this);	
			
			Font font = new Font("Playing Cards", Font.PLAIN, frame.getWidth()/30);
			g.setFont(font);
			g.setColor(cardColor(cards.get(i).getSuit().toString()));
			g.drawString(cardValue(cards.get(i).getValue().toString()), x_valueText1 + space*i, y_valueText1); 
			g.drawString(cardValue(cards.get(i).getValue().toString()), x_valueText2 + space*i, y_valueText2);
		}
	}
}
