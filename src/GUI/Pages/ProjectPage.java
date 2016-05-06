package GUI.Pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JTable;

import GUI.GUIController;
import GUI.DialogBoxes.CreateProjectDialog;
import GUI.DialogBoxes.CreateUserDialog;
import GUI.Panels.ProjectPanel;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;

public class ProjectPage extends SuperPage<ProjectPanel> {

	public ProjectPage(GUIController controller, Scheduler scheduler) {
		super(controller, scheduler);
	}

	@Override
	public ProjectPanel createPage(GUIController controlle) {
		ProjectPanel projectPanel = new ProjectPanel();
		
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
		final String[] columnNames = {"Projeker"};
		List<Project> projects = null;
		try {
			projects = scheduler.getProjects();
			final Object[][] employeesAsATable = new Object[projects.size()][1];
			for(int i = projects.size() - 1; i >= 0 ; i--)
			{
				employeesAsATable[employeesAsATable.length - 1 - i][0] = projects.get(i).getName();
			}
			page.getProjectsScrollBar().setViewportView(new JTable(employeesAsATable, columnNames));
		} catch (Exception e) { }
		
	}

}
