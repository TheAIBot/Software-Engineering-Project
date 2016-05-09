package GUI.Pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import GUI.GUIController;
import GUI.Tools;
import GUI.DialogBoxes.AddEmployeesToProjectDialog;
import GUI.DialogBoxes.ChangeActivityDialog;
import GUI.DialogBoxes.ChangeProjectDialog;
import GUI.DialogBoxes.CreateActivityDialog;
import GUI.DialogBoxes.CreateProjectDialog;
import GUI.Listeners.WindowClosingListener;
import GUI.Panels.ProjectPanel;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.ProjectAlreadyClosedException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotLoggedInException;

/**
 * @author ELL
 */
public class ProjectPage extends SuperPage<ProjectPanel> {

	private final Project project;
	
	public ProjectPage(GUIController controller, Scheduler scheduler, Project project) {
		super(controller, scheduler);
		this.project = project;
	}

	@Override
	public ProjectPanel createPage(GUIController controlle) {
		ProjectPanel projectPanel = new ProjectPanel();
		
		try {
			if (project.hasPermissionToEdit()) {
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
				
				projectPanel.getGenerateReportButton().setEnabled(true);
				projectPanel.getGenerateReportButton().addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						project.generateReport();
					}
				});
				
				if (project.isOpen()) {
					projectPanel.getCloseProjectButton().setEnabled(true);
					projectPanel.getCloseProjectButton().addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								project.close();
								loadInformation();
							} catch (ProjectAlreadyClosedException e1) { }
						}
					});
				}
			}
		} catch (ProjectManagerNotLoggedInException e) {}
		return projectPanel;
	}

	@Override
	public void loadInformation() {
		page.getActivitiesScrollPane().setViewportView(Tools.createTableOfActivities(project.getOpenActivities(), controller, scheduler));
		
		page.getProjectNameLabel().setText(project.getName());
		page.getProjectManagerInitialsLabel().setText(project.getProjectManager().getInitials());
		page.getCostumerNameLabel().setText(project.getCostumerName());
		page.getBudgettedTimeLabel().setText(String.valueOf(project.getBudgetedTime()));
		page.getStatusLabel().setText((project.isOpen()) ? "Åben" : "Lukket");
	}

}
