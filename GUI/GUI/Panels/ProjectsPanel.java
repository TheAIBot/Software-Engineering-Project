package GUI.Panels;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ProjectsPanel extends JPanel {
	private JScrollPane openProjectsScrollBar;
	private JButton createProjectButton;
	private JScrollPane closedProjectsScrollPane;
	
	/**
	 * Create the panel.
	 */
	public ProjectsPanel() {
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));
		setLayout(null);
		
		openProjectsScrollBar = new JScrollPane();
		openProjectsScrollBar.setBounds(10, 36, 387, 519);
		add(openProjectsScrollBar);
		
		createProjectButton = new JButton("Opret projekt");
		createProjectButton.setBounds(648, 566, 142, 23);
		add(createProjectButton);	
		
		closedProjectsScrollPane = new JScrollPane();
		closedProjectsScrollPane.setBounds(407, 36, 383, 519);
		add(closedProjectsScrollPane);
		
		JLabel lblNewLabel = new JLabel("\u00C5bne projekter");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 387, 14);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Lukkede projekter");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(407, 11, 383, 14);
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
