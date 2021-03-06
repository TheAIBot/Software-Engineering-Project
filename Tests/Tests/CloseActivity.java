package Tests;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;

/**
 * @author ELL
 */
public class CloseActivity {

	/**
	 * Emil
	 * @throws ActivityNotFoundException
	 */
	@Test
	public void closeActivitySuccessTest() throws ActivityNotFoundException
	{
		Scheduler scheduler = new Scheduler();
		TestTools.login(scheduler);
		try {
			TestTools.createProject(scheduler,"Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
		
		Project project = null;
		try {
			project = scheduler.getProject("Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
		assertEquals(project.getOpenActivities().size(), 0);
		
		String activityName = "Udvikling af brugerinterface";
		String activityDetailedDescription = "oprettelsen af et brugerinterface for programmet";
		int expectedHours = 200;
		Calendar startDate = new GregorianCalendar();
		startDate.set(2016, 3, 16);
		Calendar endDate = new GregorianCalendar();
		endDate.set(2016, 4, 18);
		try {
			scheduler.addEmployee("JBS");
			scheduler.addEmployee("ELL");
			scheduler.addEmployee("AGC");
			scheduler.addEmployee("NR");
		} catch (Exception e) {
			Assert.fail();
		}
		

		try {
			if(!(project.addEmployee("JBS") && project.addEmployee("ELL") && project.addEmployee("AGC") &&	project.addEmployee("NR"))){
				Assert.fail();
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		} 
		
		
		List<String> employeeInitials = new ArrayList<String>();
		employeeInitials.add("JBS");
		employeeInitials.add("ELL");
		employeeInitials.add("AGC");
		employeeInitials.add("NR");
		
		try {
			project.addAcitivity(activityName,	activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
		} catch (Exception e) {
			Assert.fail();
		}
		
		assertEquals(project.getOpenActivities().size(), 1);
		assertEquals(project.getClosedActivities().size(), 0);
		project.closeActivity(activityName);
		assertEquals(project.getOpenActivities().size(), 0);
		assertEquals(project.getClosedActivities().size(), 1);
	}

	/**
	 * Andreas
	 */
	@Test
	public void closeActivityMissingActivitytest()
	{
		Scheduler scheduler = new Scheduler();
		TestTools.login(scheduler);
		try {
			scheduler.createProject("Navision Stat");
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
		
		Project project = null;
		try {
			project = scheduler.getProject("Navision Stat");
		} catch (Exception e) {
			Assert.fail();
		}
		try {
			project.closeActivity("Does not exist");
			Assert.fail();
		} catch (ActivityNotFoundException e) {
		}
	}
}
