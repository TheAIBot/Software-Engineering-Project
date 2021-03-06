package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import SoftwareHouse.Activity;
import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.Tools;
import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeAlreadyAssignedException;
import SoftwareHouse.ExceptionTypes.EmployeeMaxActivitiesReachedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;

/**
 * @author Andreas
 */
public class ActivitySpecificTest {

	private Scheduler scheduler = null;
	private Project project;
	private Activity activity = null;
	
	/**
	 * Andreas
	 */
	@Before
	public void setup()
	{
		scheduler = new Scheduler();
		TestTools.login(scheduler);
		try {
			scheduler.createProject("Navision Stat", "SCMEH", "Of doom", null, 0, null, null);
			project = scheduler.getProject("Navision Stat");
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		assertEquals(project.getOpenActivities().size(), 0);
		
		TestTools.addEmployeeToProject(scheduler, "JSB", "Navision Stat");
		TestTools.addEmployeeToProject(scheduler, "ELL", "Navision Stat");
		TestTools.addEmployeeToProject(scheduler, "AGC", "Navision Stat");
		TestTools.addEmployeeToProject(scheduler, "NR", "Navision Stat");
		try {
			scheduler.login("JSB");
			project.setProjectManager("JSB");
		} catch (Exception e1) {
			Assert.fail();
		}
		
		try {
			project.forceAddAcitivity("ZaMeh", "test", null, null, null, 0);
			activity = project.getActivity("ZaMeh");
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		} 
		
	}
	
	@Test
	public void testSetBudgettedTime(){
	}
	
	/**
	 * Jesper
	 */
	@Test
	public void testGetOpenActivitiesActivityNotExisting(){
		try {
			project.getActivity("The activity that would not be");
			Assert.fail();
		} catch (ActivityNotFoundException e) {
			assertEquals("The activity: The activity that would not be is not a part of the project: Navision Stat", e.getMessage());
			try {
				project.getOpenActivityFromName("The activity that would not be");
				Assert.fail();
			} catch (Exception e1) {
				assertEquals("The open activity: The activity that would not be is not a part of the project: Navision Stat", e1.getMessage());
			}
		}
	}
	
	/**
	 * Andreas
	 */
	@Test
	public void testAddActivityEmptyListOfEmployees(){
		try {
			project.addAcitivity("meh", "test", new ArrayList<String>(), new GregorianCalendar(2011, 10, 3), new GregorianCalendar(2012, 10, 3), 0);
			Assert.fail();
		} catch (Exception e) {
			assertEquals("Missing employees", e.getMessage());
		}
	}
	
	@Test
	public void testIsValidProjectNameNameIsNull(){
		assertFalse(project.isNewValidActivityName(null));
	}
	
	@Test
	public void testIsValidProjectSemiEmptyString(){
		assertFalse(project.isNewValidActivityName("   "));
	}
	
	@Test
	public void testGetInProject(){
		assertEquals(project.getName(), activity.getInProject().getName());
	}
	

	@Test
	public void addEmployeeAlreadyPartOfActivity(){
		try {
			activity.addEmployee("JSB");
			activity.addEmployee("JSB");
		} catch(EmployeeAlreadyAssignedException e){
			assertEquals("JSB is already assigned to this activity", e.getMessage());
		} catch (Exception e1) {
			Assert.fail(e1.getMessage());
		} 
	}
	
	
	
	
	

}
