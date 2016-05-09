package GUI.Panels;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

<<<<<<< HEAD
/**
 * @author Andreas
 */
=======
import GUI.GUIController;

>>>>>>> refs/remotes/origin/Andreas
public class AbsenseActivitiesPanel extends JPanel {
	private JScrollPane absenseActivitiesScrollPane;

	/**
	 * Create the panel.
	 */
	public AbsenseActivitiesPanel() {
		setMaximumSize(GUIController.DEFAULT_PANEL_SIZE);
		setMinimumSize(GUIController.DEFAULT_PANEL_SIZE);
		setPreferredSize(GUIController.DEFAULT_PANEL_SIZE);
		setLayout(null);
		
		absenseActivitiesScrollPane = new JScrollPane();
		absenseActivitiesScrollPane.setBounds(796, 13, 392, 574);
		add(absenseActivitiesScrollPane);
	}

	/**
	 * @return the absenseActivitiesScrollPane
	 */
	public JScrollPane getAbsenseActivitiesScrollPane() {
		return absenseActivitiesScrollPane;
	}

}
