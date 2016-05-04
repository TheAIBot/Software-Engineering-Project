package GUI.Pages;

import java.awt.GridBagConstraints;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.sun.glass.events.WindowEvent;

import GUI.GUIController;
import GUI.Tools;
import GUI.Panels.LoginPanel;
import GUI.DialogBoxes.CreateUserDialog;
import SoftwareHouse.Employee;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;

public class LoginPage extends SuperPage<LoginPanel> {
	
	public LoginPage(GUIController controller, Scheduler scheduler) {
		super(controller, scheduler);
	}

	@Override
	public LoginPanel createPage(GUIController controller) {
		LoginPanel loginPanel = new LoginPanel();
		loginPanel.getEmployeeSearchTextField().getDocument().addDocumentListener(new DocumentListener() {
			  public void changedUpdate(DocumentEvent e){
				  loadInformation();
			  }
			  public void removeUpdate(DocumentEvent e){
				  loadInformation();
			  }
			  public void insertUpdate(DocumentEvent e){
				  loadInformation();
			  }
		});
		loginPanel.getLoginButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable)loginPanel.getEmployeeList().getViewport().getView();
				if (table.getSelectedRow() != -1) {
					String emplyeeInitials = (String)table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
					try {
						scheduler.login(emplyeeInitials);
						loadInformation();
					} catch (EmployeeNotFoundException e1) {
						page.getLoginErrorLabel().setText(e1.getMessage());
					}	
				}
			}
		});
		loginPanel.getCreateNewEmployeeButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CreateUserDialog dialog = new CreateUserDialog(scheduler);
				dialog.addWindowListener(new WindowListener() {
					@Override
					public void windowOpened(java.awt.event.WindowEvent e) {}
					@Override
					public void windowIconified(java.awt.event.WindowEvent e) {}
					@Override
					public void windowDeiconified(java.awt.event.WindowEvent e) {}
					@Override
					public void windowDeactivated(java.awt.event.WindowEvent e) {}
					@Override
					public void windowClosing(java.awt.event.WindowEvent e) {
						loadInformation();
					}
					@Override
					public void windowClosed(java.awt.event.WindowEvent e) {}
					@Override
					public void windowActivated(java.awt.event.WindowEvent e) {}
				});
				dialog.setVisible(true);
			}
		});
		return loginPanel;
	}
	
	@Override
	public void loadInformation()
	{		
		page.getEmployeeList().setViewportView(Tools.createTableOfEmployees(scheduler.getEmployeesContainingString(page.getEmployeeSearchTextField().getText())));
		
		if (scheduler.isAnyoneLoggedIn()) {
			page.getLoggedInAsLabel().setText(scheduler.getLoggedInEmployee().getInitials());
		} else {
			page.getLoggedInAsLabel().setText("None");
		}
	}
}
