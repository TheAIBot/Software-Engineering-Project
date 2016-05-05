package GUI.Panels;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;

public class ProjectPanel extends JPanel {
	private JScrollPane ProjectsScrollBar;
	private JButton createProjectButton;
	
	/**
	 * Create the panel.
	 */
	public ProjectPanel() {
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));
		setLayout(null);
		
		ProjectsScrollBar = new JScrollPane();
		ProjectsScrollBar.setBounds(596, 11, 194, 544);
		add(ProjectsScrollBar);
		
		createProjectButton = new JButton("Opret projekt");
		createProjectButton.setBounds(596, 566, 194, 23);
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
