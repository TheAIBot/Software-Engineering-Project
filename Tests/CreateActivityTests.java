

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.Test;

import com.sun.swing.internal.plaf.metal.resources.metal;

import SoftwareHouse.Activity;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;

public class CreateActivityTests {
	
	@Test
	public void testCreateNormal() {	
		Scheduler scheduler = new Scheduler();
		scheduler.createProject("Navision Stat");
		Project project = scheduler.getProject("Navision Stat");
		assertTrue(project.getOpenActivities().size() == 0);
		String activityName = "Udvikling af brugerinterface";
		String activityDetailedDescription = "oprettelsen af et brugerinterface for programmet";
		int excepctedHours = 200;
		Calendar startTime = new GregorianCalendar();
		startTime.set(2016, 3, 16);
		Calendar endTime = new GregorianCalendar();
		endTime.set(2016, 4, 18);
		project.addAcitivity(activityName,	activityDetailedDescription, 200, startTime, endTime);
		assertTrue(project.getOpenActivities().size() == 1);
		Activity activity = project.getOpenActivities().get(0);
		assertTrue(activity.getName() == activityName);
		assertTrue(activity.getDescription() == activityDetailedDescription);
		assertTrue(activity.getBudgetedTime() == excepctedHours);
		assertTrue(activity.getTimePeriod().getStartDate() == startTime);
		assertTrue(activity.getTimePeriod().getEndDate() == endTime);		
	}
	
	
}
