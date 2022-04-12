package view;
import javax.swing.SwingUtilities;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

public class GameEngineCallbackGUI implements GameEngineCallback{
   
   private MainFrame frame;
	
   public GameEngineCallbackGUI(MainFrame frame) 
   {
		this.frame = frame;
   }

   @Override
   public void nextCard(Player player, PlayingCard card, GameEngine engine)
   {
	   SwingUtilities.invokeLater(new Runnable()
	   {
		   @Override
		   public void run()
		   {   
			   frame.getCardpanel().updatePlayerCardsLists(player.getPlayerId(), card);
		   }
	   });
   }

   @Override
   public void result(Player player, int result, GameEngine engine)
   {
	   SwingUtilities.invokeLater(new Runnable()
	   {
		   @Override
		   public void run()
		   {
			   frame.getSummarypanel().setResultToSummaryPannel(player.getPlayerId(), result);
		   }
	   });
   }

   @Override
   public void bustCard(Player player, PlayingCard card, GameEngine engine) 
   {
	   SwingUtilities.invokeLater(new Runnable()
	   {
		   @Override
		   public void run()
		   {
			   frame.getCardpanel().updatePlayerCardsLists(player.getPlayerId(), card);
		   }
	   });
   }

   @Override
   public void nextHouseCard(PlayingCard card, GameEngine engine) 
   {
	   SwingUtilities.invokeLater(new Runnable()
	   {
		   @Override
		   public void run()
		   {
			   frame.getCardpanel().updatePlayerCardsLists(" ", card);
		   }
	   });
   }

   @Override
   public void houseBustCard(PlayingCard card, GameEngine engine) 
   {
	   SwingUtilities.invokeLater(new Runnable()
	   {
		   @Override
		   public void run()
		   {
			   frame.getCardpanel().updatePlayerCardsLists(" ", card);
		   }
	   });
   }

   @Override
   public void houseResult(int result, GameEngine engine) 
   {
	   SwingUtilities.invokeLater(new Runnable()
	   {
		   @Override
		   public void run()
		   {
			   	frame.getSummarypanel().applyWinLossToSummaryPannel(result, engine);			   	
		   }
	   });
   }
}
