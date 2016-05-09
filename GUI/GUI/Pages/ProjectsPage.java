package GUI.Pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JTable;

import GUI.GUIController;
import GUI.Tools;
import GUI.DialogBoxes.CreateProjectDialog;
import GUI.Listeners.WindowClosingListener;
import GUI.Panels.ProjectsPanel;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;

public class ProjectsPage extends SuperPage<ProjectsPanel> {

	public ProjectsPage(GUIController controller, Scheduler scheduler) {
		super(controller, scheduler);
	}

	@Override
	public ProjectsPanel createPage(GUIController controlle) {
		ProjectsPanel projectPanel = new ProjectsPanel();
		
		projectPanel.getCreateProjectButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateProjectDialog dialog = new CreateProjectDialog(scheduler);
				dialog.addWindowListener(new WindowClosingListener() {
					@Override
					public void windowClosing(WindowEvent e) {
						loadInformation();							
					}
				});
				dialog.setVisible(true);
				dialog.loadInformation();
			}
		});
		
		return projectPanel;
	}

	@Override
	public void loadInformation() {
		try {
			List<Project> openProjects = scheduler.getProjects().stream()
																.filter(x -> x.isOpen())
																.collect(Collectors.toList());
			List<Project> closedProjects = scheduler.getProjects().stream()
																  .filter(x -> !x.isOpen())
																  .collect(Collectors.toList());
			page.getProjectsScrollBar().setViewportView(Tools.createTableOfProjects(openProjects, controller, scheduler));
			page.getClosedProjectsScrollPane().setViewportView(Tools.createTableOfProjects(closedProjects, controller, scheduler));
		} catch (Exception e) { }
		
	}

}
