package GUI.Components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;;

public class JTextBox extends JPanel {
	
	private final JTextField textField = new JTextField();
	
	public JTextBox()
	{
		setLayout(new BorderLayout());
		add(textField, BorderLayout.CENTER);
		makeBorderRed();
	}
	
	public void makeBorderRed()
	{
		setBorder(BorderFactory.createLineBorder(Color.RED, 3));
	}
	
	public void makeBorderGreen()
	{
		setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));
	}
	
	public String getText()
	{
		return textField.getText();
	}
	
	public void setText(String text)
	{
		textField.setText(text);
	}

	
	public void setColumns(int i) {
		textField.setColumns(i);
	}
	
	public void addActionListener(ActionListener actionListener)
	{
		textField.addActionListener(actionListener);
	}
}
