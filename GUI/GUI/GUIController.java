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
			Tests.TestTools.createProject(scheduler, "For the lols");
			Tests.TestTools.createProject(scheduler, "Test project please ignore");
			Tests.TestTools.createProject(scheduler, "Lave pizza");
			Tests.TestTools.createProject(scheduler, "Få 12");
			Tests.TestTools.createProject(scheduler, "Zing", "ELL");
			Tests.TestTools.createProject(scheduler, "import AI.Skynet;");
			Tests.TestTools.safeCloseProject(scheduler, "import AI.Skynet;");
			
			Tests.TestTools.addEmployeeToProject(scheduler, Tests.TestTools.LOGIN_EMPLOYEE_INITIALS, "Zing");
			Tests.TestTools.addEmployeeToProject(scheduler, Tests.TestTools.LOGIN_EMPLOYEE_INITIALS, "Lave pizza");
			Tests.TestTools.addEmployeeToProject(scheduler, Tests.TestTools.LOGIN_EMPLOYEE_INITIALS, "Få 12");
			
			scheduler.login("ELL");
			Tests.TestTools.addActivity(scheduler, "Zing", "Zang", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "Zing", "Ding", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "Zing", "Dong", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "Zing", "Pling", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "Zing", "Plong", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			scheduler.login("LLLL");
			Tests.TestTools.addActivity(scheduler, "Lave pizza", "Ost", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "Lave pizza", "Pepperoni", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "Lave pizza", "Tomat", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "Lave pizza", "Dej", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "Lave pizza", "Skinke", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		switchPage(loginPage);
	}
	
	public <T extends JPanel> void switchPage(SuperPage<T> page)
	{
		mainPage.showPage(page.getPage(this));
		page.loadInformation();
		frame.setVisible(true);
	}
}
