package GUI.Panels;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import GUI.GUIController;

public class ProjectsPanel extends JPanel {
	private JScrollPane openProjectsScrollBar;
	private JButton createProjectButton;
	private JScrollPane closedProjectsScrollPane;
	
	/**
	 * Create the panel.
	 */
	public ProjectsPanel() {
		setMaximumSize(GUIController.DEFAULT_PANEL_SIZE);
		setMinimumSize(GUIController.DEFAULT_PANEL_SIZE);
		setPreferredSize(GUIController.DEFAULT_PANEL_SIZE);
		setLayout(null);
		
		openProjectsScrollBar = new JScrollPane();
		openProjectsScrollBar.setBounds(408, 38, 387, 519);
		add(openProjectsScrollBar);
		
		createProjectButton = new JButton("Opret projekt");
		createProjectButton.setBounds(648, 566, 142, 23);
		add(createProjectButton);	
		
		closedProjectsScrollPane = new JScrollPane();
		closedProjectsScrollPane.setBounds(805, 38, 383, 519);
		add(closedProjectsScrollPane);
		
		JLabel lblNewLabel = new JLabel("\u00C5bne projekter");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(408, 13, 387, 14);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Lukkede projekter");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(805, 13, 383, 14);
		add(lblNewLabel_1);
	}

	/**
	 * @return the projectsScrollBar
	 */
	public JScrollPane getProjectsScrollBar() {
		return openProjectsScrollBar;
	}

	/**
	 * @return the createProjectButton
	 */
	public JButton getCreateProjectButton() {
		return createProjectButton;
	}

	/**
	 * @return the closedProjectsScrollPane
	 */
	public JScrollPane getClosedProjectsScrollPane() {
		return closedProjectsScrollPane;
	}
}
