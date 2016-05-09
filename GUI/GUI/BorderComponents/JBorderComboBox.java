package GUI.BorderComponents;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.border.Border;

/**
 * Used to highlight a text combo box depending on current input
 * Jesper
 */
public class JBorderComboBox extends JComboBox<String> implements ColoredBorder {

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

	@Override
	public String getText() {
		String text = (String)getSelectedItem();
		if (text == null) {
			text = "";
		}
		return text;
	}	
	
	
}
