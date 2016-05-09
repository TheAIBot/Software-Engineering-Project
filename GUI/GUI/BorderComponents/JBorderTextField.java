package GUI.BorderComponents;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * Used to highlight a text field depending on current input
 * Emil
 */
public class JBorderTextField extends JTextField implements ColoredBorder {

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
