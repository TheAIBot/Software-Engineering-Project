package GUI.Pages;

import GUI.GUIController;
import GUI.Tools;
import GUI.Panels.AbsenseActivitiesPanel;
import SoftwareHouse.Scheduler;

/**
 * @author Jesper
 */
public class AbsenseActivitiesPage extends SuperPage<AbsenseActivitiesPanel> {

	public AbsenseActivitiesPage(GUIController controller, Scheduler scheduler) {
		super(controller, scheduler);
	}

	@Override
	public AbsenseActivitiesPanel createPage(GUIController controlle) {
		return new AbsenseActivitiesPanel();
	}

	@Override
	public void loadInformation() {
		page.getAbsenseActivitiesScrollPane().setViewportView(Tools.createTableOfActivities(scheduler.getAbsenceProject().getOpenActivities(), controller, scheduler));
	}
}
