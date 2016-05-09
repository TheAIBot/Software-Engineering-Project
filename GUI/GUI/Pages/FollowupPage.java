package GUI.Pages;

import GUI.GUIController;
import GUI.Tools;
import GUI.Panels.FollowupPanel;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;

public class FollowupPage extends SuperPage<FollowupPanel> {

	private final Project project;
	
	public FollowupPage(GUIController controller, Scheduler scheduler, Project project) {
		super(controller, scheduler);
		this.project = project;
	}

	@Override
	public FollowupPanel createPage(GUIController controlle) {
		return new FollowupPanel();
	}

	@Override
	public void loadInformation() {
		page.getOpenActivitiesScrollPane().setViewportView(Tools.createTableOfActivities(project.getOpenActivities(), controller, scheduler));
		page.getClosedActivitiesScrollPane().setViewportView(Tools.createTableOfActivities(project.getClosedActivities(), controller, scheduler));
		try {
			page.getRegisteredTimeScrollPane().setViewportView(Tools.createTableOfRegisteredTimes(scheduler.getTimeVault().getProjectTime(project.getName())));
		} catch (Exception e) {	}
	}

}
