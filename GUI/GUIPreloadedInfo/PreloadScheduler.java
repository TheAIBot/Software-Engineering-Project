package GUIPreloadedInfo;

import org.junit.Assert;
import org.junit.Test;

import GUI.GUIController;
import SoftwareHouse.Scheduler;

public class PreloadScheduler {
	
	public static void main(String[] args) {
		try {
			Scheduler scheduler = new Scheduler();
			
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
			Tests.TestTools.addActivity(scheduler, "Lave pizza", "Pølse", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "Lave pizza", "Bacon", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "Lave pizza", "Salat", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "Lave pizza", "Pomfritter", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			Tests.TestTools.addActivity(scheduler, "Lave pizza", "Småkager", new String[] {Tests.TestTools.LOGIN_EMPLOYEE_INITIALS});
			
			new GUIController(scheduler).show();		
		} catch (Exception e) {	}
	}
}
