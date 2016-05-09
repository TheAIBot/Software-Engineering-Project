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

/**
 * @author Andreas
 */
public class GUIController {
	private JFrame frame;
	private final Scheduler scheduler;
	private final MainPage mainPage;
	public final LoginPage loginPage;
	public final ProjectsPage projectPage;
	public final MyPagePage myPagePage;
	
 	public GUIController()
	{
 		this(new Scheduler());
	}
 	
 	public GUIController(Scheduler scheduler)
	{
 		this.scheduler = scheduler;
 		this.mainPage = new MainPage(this, scheduler);
 		this.loginPage = new LoginPage(this, scheduler);
 		this.projectPage = new ProjectsPage(this, scheduler);
 		this.myPagePage = new MyPagePage(this, scheduler);
 		
		this.frame = new JFrame("👁 Big Brother");
		this.frame.add(mainPage.getPage(this));
		this.frame.setSize(1010, 640); 
	}
	
	public void show()
	{		
		switchPage(loginPage);
	}
	
	public <T extends JPanel> void switchPage(SuperPage<T> page)
	{
		mainPage.showPage(page.getPage(this));
		page.loadInformation();
		frame.setVisible(true);
	}
}
