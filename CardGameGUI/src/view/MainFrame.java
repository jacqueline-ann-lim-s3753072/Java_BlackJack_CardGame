package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import model.interfaces.GameEngine;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{

	private PullDownMenu pulldownmenu;
	private ToolBar toolbar;
	private JPanel panel;
	private SummaryPanel summarypanel;
	private CardPanel cardpanel;
	private StatusBar statusbar;
		
	public MainFrame(GameEngine engine) 
	{
		super("CardGameGUI");
		setBounds(100, 100, 900, 580);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		pulldownmenu = new PullDownMenu(this);
		setJMenuBar(pulldownmenu);

		toolbar = new ToolBar(this, engine);
		panel = new JPanel(new BorderLayout());
		summarypanel = new SummaryPanel(this, engine);
		cardpanel = new CardPanel(this, engine);
		statusbar = new StatusBar();
		engine.addGameEngineCallback(new GameEngineCallbackImpl());
		engine.addGameEngineCallback(new GameEngineCallbackGUI(this));
		populate();
		setVisible(true);
	}

	public PullDownMenu getPulldownmenu() {
		return pulldownmenu;
	}

	public ToolBar getToolbar() {
		return toolbar;
	}

	public SummaryPanel getSummarypanel() {
		return summarypanel;
	}

	public CardPanel getCardpanel() {
		return cardpanel;
	}

	public JPanel getPanel() {
		return panel;
	}

	public StatusBar getStatusbar() {
		return statusbar;
	}

	private void populate()
	{
		add(toolbar, BorderLayout.NORTH);
		panel.setBounds(0, 0, 960, 540);
		panel.add(summarypanel, BorderLayout.NORTH);
		panel.add(cardpanel, BorderLayout.CENTER);
		add(panel, BorderLayout.CENTER);
		add(statusbar, BorderLayout.SOUTH);
	}
}
