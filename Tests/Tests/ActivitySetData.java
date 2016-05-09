package Tests;

import org.junit.Test;

import SoftwareHouse.Activity;
import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.TimePeriod;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeAlreadyAssignedException;
import SoftwareHouse.ExceptionTypes.EmployeeMaxActivitiesReachedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotLoggedInException;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.*;

public class ActivitySetData {

	public static Scheduler scheduler;
	private static final String PROJECT_NAME = "Navision Stat";
	private static final String ACTIVITY_NAME = "TheActivity";
	private static final String DETAILED_TEXT = "The best";
	private static final String COMPANY_NAME = "Det offentlige";
	private static final int BUDGETED_TIME = 42;
	private static final List<Employee> EMPLOYEE_LIST_EMPTY = new ArrayList<>();
	private static final String EMPTY_NAME = "";
	private static final String PROJECT_MANAGER_INITIALS = "JSB";
	private TimePeriod VALID_TIME_PERIOD ;
	private static List<Employee> employeeListWithEmployees;
	private static Project project;
	private static Activity activity;
	private static final String hasNoPermissionMessage = "Either there needs to be no project manager for the project" +
            " or the person needs to be logged in, for edits to be made";
	
	@Before
	public void setup(){
		scheduler = new Scheduler();
		TestTools.login(scheduler);
		try {
			TestTools.createProject(scheduler, PROJECT_NAME);
			scheduler.addEmployee(PROJECT_MANAGER_INITIALS);
			project = scheduler.getProject(PROJECT_NAME);
			project.addEmployee(PROJECT_MANAGER_INITIALS);
			project.setProjectManager(PROJECT_MANAGER_INITIALS);
			scheduler.login(PROJECT_MANAGER_INITIALS);
			project.forceAddAcitivity(ACTIVITY_NAME, DETAILED_TEXT, null, null, null, 0);
			activity = project.getActivity(ACTIVITY_NAME);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}		
	}
	
	public void loginToHasNoPermission(){
		try {
			scheduler.addEmployee("ASB");
			scheduler.login("ASB");
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void setActivityNameToSameName(){
		try {
			activity.addEmployee(PROJECT_MANAGER_INITIALS);
			activity.setName(ACTIVITY_NAME);
			assertTrue(activity.getName() == ACTIVITY_NAME);
		} catch (Exception e1) {
			Assert.fail(e1.getMessage());
		} 
	}
	
	@Test
	public void setDetailTextToNewDetailText(){
		try {
			activity.setDetailText("The new text");
			assertEquals("The new text", activity.getDetailText());
		} catch (Exception e1) {
			Assert.fail(e1.getMessage());
		} 
	}
	
	@Test
	public void setBudgetedTimeToNewLegalTime(){
		try {
			activity.setBudgettedTime(42);
			assertEquals(42, activity.getBudgettedTime());
		} catch (Exception e1) {
			Assert.fail(e1.getMessage());
		} 
	}
	
	@Test
	public void setNewLegalTimePeriod(){
		try {
			activity.setTimePeriod(new TimePeriod(new GregorianCalendar(1200, 1, 23), new GregorianCalendar(1222, 3, 2)));
			assertEquals(new GregorianCalendar(1200, 1, 23), activity.getTimePeriod().getStartDate());
			assertEquals(new GregorianCalendar(1222, 3, 2), activity.getTimePeriod().getEndDate());
		} catch (Exception e1) {
			Assert.fail(e1.getMessage());
		} 
	}
	
	@Test
	public void setAssignedEmployeesToListWithEmployees(){
		try {
			scheduler.addEmployee("DIO");
			scheduler.addEmployee("JoJo");
			project.addEmployee("DIO");
			project.addEmployee("JoJo");
			List<Employee> bizzareAdventures = new ArrayList<Employee>();
			Employee DIO = scheduler.getEmployeeFromInitials("DIO");
			Employee JoJo = scheduler.getEmployeeFromInitials("JoJo");
			bizzareAdventures.add(DIO);
			bizzareAdventures.add(JoJo);
			activity.setAssignedEmployees(bizzareAdventures);
			assertTrue(activity.getAssignedEmployees().contains(DIO));
			assertTrue(activity.getAssignedEmployees().contains(JoJo));
			assertEquals(2, activity.getAssignedEmployees().size());
		} catch (Exception e1) {
			Assert.fail(e1.getMessage());
		} 
	}
   
	@Test
	public void setAssignedEmployeesToNull(){
		try {
			activity.setAssignedEmployees(null);
			assertEquals(0, activity.getAssignedEmployees().size());
		} catch (Exception e1) {
			Assert.fail(e1.getMessage());
		} 
	}
	
	@Test
	public void setActivityNameNoPermission(){
		loginToHasNoPermission();
		try {
			activity.addEmployee(PROJECT_MANAGER_INITIALS);
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			assertEquals(hasNoPermissionMessage, e.getMessage());
		} catch (Exception e1) {
			Assert.fail(e1.getMessage());
		}
	}	

	@Test
	public void setDetailTextToNewDetailTextHasNoPermission(){
		loginToHasNoPermission();
		try {
			activity.setDetailText("The new text");
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			assertEquals(hasNoPermissionMessage, e.getMessage());
		} catch (Exception e1) {
			Assert.fail(e1.getMessage());
		} 
	}
	
	@Test
	public void setBudgetedTimeNoPermission(){
		loginToHasNoPermission();
		try {
			activity.setBudgettedTime(42);
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			assertEquals(hasNoPermissionMessage, e.getMessage());
		} catch (Exception e1) {
			Assert.fail(e1.getMessage());
		} 
	}
	
	public void  setNewLegalTimePeriodHasNoPermission(){
		loginToHasNoPermission();
		try {
			activity.setTimePeriod(new TimePeriod(new GregorianCalendar(1200, 1, 23), new GregorianCalendar(1222, 3, 2)));
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			assertEquals(hasNoPermissionMessage, e.getMessage());
		} catch (Exception e1) {
			Assert.fail(e1.getMessage());
		} 
	}

	@Test
	public void setAssignedEmployeesHasNoPermission(){
		loginToHasNoPermission();
		try {
			activity.setAssignedEmployees(null);
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			assertEquals(hasNoPermissionMessage, e.getMessage());
		} catch (Exception e1) {
			Assert.fail(e1.getMessage());
		} 
	}
	
	@Test
	public void addEmployeeHasPermission(){
		loginToHasNoPermission();
		try {
			activity.addEmployee("JSB");
			Assert.fail();
		} catch (ProjectManagerNotLoggedInException e) {
			assertEquals(hasNoPermissionMessage, e.getMessage());
		} catch (Exception e1) {
			Assert.fail(e1.getMessage());
		} 
	}
}
