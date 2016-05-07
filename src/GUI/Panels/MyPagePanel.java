package GUI.Panels;

import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

public class MyPagePanel extends JPanel {
	private JScrollPane projectsScrollPane;
	private JScrollPane activitiesScrollPane;
	private JLabel initialsLabel;

	/**
	 * Create the panel.
	 */
	public MyPagePanel() {
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));
		setLayout(null);
		
		projectsScrollPane = new JScrollPane();
		projectsScrollPane.setBounds(152, 11, 270, 548);
		add(projectsScrollPane);
		
		activitiesScrollPane = new JScrollPane();
		activitiesScrollPane.setBounds(432, 11, 358, 548);
		add(activitiesScrollPane);
		
		JLabel lblInitials = new JLabel("Initials:");
		lblInitials.setBounds(10, 11, 46, 14);
		add(lblInitials);
		
		initialsLabel = new JLabel("New label");
		initialsLabel.setBounds(66, 11, 46, 14);
		add(initialsLabel);

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
}
