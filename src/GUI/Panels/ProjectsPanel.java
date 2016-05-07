package GUI.Panels;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;

public class ProjectsPanel extends JPanel {
	private JScrollPane ProjectsScrollBar;
	private JButton createProjectButton;
	
	/**
	 * Create the panel.
	 */
	public ProjectsPanel() {
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));
		setLayout(null);
		
		ProjectsScrollBar = new JScrollPane();
		ProjectsScrollBar.setBounds(332, 11, 458, 544);
		add(ProjectsScrollBar);
		
		createProjectButton = new JButton("Opret projekt");
		createProjectButton.setBounds(648, 566, 142, 23);
		add(createProjectButton);	
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
}
