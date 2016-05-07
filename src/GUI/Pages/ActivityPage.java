package GUI.Pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import GUI.GUIController;
import GUI.Tools;
import GUI.DialogBoxes.AddEmployeesToActivityDialog;
import GUI.DialogBoxes.ChangeActivityDialog;
import GUI.DialogBoxes.CreateActivityDialog;
import GUI.DialogBoxes.RegisterTimeDialog;
import GUI.Listeners.WindowClosingListener;
import GUI.Panels.ActivityPanel;
import GUI.Panels.ProjectPanel;
import SoftwareHouse.Activity;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;

public class ActivityPage extends SuperPage<ActivityPanel> {

	private final Activity activity;
	
	public ActivityPage(GUIController controller, Scheduler scheduler, Activity activity) {
		super(controller, scheduler);
		this.activity = activity;
	}

	@Override
	public ActivityPanel createPage(GUIController controlle) {
		ActivityPanel activityPanel = new ActivityPanel();
		
		if (activity.getInProject().isProjectManagerLoggedIn()) {
			activityPanel.getChangeActivityButton().setEnabled(true);
			activityPanel.getChangeActivityButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					ChangeActivityDialog dialog = new ChangeActivityDialog(scheduler, activity);
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
			
			activityPanel.getAddEmployeesButton().setEnabled(true);
			activityPanel.getAddEmployeesButton().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					AddEmployeesToActivityDialog dialog = new AddEmployeesToActivityDialog(scheduler, activity);
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
			
			
		}
		activityPanel.getRegisterTimeButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterTimeDialog dialog = new RegisterTimeDialog(scheduler, activity.getInProject(), activity);
				dialog.addWindowListener(new WindowClosingListener() {
					@Override
					public void windowClosing(WindowEvent e) {
						loadInformation();							
					}
				});
				dialog.setVisible(true);
			}
		});
		return activityPanel;
	}

	@Override
	public void loadInformation() {
		page.getEmployeesScrollPane().setViewportView(Tools.createTableOfEmployees(activity.getAssignedEmployees()));
		page.getActivityNameLabel().setText(activity.getName());
		page.getBudgettedTimeLabel().setText(String.valueOf(activity.getBudgettedTime()));
		page.getInProjectLabel().setText(activity.getInProject().getName());
	}	
}
