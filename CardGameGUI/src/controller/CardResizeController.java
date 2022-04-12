package controller;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import model.interfaces.GameEngine;
import view.MainFrame;

public class CardResizeController extends ComponentAdapter implements ComponentListener{
	
	private GameEngine engine;
	private MainFrame frame;
	
	public CardResizeController(GameEngine engine, MainFrame frame) {
		this.engine = engine;
		this.frame = frame;
	}

	@Override
	public void componentResized(ComponentEvent e) 
	{
		if(!(engine.getAllPlayers().isEmpty()))
		{
			String playerID = frame.getToolbar().getSelectedPlayerID();
			frame.getCardpanel().cardResize(playerID);
		}
	}
}
