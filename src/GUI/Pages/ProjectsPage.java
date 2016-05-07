package GUI.Pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JTable;

import GUI.GUIController;
import GUI.Tools;
import GUI.DialogBoxes.CreateProjectDialog;
import GUI.DialogBoxes.CreateUserDialog;
import GUI.Panels.ProjectsPanel;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;

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
				dialog.addWindowListener(new WindowListener() {
					@Override
					public void windowOpened(java.awt.event.WindowEvent e) {}
					@Override
					public void windowIconified(java.awt.event.WindowEvent e) {}
					@Override
					public void windowDeiconified(java.awt.event.WindowEvent e) {}
					@Override
					public void windowDeactivated(java.awt.event.WindowEvent e) {}
					@Override
					public void windowClosing(java.awt.event.WindowEvent e) {
						loadInformation();
					}
					@Override
					public void windowClosed(java.awt.event.WindowEvent e) {}
					@Override
					public void windowActivated(java.awt.event.WindowEvent e) {}
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
			page.getProjectsScrollBar().setViewportView(Tools.createTableOfProjects(scheduler.getProjects(), controller, scheduler));
		} catch (Exception e) { }
		
	}

}
