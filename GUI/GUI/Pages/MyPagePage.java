package GUI.Pages;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		MyPagePanel myPagePanel = new MyPagePanel();
		
		myPagePanel.getAbsenseActivitiesButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlle.switchPage(new AbsenseActivitiesPage(controlle, scheduler));
			}
		});
		
		return myPagePanel;
	}

	@Override
	public void loadInformation() {
		Employee employee = scheduler.getLoggedInEmployee();
		
		page.getInitialsLabel().setText(employee.getInitials());
		page.getProjectsScrollPane().setViewportView(Tools.createTableOfProjects(employee.getProjects(), controller, scheduler));
		page.getActivitiesScrollPane().setViewportView(Tools.createTableOfActivities(employee.getActivities(), controller, scheduler));
		try {
			page.getRegisteredTimeScrollPane().setViewportView(Tools.createTableOfRegisteredTimes(scheduler.getTimeVault().getEmployeeTime(employee.getInitials())));
		} catch (Exception e) {	}
	}
}
