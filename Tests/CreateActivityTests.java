import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.Test;

import SoftwareHouse.Activity;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;

import org.junit.Assert;

public class CreateActivityTests {
	
	@Test
	public void testCreateNormal() {	
		Scheduler scheduler = new Scheduler();
		try {
			scheduler.createProject("Navision Stat");
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
		assertTrue(project.getOpenActivities().size() == 1);
		
		Activity activity = project.getOpenActivities().get(0);
		assertEquals(activity.getTitle(), activityName);
		assertEquals(activity.getDetailText(), activityDetailedDescription);
		assertEquals(activity.getBudgettedTime(), expectedHours);
		assertEquals(activity.getTimePeriod().getStartDate(), startDate);
		assertEquals(activity.getTimePeriod().getEndDate(), endDate);		
	}
	//need to add a lot more tests here to check for missing information
	//also need to test forceAddActivity
}