package Tests;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import SoftwareHouse.Activity;
import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeMaxActivitiesReachedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.IllegalCharException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.InvalidProjectInitilizationInput;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;
import SoftwareHouse.ExceptionTypes.TooManyCharsException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;

public class TestTools {
	
	public static final String LOGIN_EMPLOYEE_INITIALS = "LLLL";
	
	public static Employee addEmployee(Scheduler scheduler, String name) throws EmployeeNotFoundException, MissingInformationException, DuplicateNameException, TooManyCharsException, IllegalCharException
	{
		scheduler.addEmployee(name);
		Employee employee = scheduler.getEmployeeFromInitials(name);
		assertEquals(employee.getInitials(), name);
		return employee;
	}
	
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
	
	
	
	
	public static Activity forceAddActivity(Scheduler scheduler, String projectName, String activityName, String[] toAddEmployeeInitials) throws ProjectNotFoundException, ActivityNotFoundException, NotLoggedInException, EmployeeNotFoundException, DuplicateNameException, EmployeeMaxActivitiesReachedException, ProjectManagerNotLoggedInException
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
	
	public static Activity forceAddActivity(Scheduler scheduler, 
			   								String projectName, 
			   								String activityName,
			   								String activityDetailedDescription,
			   								int expectedHours,
			   								Calendar startDate,
			   								Calendar endDate,
			   								String[] toAddEmployeeInitials) throws ProjectNotFoundException, ActivityNotFoundException, NotLoggedInException, EmployeeNotFoundException, DuplicateNameException, EmployeeMaxActivitiesReachedException, ProjectManagerNotLoggedInException
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
		Assert.assertTrue(project.addEmployee(employeeName));
		//TODO add asserts here to check that the employee was added
		return employee;
	}

	public static Project createProject(Scheduler scheduler,String projectName) throws MissingInformationException, DuplicateNameException, NotLoggedInException, InvalidInformationException, EmployeeNotFoundException
	{
		return createProject(scheduler, projectName, LOGIN_EMPLOYEE_INITIALS);
	}
	
	public static Project createProject(Scheduler scheduler,String projectName, String projectManagerInitial) throws MissingInformationException, DuplicateNameException, NotLoggedInException, InvalidInformationException, EmployeeNotFoundException
	{
		int currentNumberOfProjects = scheduler.getProjects().size();
		scheduler.createProject(projectName, "", "", null, 0, projectManagerInitial, null);;
		
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
}