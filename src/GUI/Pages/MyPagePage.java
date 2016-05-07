package GUI.Pages;

import GUI.GUIController;
import GUI.Tools;
import GUI.Panels.MyPagePanel;
import SoftwareHouse.Employee;
import SoftwareHouse.Scheduler;

public class MyPagePage extends SuperPage<MyPagePanel> {

	public MyPagePage(GUIController controller, Scheduler scheduler) {
		super(controller, scheduler);
	}

	@Override
	public MyPagePanel createPage(GUIController controlle) {
		return new MyPagePanel();
	}

	@Override
	public void loadInformation() {
		Employee employee = scheduler.getLoggedInEmployee();
		
		page.getInitialsLabel().setText(employee.getInitials());
		page.getProjectsScrollPane().setViewportView(Tools.createTableOfProjects(employee.getProjects(), controller, scheduler));
		page.getActivitiesScrollPane().setViewportView(Tools.createTableOfActivities(employee.getActivities()));
	}
}
