package GUI.Panels;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;

public class ProjectPanel extends JPanel {
<<<<<<< HEAD
	private JScrollPane ProjectsScrollBar;
	private JButton createProjectButton;
	
	/**
	 * Create the panel.
	 */
=======
	private JScrollPane activitiesScrollPane;
	private JLabel projectNameLabel;
	private JLabel costumerNameLabel;
	private JLabel projectManagerInitialsLabel;
	private JLabel budgettedTimeLabel;
	private JButton followupButton;
	private JButton changeProjectButton;
	private JButton addEmployeesButton;
	private JButton createActivityButton;

>>>>>>> refs/remotes/origin/Andreas
	public ProjectPanel() {
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));
		setLayout(null);
		
		ProjectsScrollBar = new JScrollPane();
		ProjectsScrollBar.setBounds(596, 11, 194, 544);
		add(ProjectsScrollBar);
		
<<<<<<< HEAD
		createProjectButton = new JButton("Opret projekt");
		createProjectButton.setBounds(596, 566, 194, 23);
		add(createProjectButton);	
=======
		budgettedTimeLabel = new JLabel("");
		budgettedTimeLabel.setBounds(125, 86, 151, 14);
		add(budgettedTimeLabel);
		
		followupButton = new JButton("Opf\u00F8lgning for projekt");
		followupButton.setEnabled(false);
		followupButton.setBounds(176, 472, 253, 52);
		add(followupButton);
		
		changeProjectButton = new JButton("\u00C6ndr projekt");
		changeProjectButton.setEnabled(false);
		changeProjectButton.setBounds(176, 537, 123, 52);
		add(changeProjectButton);
		
		addEmployeesButton = new JButton("Tilf\u00F8j medarbejdere");
		addEmployeesButton.setEnabled(false);
		addEmployeesButton.setBounds(10, 537, 156, 52);
		add(addEmployeesButton);
		
		createActivityButton = new JButton("Opret aktivitet");
		createActivityButton.setEnabled(false);
		createActivityButton.setBounds(309, 537, 120, 52);
		add(createActivityButton);
	}

	/**
	 * @return the activitiesScrollPane
	 */
	public JScrollPane getActivitiesScrollPane() {
		return activitiesScrollPane;
	}

	/**
	 * @return the projectNameLabel
	 */
	public JLabel getProjectNameLabel() {
		return projectNameLabel;
	}

	/**
	 * @return the costumerNameLabel
	 */
	public JLabel getCostumerNameLabel() {
		return costumerNameLabel;
	}

	/**
	 * @return the projectManagerInitialsLabel
	 */
	public JLabel getProjectManagerInitialsLabel() {
		return projectManagerInitialsLabel;
	}

	/**
	 * @return the budgettedTimeLabel
	 */
	public JLabel getBudgettedTimeLabel() {
		return budgettedTimeLabel;
	}

	/**
	 * @return the createActivityButton
	 */
	public JButton getCreateActivityButton() {
		return createActivityButton;
>>>>>>> refs/remotes/origin/Andreas
	}

	/**
	 * @return the projectsScrollBar
	 */
	public JScrollPane getProjectsScrollBar() {
		return ProjectsScrollBar;
	}

	/**
	 * @return the createProjectButton
	 */
	public JButton getCreateProjectButton() {
		return createProjectButton;
	}

	/**
	 * @return the followupButton
	 */
	public JButton getFollowupButton() {
		return followupButton;
	}
}
