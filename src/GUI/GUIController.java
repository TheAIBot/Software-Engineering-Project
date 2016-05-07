package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GUI.Pages.LoginPage;
import GUI.Pages.MainPage;
import GUI.Pages.MyPagePage;
import GUI.Pages.ProjectsPage;
import GUI.Pages.SuperPage;
import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import junit.framework.Test;

public class GUIController {
	private JFrame frame;
	private final Scheduler scheduler = new Scheduler();
	private final MainPage mainPage = new MainPage(this, scheduler);
	public final LoginPage loginPage = new LoginPage(this, scheduler);
	public final ProjectsPage projectPage = new ProjectsPage(this, scheduler);
	public final MyPagePage myPagePage = new MyPagePage(this, scheduler);
	
 	public GUIController()
	{
		frame = new JFrame("herp a derp");
		frame.add(mainPage.getPage(this));
		frame.setSize(1010, 640); 
	}
	
	public void show()
	{
		try {
			Tests.TestTools.addEmployee(scheduler, "AGC");
			Tests.TestTools.addEmployee(scheduler, "JSB");
			Tests.TestTools.addEmployee(scheduler, "NR");
			Tests.TestTools.addEmployee(scheduler, "ELL");
			
			Tests.TestTools.login(scheduler);
			
			Tests.TestTools.createProject(scheduler, "Do the things");
			Tests.TestTools.createProject(scheduler, "for the lols");
			Tests.TestTools.createProject(scheduler, "Test project please ignore");
			Tests.TestTools.createProject(scheduler, "zing", "ELL");
			Tests.TestTools.createProject(scheduler, "lave pizza", Tests.TestTools.LOGIN_EMPLOYEE_INITIALS);
			Tests.TestTools.createProject(scheduler, "få 12");
			
			Tests.TestTools.addEmployeeToProject(scheduler, Tests.TestTools.LOGIN_EMPLOYEE_INITIALS, "zing");
			Tests.TestTools.addEmployeeToProject(scheduler, Tests.TestTools.LOGIN_EMPLOYEE_INITIALS, "lave pizza");
			Tests.TestTools.addEmployeeToProject(scheduler, Tests.TestTools.LOGIN_EMPLOYEE_INITIALS, "få 12");
			
			Tests.TestTools.addActivity(scheduler, "zing", "zang", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "zing", "ding", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "zing", "dong", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "zing", "pling", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "zing", "plong", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			
			Tests.TestTools.addActivity(scheduler, "lave pizza", "ost", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "lave pizza", "pepperoni", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "lave pizza", "tomat", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "lave pizza", "dej", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "lave pizza", "skinke", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			
		} catch (Exception e) {}
		
		switchPage(loginPage);
	}
	
	public <T extends JPanel> void switchPage(SuperPage<T> page)
	{
		mainPage.showPage(page.getPage(this));
		page.loadInformation();
		frame.setVisible(true);
	}
}
