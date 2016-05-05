package GUI.Components;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class JTextBox extends JTextField {

	private final Border defaultBorder = getBorder();
	
	public void makeBorderRed()
	{
		setBorder(BorderFactory.createLineBorder(Color.RED, 2));
	}
	
	public void makeBorderGreen()
	{
		setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
	}

	public void makeBorderDefaultColor()
	{
		setBorder(defaultBorder);
	}
}
