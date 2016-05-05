package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

import GUI.Pages.LoginPage;
import GUI.Pages.MainPage;
import GUI.Pages.ProjectPage;
import GUI.Pages.SuperPage;
import SoftwareHouse.Scheduler;

public class GUIController {
	private JFrame frame;
	private final Scheduler scheduler = new Scheduler();
	private final MainPage mainPage = new MainPage(this, scheduler);
	public final LoginPage loginPage = new LoginPage(this, scheduler);
	public final ProjectPage projectPage = new ProjectPage(this, scheduler);
	
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
			Tests.TestTools.createProject(scheduler, "zing");
			Tests.TestTools.createProject(scheduler, "lave pizza");
			Tests.TestTools.createProject(scheduler, "få 12");
			
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
