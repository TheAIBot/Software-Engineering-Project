package GUI.Panels;

import javax.swing.JPanel;
import java.awt.Dimension;
<<<<<<< HEAD

public class MyPagePanel extends JPanel {
=======
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;

public class MyPagePanel extends JPanel {
	private JScrollPane projectsScrollPane;
	private JScrollPane activitiesScrollPane;
	private JLabel initialsLabel;
	private JButton absenseActivitiesButton;
>>>>>>> refs/remotes/origin/Andreas

	/**
	 * Create the panel.
	 */
	public MyPagePanel() {
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));
<<<<<<< HEAD

	}

=======
		setLayout(null);
		
		projectsScrollPane = new JScrollPane();
		projectsScrollPane.setBounds(165, 11, 270, 576);
		add(projectsScrollPane);
		
		activitiesScrollPane = new JScrollPane();
		activitiesScrollPane.setBounds(447, 11, 343, 576);
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
>>>>>>> refs/remotes/origin/Andreas
}
