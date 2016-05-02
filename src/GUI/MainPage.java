package GUI;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import SoftwareHouse.Scheduler;

public class MainPage extends SuperPage {

	private JPanel showPagePanel = new JPanel(new BorderLayout());
	private JButton employeesPageButton = new JButton("Oversigt over medarbejdere");
	private JButton myPageButton = new JButton("Min side");
	private JButton projectsPageButton = new JButton("Projekter");
	private JButton derpPageButton = new JButton("jfsdlfk");
	
	public MainPage(Scheduler scheduler) {
		super(scheduler);
	}

	@Override
	public void initiatePage(GUIController controller) {
		page.add(employeesPageButton, Tools.createConstraint(0, 0, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.BOTH, 0, 1));
		page.add(myPageButton,        Tools.createConstraint(0, 1, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.BOTH, 0, 1));
		page.add(projectsPageButton,  Tools.createConstraint(0, 2, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.BOTH, 0, 1));
		page.add(derpPageButton,      Tools.createConstraint(0, 3, 1, 1, GridBagConstraints.LINE_START, GridBagConstraints.BOTH, 0, 1));
		page.add(showPagePanel,       Tools.createConstraint(1, 0, 2, 4, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 0, 40));
	}
	
	public void showPage(JPanel toShow)
	{
		showPagePanel.removeAll();
		showPagePanel.add(toShow, BorderLayout.CENTER);
	}
	
	public void subscribeEmployeePageButton(Runnable eventMethod)
	{
		employeesPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eventMethod.run();
			}
		});
	}
	
	public void subscribeMyPageButton(Runnable eventMethod)
	{
		myPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eventMethod.run();
			}
		});
	}
	
	public void subscribeProjectPageButton(Runnable eventMethod)
	{
		projectsPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eventMethod.run();
			}
		});
	}
	
	public void subscribeDerpPageButton(Runnable eventMethod)
	{
		derpPageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eventMethod.run();
			}
		});
	}
	
	

	
}
