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
		
		if (project.isProjectManagerLoggedIn()) {
			projectPanel.getCreateActivityButton().setEnabled(true);
			projectPanel.getCreateActivityButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CreateActivityDialog dialog = new CreateActivityDialog(scheduler, project);
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
			
			projectPanel.getChangeProjectButton().setEnabled(true);
			projectPanel.getChangeProjectButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ChangeProjectDialog dialog = new ChangeProjectDialog(scheduler, project);
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
			
			projectPanel.getAddEmployeesButton().setEnabled(true);
			projectPanel.getAddEmployeesButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AddEmployeesToProjectDialog dialog = new AddEmployeesToProjectDialog(scheduler, project);
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
			
			projectPanel.getFollowupButton().setEnabled(true);
			projectPanel.getFollowupButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controlle.switchPage(new FollowupPage(controlle, scheduler, project));
				}
			});
		}
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
