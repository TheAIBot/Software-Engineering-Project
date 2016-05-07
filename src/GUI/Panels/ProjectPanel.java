package GUI.Panels;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;

public class ProjectPanel extends JPanel {
	private JScrollPane activitiesScrollPane;
	private JLabel projectNameLabel;
	private JLabel costumerNameLabel;
	private JLabel projectManagerInitialsLabel;
	private JLabel budgettedTimeLabel;
	private JButton createActivityButton;
	private JButton changeProjectButton;
	private JButton addEmployeesButton;

	public ProjectPanel() {
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));
		setLayout(null);
		
		activitiesScrollPane = new JScrollPane();
		activitiesScrollPane.setBounds(439, 11, 351, 578);
		add(activitiesScrollPane);
		
		JLabel lblProjektNavn = new JLabel("Projekt navn:");
		lblProjektNavn.setBounds(10, 11, 95, 14);
		add(lblProjektNavn);
		
		projectNameLabel = new JLabel("");
		projectNameLabel.setBounds(125, 11, 151, 14);
		add(projectNameLabel);
		
		JLabel lblNewLabel = new JLabel("K\u00F8ber:");
		lblNewLabel.setBounds(10, 36, 95, 14);
		add(lblNewLabel);
		
		costumerNameLabel = new JLabel("");
		costumerNameLabel.setBounds(125, 36, 151, 14);
		add(costumerNameLabel);
		
		JLabel lblProjektManager = new JLabel("Projekt manager:");
		lblProjektManager.setBounds(10, 61, 105, 14);
		add(lblProjektManager);
		
		JLabel lblBudgetteretTid = new JLabel("Budgetteret tid:");
		lblBudgetteretTid.setBounds(10, 86, 95, 14);
		add(lblBudgetteretTid);
		
		projectManagerInitialsLabel = new JLabel("");
		projectManagerInitialsLabel.setBounds(125, 61, 151, 14);
		add(projectManagerInitialsLabel);
		
		budgettedTimeLabel = new JLabel("");
		budgettedTimeLabel.setBounds(125, 86, 151, 14);
		add(budgettedTimeLabel);
		
		createActivityButton = new JButton("Opret aktivitet");
		createActivityButton.setEnabled(false);
		createActivityButton.setBounds(309, 537, 120, 52);
		add(createActivityButton);
		
		changeProjectButton = new JButton("\u00C6ndr projekt");
		changeProjectButton.setEnabled(false);
		changeProjectButton.setBounds(176, 537, 123, 52);
		add(changeProjectButton);
		
		addEmployeesButton = new JButton("Tilf\u00F8j medarbejdere");
		addEmployeesButton.setEnabled(false);
		addEmployeesButton.setBounds(10, 537, 156, 52);
		add(addEmployeesButton);
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
	}

	/**
	 * @return the changeProjectButton
	 */
	public JButton getChangeProjectButton() {
		return changeProjectButton;
	}

	/**
	 * @return the addEmployeesButton
	 */
	public JButton getAddEmployeesButton() {
		return addEmployeesButton;
	}
}
