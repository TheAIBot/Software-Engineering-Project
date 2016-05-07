package GUI.Pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import GUI.GUIController;
import GUI.Tools;
import GUI.DialogBoxes.CreateActivityDialog;
import GUI.DialogBoxes.CreateProjectDialog;
import GUI.Panels.ProjectPanel;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;

public class ProjectPage extends SuperPage<ProjectPanel> {

	private final Project project;
	
	public ProjectPage(GUIController controller, Scheduler scheduler, Project project) {
		super(controller, scheduler);
		this.project = project;
	}

	@Override
	public ProjectPanel createPage(GUIController controlle) {
		ProjectPanel projectPanel = new ProjectPanel();
		
		if (project.isProjectManagerLoggedIn()) {
			projectPanel.getCreateActivityButton().setEnabled(true);
			projectPanel.getCreateActivityButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					CreateActivityDialog dialog = new CreateActivityDialog(scheduler, project);
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
		}
		return projectPanel;
	}

	@Override
	public void loadInformation() {
		page.getActivitiesScrollPane().setViewportView(Tools.createTableOfActivities(project.getOpenActivities()));
		
		page.getProjectNameLabel().setText(project.getName());
		page.getProjectManagerInitialsLabel().setText(project.getCostumerName());
		page.getBudgettedTimeLabel().setText(String.valueOf(project.budgettedTime));
	}

}
