package app;

import javax.swing.SwingUtilities;

import model.GameEngineImpl;
import model.interfaces.GameEngine;
import validate.Validator;
import view.MainFrame;

public class MVCTest {
	
	public static void main(String[] args) 
	{
		  final GameEngine engine = new GameEngineImpl();
		
	      SwingUtilities.invokeLater(new Runnable()
	      {
	         @Override
	         public void run()
	         {
	        		new MainFrame(engine);
	         }
	      });
	      
	      Validator.validate(false);
	}
}
