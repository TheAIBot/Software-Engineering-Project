package GUI;

import java.awt.GridBagConstraints;

import javax.swing.JFrame;

import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import Tests.TestCloseProject;

public class GUIController {
	private final Scheduler scheduler = new Scheduler();
	private final MainPage mainPage = new MainPage(scheduler);
	private final LoginWindow loginWindow = new LoginWindow(scheduler);
	
	public void show()
	{
		GUIFrame best = new GUIFrame();
		
		try {
			Tests.TestTools.addEmployee(scheduler, "AGC");
			Tests.TestTools.addEmployee(scheduler, "JSB");
			Tests.TestTools.addEmployee(scheduler, "NR");
			Tests.TestTools.addEmployee(scheduler, "ELL");
		} catch (Exception e) {
			
		}
		
		
		
		JFrame frame = best.getFrame();
		frame.add(mainPage.getPage(this));
		mainPage.showPage(loginWindow.getPage(this));
		loginWindow.loadInformation();
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setVisible(true);
		frame.revalidate();
	}
}
