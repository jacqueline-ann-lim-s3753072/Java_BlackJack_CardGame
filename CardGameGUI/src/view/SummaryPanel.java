package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import model.interfaces.GameEngine;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class SummaryPanel extends JPanel{
	
	private JTable table = new JTable();  
	private JScrollPane pane = new JScrollPane(table);
	private List<RowSorter.SortKey> sortKeys = new ArrayList<>();
    private DefaultTableModel tableModel = new DefaultTableModel();
    private DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
    private final String[] columnHeadings = {"ID", "Player Name", "Bet", "Points", "Result", "Win/Loss"};
    private HashMap<String, Player> playerMap;
    
    public SummaryPanel(MainFrame frame, GameEngine engine) 
    {
    	setLayout(new GridLayout(1,1));
		tableModel.setColumnIdentifiers(columnHeadings);
		table.setModel(tableModel);
		table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 16));
		table.setFont(new Font("SansSerif", Font.PLAIN, 16));
		table.setRowHeight(20);
		table.setEnabled(false);
		table.getTableHeader().setReorderingAllowed(false);

		//sorting the table by playerID
		TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
		table.setRowSorter(sorter);
		int columnIndexToSort = 0;
		sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
		sorter.setSortKeys(sortKeys);
		sorter.sort();
		
		//format the text values in the center of the cell
		renderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i=0; i < table.getColumnCount();i++)
		{
	         table.setDefaultRenderer(table.getColumnClass(i),renderer);
		}	
		table.updateUI();
		 
		//display the table
		pane.setPreferredSize(new Dimension(frame.getWidth() - 50, frame.getHeight()/3)); //resize
		add(pane);	
		
		if(!(engine.getAllPlayers().isEmpty()))
		{
			for(Player player: engine.getAllPlayers())
			{
				addRowToSummaryPannel(player);
				frame.getToolbar().getDropDownPlayersMenu().addItem(String.format("ID: %s | Name: %s", player.getPlayerId(), player.getPlayerName()));
			}
		}
	}
	
	public void addRowToSummaryPannel(Player player)
	{
		String id = player.getPlayerId();
		String name = player.getPlayerName();
		int bet = player.getBet();
		int points = player.getPoints();
		int result = player.getResult();
		
		Object[] data = {id, name, bet, points, result};
		tableModel.addRow(data);
	}

	public void deleteRowFromSummaryPannel(String playerID) 
	{
        for (int i = 0; i < tableModel.getRowCount(); i++) 
        {
            if (((String)tableModel.getValueAt(i, 0)).equals(playerID))
            {
                tableModel.removeRow(i);
            }
        }
	}
	
	public void setBetToSummaryPannel(String playerID, int bet)
	{
        for (int i = 0; i < tableModel.getRowCount(); i++) 
        {
            if (((String)tableModel.getValueAt(i, 0)).equals(playerID))
            {
            	int columnIndexOfBet = 2;
                tableModel.setValueAt(bet, i, columnIndexOfBet);
            }
        }
	}
	
	public void setResultToSummaryPannel(String playerID, int result)
	{
        for (int i = 0; i < tableModel.getRowCount(); i++) 
        {
            if (((String)tableModel.getValueAt(i, 0)).equals(playerID))
            {
            	int columnIndexOfResult = 4;
                tableModel.setValueAt(result, i, columnIndexOfResult);
            }
        }
	}

	public void updateFinalPointsToSummaryPannel(String playerID, int points, String winloss)
	{
        for (int i = 0; i < tableModel.getRowCount(); i++) 
        {
            if (((String)tableModel.getValueAt(i, 0)).equals(playerID))
            {
            	int columnIndexOfPoints = 3;
                tableModel.setValueAt(points, i, columnIndexOfPoints);
                
                int columnIndexOfWinLoss = 5;
                tableModel.setValueAt(winloss, i, columnIndexOfWinLoss);
            }
        }
	}
	
	public boolean checkIfAllPlayersHaveDealt(GameEngine engine, MainFrame frame)
	{
		for(Player player: engine.getAllPlayers())
		{
			if(!(player.getPlayerId().equals(" ")) && player.getResult() == 0)
			{
				return false;
			}
		}
		frame.getToolbar().setAllButtons(false);
		frame.getToolbar().getAddPlayerButton().setEnabled(true);
		return true;
	}
	
	public boolean checkIfHasPlayers(GameEngine engine, MainFrame frame)
	{
		for(Player player: engine.getAllPlayers())
		{
			if(!(player.getPlayerId().equals(" ")))
			{
				return true;
			}
		}
		frame.getToolbar().setAllButtons(false);
		frame.getToolbar().getAddPlayerButton().setEnabled(true);
		return false;
	}
	
	public void applyWinLossToSummaryPannel(int houseResult, GameEngine engine)
	{
		for(Player player: engine.getAllPlayers())
		{
			if(player.getResult() < houseResult) 
			{
				updateFinalPointsToSummaryPannel(player.getPlayerId(), player.getPoints(), "LOSS");
			}
			else if(player.getResult() > houseResult)
			{
				updateFinalPointsToSummaryPannel(player.getPlayerId(), player.getPoints(), "WIN");
			}
			else
			{
				updateFinalPointsToSummaryPannel(player.getPlayerId(), player.getPoints(), "DRAW");
			}
		}
	}

	public HashMap<String, Player> getPlayerMap(GameEngine engine) 
	{
		playerMap = new HashMap<String, Player>();
		for(Player player: engine.getAllPlayers())
		{
			playerMap.put(player.getPlayerId(), player);
		}
		return playerMap;
	}
	
	public JTable getTable() 
	{
		return table;
	}
}
