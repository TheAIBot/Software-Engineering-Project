package GUI.Pages;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import GUI.GUIController;
import GUI.Tools;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;

public class MainPage extends SuperPage<JPanel> {

	private JPanel showPagePanel = new JPanel(new BorderLayout());
	private JButton employeesPageButton = new JButton("Oversigt over medarbejdere");
	private JButton myPageButton = new JButton("Min side");
	private JButton projectsPageButton = new JButton("Projekter");
	private JButton derpPageButton = new JButton("jfsdlfk");
	
	public MainPage(GUIController controller, Scheduler scheduler) {
		super(controller, scheduler);
	}

	@Override
	public JPanel createPage(GUIController controller) {
		page = new JPanel(new GridBagLayout());
		page.add(employeesPageButton, Tools.createConstraint(0, 0, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.BOTH, 0, 5));
		page.add(myPageButton,        Tools.createConstraint(0, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.BOTH, 0, 5));
		page.add(projectsPageButton,  Tools.createConstraint(0, 2, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.BOTH, 0, 5));
		//page.add(derpPageButton,      Tools.createConstraint(0, 3, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.BOTH, 0, 5));
		page.add(showPagePanel,       Tools.createConstraint(1, 0, 2, 4, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 100));
		
		employeesPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (scheduler.isAnyoneLoggedIn()) {
					controller.switchPage(controller.loginPage);
				}
			}
		});
		myPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (scheduler.isAnyoneLoggedIn()) {
					controller.switchPage(controller.myPagePage);
				}
			}
		});
		projectsPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (scheduler.isAnyoneLoggedIn()) {
					controller.switchPage(controller.projectPage);
				}
			}
		});
		derpPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (scheduler.isAnyoneLoggedIn()) {
					controller.switchPage(controller.loginPage);
				}
			}
		});
		
		
		return page;
	}

	public void showPage(JPanel toShow)
	{
		showPagePanel.removeAll();
		showPagePanel.add(toShow, BorderLayout.CENTER);
	}
	
	@Override
	public void loadInformation() {
		
	}
}
