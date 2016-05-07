package GUI.Panels;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class AbsenseActivitiesPanel extends JPanel {
	private JScrollPane absenseActivitiesScrollPane;

	/**
	 * Create the panel.
	 */
	public AbsenseActivitiesPanel() {
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));
		setLayout(null);
		
		absenseActivitiesScrollPane = new JScrollPane();
		absenseActivitiesScrollPane.setBounds(396, 13, 392, 574);
		add(absenseActivitiesScrollPane);
	}

	/**
	 * @return the absenseActivitiesScrollPane
	 */
	public JScrollPane getAbsenseActivitiesScrollPane() {
		return absenseActivitiesScrollPane;
	}

}
