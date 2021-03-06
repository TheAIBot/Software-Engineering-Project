package Tests;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import SoftwareHouse.Activity;
import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeAlreadyAssignedException;
import SoftwareHouse.ExceptionTypes.EmployeeMaxActivitiesReachedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.IllegalCharException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotPartOfEmployeesAdded;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;
import SoftwareHouse.ExceptionTypes.TooManyCharsException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;

/**
 * @author Andreas
 */
public class TestTools {
	
	public static final String LOGIN_EMPLOYEE_INITIALS = "LLLL";
	
	/**
	 * Andreas
	 */
	public static Employee addEmployee(Scheduler scheduler, String name) throws EmployeeNotFoundException, MissingInformationException, DuplicateNameException, TooManyCharsException, IllegalCharException
	{
		scheduler.addEmployee(name);
		Employee employee = scheduler.getEmployeeFromInitials(name);
		assertEquals(employee.getInitials(), name);
		return employee;
	}
	
	/**
	 * Andreas
	 */
	public static Activity addActivity(Scheduler scheduler, String projectName, String activityName, String[] toAddEmployeeInitials) throws ProjectNotFoundException, ActivityNotFoundException, NotLoggedInException, MissingInformationException, InvalidInformationException, EmployeeNotFoundException, DuplicateNameException, EmployeeMaxActivitiesReachedException, ProjectManagerNotLoggedInException
	{		
		String activityDetailedDescription = "oprettelsen af et brugerinterface for programmet";
		int expectedHours = 200;
		Calendar startDate = new GregorianCalendar();
		startDate.set(2016, 3, 16);
		Calendar endDate = new GregorianCalendar();
		endDate.set(2016, 4, 18);
		
		return addActivity(scheduler, 
						   projectName, 
						   activityName, 
						   activityDetailedDescription, 
						   expectedHours, 
						   startDate, 
						   endDate, 
						   toAddEmployeeInitials);
	}

	/**
	 * Andreas
	 */
	public static Activity addActivity(Scheduler scheduler, 
									   String projectName, 
									   String activityName,
									   String activityDetailedDescription,
									   int expectedHours,
									   Calendar startDate,
									   Calendar endDate,
									   String[] toAddEmployeeInitials) 
									   throws ProjectNotFoundException, 
															 ActivityNotFoundException, 
															 NotLoggedInException, 
															 MissingInformationException, 
															 InvalidInformationException, 
															 EmployeeNotFoundException, 
															 DuplicateNameException, 
															 EmployeeMaxActivitiesReachedException, 
															 ProjectManagerNotLoggedInException
	{
		Project project = null;
		try {
			project = scheduler.getProject(projectName);
		} catch (Exception e) {
			Assert.fail();
		}
		
		List<String> employeeInitials = new ArrayList<String>();
		for (String employeeInitial : toAddEmployeeInitials) {
			employeeInitials.add(employeeInitial);
		}
		
		int currentNumberOfActivities = project.getOpenActivities().size();
		project.addAcitivity(activityName,	activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
		assertEquals(currentNumberOfActivities + 1, project.getOpenActivities().size());
		
		Activity activity = scheduler.getActivity(projectName, activityName);
		
		assertEquals(activity.getName(), activityName);
		assertEquals(activity.getDetailText(), activityDetailedDescription);
		assertEquals(activity.getBudgettedTime(), expectedHours);
		assertEquals(activity.getTimePeriod().getStartDate(), startDate);
		assertEquals(activity.getTimePeriod().getEndDate(), endDate);
		
		return activity;
	}
	
	
	

	/**
	 * Andreas
	 */
	public static Activity forceAddActivity(Scheduler scheduler, String projectName, String activityName, String[] toAddEmployeeInitials) throws ProjectNotFoundException, ActivityNotFoundException, NotLoggedInException, EmployeeNotFoundException, DuplicateNameException, EmployeeMaxActivitiesReachedException, ProjectManagerNotLoggedInException, InvalidInformationException, MissingInformationException
	{
		String activityDetailedDescription = "oprettelsen af et brugerinterface for programmet";
		int expectedHours = 200;
		Calendar startDate = new GregorianCalendar();
		startDate.set(2016, 3, 16);
		Calendar endDate = new GregorianCalendar();
		endDate.set(2016, 4, 18);
		
		return forceAddActivity(scheduler, 
						   projectName, 
						   activityName, 
						   activityDetailedDescription, 
						   expectedHours, 
						   startDate, 
						   endDate, 
						   toAddEmployeeInitials);
	}

	/**
	 * Andreas
	 */
	public static Activity forceAddActivity(Scheduler scheduler, 
			   								String projectName, 
			   								String activityName,
			   								String activityDetailedDescription,
			   								int expectedHours,
			   								Calendar startDate,
			   								Calendar endDate,
			   								String[] toAddEmployeeInitials) throws ProjectNotFoundException, ActivityNotFoundException, NotLoggedInException, EmployeeNotFoundException, DuplicateNameException, EmployeeMaxActivitiesReachedException, ProjectManagerNotLoggedInException, InvalidInformationException, MissingInformationException
	{
		Project project = null;
		try {
			project = scheduler.getProject(projectName);
		} catch (Exception e) {
			Assert.fail();
		}
		
		List<String> employeeInitials = new ArrayList<String>();
		for (String employeeInitial : toAddEmployeeInitials) { 
			employeeInitials.add(employeeInitial);
		}
		
		int currentNumberOfActivities = project.getOpenActivities().size();
		project.forceAddAcitivity(activityName,	activityDetailedDescription, employeeInitials, startDate, endDate, expectedHours);
		assertEquals(currentNumberOfActivities + 1, project.getOpenActivities().size());
		
		Activity activity = scheduler.getActivity(projectName, activityName);
		
		assertEquals(activity.getName(), activityName);
		assertEquals(activity.getDetailText(), activityDetailedDescription);
		assertEquals(activity.getBudgettedTime(), expectedHours);
		assertEquals(activity.getTimePeriod().getStartDate(), startDate);
		assertEquals(activity.getTimePeriod().getEndDate(), endDate);
		
		return activity;
	}

	/**
	 * Andreas
	 */
	public static Employee addEmployeeToProject(Scheduler scheduler, String employeeName, String projectName)
	{
		Employee employee = null;
		try {
			employee = scheduler.getEmployeeFromInitials(employeeName);
		} catch (EmployeeNotFoundException e) {
		}
		if (employee == null) {
			try {
				employee = addEmployee(scheduler, employeeName);
			} catch (Exception e) {
				Assert.fail();
			}
		}
		Project project = null;
		try {
			project = scheduler.getProject(projectName);
		} catch (Exception e) {
			Assert.fail(e.getClass() + e.getMessage());
		}
		try {
			Assert.assertTrue(project.addEmployee(employeeName));
		} catch (Exception e) {
		}
		
		return employee;
	}

	/**
	 * Andreas
	 */
	public static Project createProject(Scheduler scheduler,String projectName) throws MissingInformationException, DuplicateNameException, NotLoggedInException, InvalidInformationException, EmployeeNotFoundException, EmployeeAlreadyAssignedException, ProjectManagerNotPartOfEmployeesAdded
	{
		return createProject(scheduler, projectName, LOGIN_EMPLOYEE_INITIALS);
	}

	/**
	 * Andreas
	 */
	public static Project createProject(Scheduler scheduler,String projectName, String projectManagerInitial) throws MissingInformationException, DuplicateNameException, NotLoggedInException, InvalidInformationException, EmployeeNotFoundException, EmployeeAlreadyAssignedException, ProjectManagerNotPartOfEmployeesAdded
	{
		int currentNumberOfProjects = scheduler.getProjects().size();
		Employee projectmanager = scheduler.getEmployeeFromInitials(projectManagerInitial);
		scheduler.createProject(projectName, "", "", Collections.singletonList(projectmanager), 0, projectManagerInitial, null);;
		
		Project project = null;
		try {
			project = scheduler.getProject(projectName);
		} catch (Exception e) {
			Assert.fail();
		}
		
		assertEquals(currentNumberOfProjects + 1, scheduler.getProjects().size());
		assertEquals(projectName, project.getName());
		
		return project;
	}

	/**
	 * Andreas
	 */
	public static Employee login(Scheduler scheduler)
	{
		Employee employee = null;
		try {
			employee = TestTools.addEmployee(scheduler, LOGIN_EMPLOYEE_INITIALS);
			scheduler.login(LOGIN_EMPLOYEE_INITIALS);
		} catch (Exception e) {
			Assert.fail();
		}
		return employee;
	}

	/**
	 * Andreas
	 */
	public static void safeCloseProject(Scheduler scheduler, String projectName)
	{
		try {
			Project projectToClose = scheduler.getProject(projectName);
			projectToClose.close();
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}