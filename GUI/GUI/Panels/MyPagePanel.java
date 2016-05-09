package GUI.Panels;

import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import GUI.GUIController;

import javax.swing.JLabel;
import javax.swing.JButton;

/**
 * @author Niklas
 */
public class MyPagePanel extends JPanel {
	private JScrollPane projectsScrollPane;
	private JScrollPane activitiesScrollPane;
	private JLabel initialsLabel;
	private JButton absenseActivitiesButton;
	private JScrollPane registeredTimeScrollPane;

	/**
	 * Create the panel.
	 */
	public MyPagePanel() {
		setMaximumSize(GUIController.DEFAULT_PANEL_SIZE);
		setMinimumSize(GUIController.DEFAULT_PANEL_SIZE);
		setPreferredSize(GUIController.DEFAULT_PANEL_SIZE);
		setLayout(null);
		
		projectsScrollPane = new JScrollPane();
		projectsScrollPane.setBounds(563, 11, 270, 576);
		add(projectsScrollPane);
		
		activitiesScrollPane = new JScrollPane();
		activitiesScrollPane.setBounds(845, 11, 343, 576);
		add(activitiesScrollPane);
		
		JLabel lblInitials = new JLabel("Initials:");
		lblInitials.setBounds(10, 11, 46, 14);
		add(lblInitials);
		
		initialsLabel = new JLabel("");
		initialsLabel.setBounds(66, 11, 74, 14);
		add(initialsLabel);
		
		absenseActivitiesButton = new JButton("Frav\u00E6rsaktiviteter");
		absenseActivitiesButton.setBounds(12, 536, 141, 51);
		add(absenseActivitiesButton);
		
		registeredTimeScrollPane = new JScrollPane();
		registeredTimeScrollPane.setBounds(299, 13, 250, 574);
		add(registeredTimeScrollPane);

	}

	/**
	 * @return the projectsScrollPane
	 */
	public JScrollPane getProjectsScrollPane() {
		return projectsScrollPane;
	}

	/**
	 * @return the activitiesScrollPane
	 */
	public JScrollPane getActivitiesScrollPane() {
		return activitiesScrollPane;
	}

	/**
	 * @return the initialsLabel
	 */
	public JLabel getInitialsLabel() {
		return initialsLabel;
	}

	/**
	 * @return the absenseActivitiesButton
	 */
	public JButton getAbsenseActivitiesButton() {
		return absenseActivitiesButton;
	}

	/**
	 * @return the registeredTimeScrollPane
	 */
	public JScrollPane getRegisteredTimeScrollPane() {
		return registeredTimeScrollPane;
	}
}
