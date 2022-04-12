package view.dialogbox;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class AddPlayerDialogBox extends JOptionPane{

	private int initialPoints;
	private final JTextField inputID = new JTextField();
	private final JTextField inputName = new JTextField();
	private final JTextField inputPoints = new JTextField();
	private Object[] inputFields = {"Enter your ID:", inputID, "Enter your name:", inputName, "Enter your initial points:", inputPoints};
	
	private void validateCondition(int option)
	{
		if(option == JOptionPane.CLOSED_OPTION)
		{
			//do nothing
		}
		else if(inputID.getText().trim().isEmpty() || inputName.getText().trim().isEmpty() || inputPoints.getText().trim().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Invalid Entry. You cannot leave a blank.");
		}
		else
		{
			try 
			{
				Integer.parseInt(inputID.getText().trim()); 
				
				int points = Integer.parseInt(inputPoints.getText().trim());
				checkValidPoints(points);
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(this, "Invalid Entry. Please enter a numeric value for both ID and points.");
			}
		}
	}
	
	private void checkValidPoints(int points)
	{
		if(points > 0)
		{
			this.initialPoints = points;
		}
		else
		{
			JOptionPane.showMessageDialog(this, "Invalid Points. Your points should be greater than 0.");
		}
	}
	public String getPlayerID()
	{
		initialPoints = 0;
		inputID.setText(null);
		inputName.setText(null);
		inputPoints.setText(null);
		int option = JOptionPane.showConfirmDialog(this, inputFields, "Enter all your details", JOptionPane.PLAIN_MESSAGE);
		
		validateCondition(option);
		
		return inputID.getText();
	}

	public String getPlayerName()
	{	
		return inputName.getText();
	}

	public int getInitialPoints() 
	{
		return initialPoints;
	}
}
