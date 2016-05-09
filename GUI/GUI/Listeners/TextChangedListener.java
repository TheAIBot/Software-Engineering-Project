package GUI.Listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author Niklas
 */
public interface TextChangedListener extends DocumentListener {

	@Override
	public default void insertUpdate(DocumentEvent e) {
		textChanged();
	}

	@Override
	public default void removeUpdate(DocumentEvent e) {
		textChanged();
	}

	@Override
	public default void changedUpdate(DocumentEvent e) {
		textChanged();
	}
	
	public abstract void textChanged();

}
