package Tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import SoftwareHouse.Activity;
import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.ActivityNotFoundException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import sun.net.www.content.audio.x_aiff;

public class SeeEmployeeInformation {
	private Scheduler scheduler = null;
	private String project1Name = "15-puzzle-spil";
	private String activity1Name = "brugergrænseflade";
	private String user1Initials = "AM";
	
	@Before
	public void setup()
	{
		scheduler = new Scheduler();
		TestTools.login(scheduler);
		try {
			scheduler.createProject("15-puzzle-spil");			
		} catch (Exception e) {
			Assert.fail();
		}		
		Project project = null;
		try {
			project = scheduler.getProject("15-puzzle-spil");
			Calendar startDate = new GregorianCalendar();
			startDate.set(2016, 3, 16);
			Calendar endDate = new GregorianCalendar();
			endDate.set(2016, 4, 18);
			project.forceAddAcitivity("brugergrænseflade", "It is the brugergrænseflade that will pierce through the heavens!"
					, new ArrayList<String>(), startDate, endDate, 200);
		} catch (Exception e) {
			Assert.fail();
		}
		assertEquals(project.getOpenActivities().size(), 1);		
		try {
			scheduler.addEmployee("JBS");
			scheduler.addEmployee("ELL");
			scheduler.addEmployee("AGC");
			scheduler.addEmployee("NR");
			scheduler.addEmployee("AM");
			
			project.addEmployee("JBS");
			project.addEmployee("ELL");
			project.addEmployee("AGC");
			project.addEmployee("NR");
			project.addEmployee("AM");
			
			project.getOpenActivities().get(0).addEmployee("AM");
		} catch (Exception e) {
			Assert.fail();
		}
	} 	
	
	public void ProperEmployeeSelectionOneEmployeeTest(){
		List<Employee> employeesFound = scheduler.getEmployeesContainingString(user1Initials); //Represents an user, filtering the list of possible users to see information about.
		assertTrue(employeesFound.size() == 1);
		assertTrue(employeesFound.stream().anyMatch(x -> x.getInitials() == user1Initials));
		assertTrue(employeesFound.stream().allMatch(x -> x.getInitials().contains(user1Initials)));
		//Every employee has an unique string of initials associated with them.
		Employee[] hopefullyOnlyArndt = (Employee[]) employeesFound.stream().filter(x -> x.getInitials().equals(user1Initials)).toArray();	//Represents an user selecting the name, amongst the filtered list of users.
		assertTrue(hopefullyOnlyArndt.length == 1);
		assertTrue(hopefullyOnlyArndt[0].getInitials() == user1Initials);
	}
	
	public void ProperEmployeeSelectionMultipleEmployeesTest(){
		List<Employee> employeesFound = scheduler.getEmployeesContainingString("A");
		assertTrue(employeesFound.size() == 2);
		assertTrue(employeesFound.stream().anyMatch(x -> x.getInitials() == user1Initials));
		assertTrue(employeesFound.stream().allMatch(x -> x.getInitials().contains("A"))); //TODO Burde lave det sådan så at man tjekker om personerne er præcist dem man forventede, men gider ikke.
		//Every employee has an unique string of initials associated with them.
		Employee[] hopefullyOnlyArndt = (Employee[]) employeesFound.stream().filter(x -> x.getInitials().equals(user1Initials)).toArray();		
		assertTrue(hopefullyOnlyArndt.length == 1);
		assertTrue(hopefullyOnlyArndt[0].getInitials() == user1Initials);
	}
	
	@Test
	public void SeeEmployeeInformationSuccesfullTestOneProjectOneActivityTest() {
		Employee Arndt = (Employee) scheduler.getEmployeesContainingString(user1Initials)
																				 .stream()
																				 .filter(x -> x.getInitials().equals(user1Initials))
																				 .toArray()[0];		
		//Checking if the project and activities information is correct - it does not take into account... /TODO
		List<Project> employeesProjects =  Arndt.getProjects();
		assertTrue(employeesProjects.size() == 1);
		assertTrue(employeesProjects.get(0).getName() == project1Name);
		List<Activity> employeesActivities = Arndt.getActivities();
		assertTrue(employeesActivities.size() == 1);
		assertTrue(employeesActivities.get(0).getName() == activity1Name);		
		
		//History employeesHistory = hopefullyArndt.getHistory(); //TODO history checks
		//It is already checked that the history object works, so no need to check it again.		
	}
	
	@Test
	public void SeeEmployeeInformationMultipleProjectsOneActivityTest() {		
		String project2Name = "Navision stat";
		String activity2Name = "TheSecondActivity";
		try {			
			Calendar startDate = new GregorianCalendar();
			startDate.set(2015, 4, 16);
			Calendar endDate = new GregorianCalendar();
			endDate.set(2016, 7, 18);
			scheduler.createProject(project2Name);
			scheduler.getProject(project2Name).forceAddAcitivity(activity2Name, "'Tis the second one", new ArrayList<String>(), startDate, endDate, 1);
			scheduler.getProject(project2Name).addEmployee(user1Initials);
			scheduler.getProject(project2Name).getOpenActivities().get(0).addEmployee(user1Initials);
		} catch (Exception e) {
			fail();
		} 				
		Employee Arndt = (Employee) scheduler.getEmployeesContainingString(user1Initials)
				 .stream()
				 .filter(x -> x.getInitials().equals(user1Initials))
				 .toArray()[0];		
		//Checking if the project and activities information is correct - it does not take into account... /TODO
		List<Project> employeesProjects =  Arndt.getProjects();
		assertTrue(employeesProjects.size() == 2);
		assertTrue(employeesProjects.stream().allMatch(x -> (x.getName() == project1Name || x.getName() == project2Name)));
		List<Activity> employeesActivities = Arndt.getActivities();
		assertTrue(employeesActivities.size() == 2);
		assertTrue(employeesActivities.stream().allMatch(x -> (x.getName() == activity1Name && x.getProjectName() == project1Name) ||
																																							(x.getName() == activity2Name && x.getProjectName() == project2Name)));
		//History employeesHistory = hopefullyArndt.getHistory(); 
		//It is already checked that the history object works, so no need to check it again.			/TODO history tests	
	}
	
	@Test
	public void SeeEmployeeInformationOneProjectMultipleActivitiesTest() {		
		String activity2Name = "TheSecondActivity";
		try {			
			Calendar startDate = new GregorianCalendar();
			startDate.set(2015, 4, 16);
			Calendar endDate = new GregorianCalendar();
			endDate.set(2016, 7, 18);
			scheduler.getProject(project1Name).forceAddAcitivity(activity2Name, "'Tis the second one", new ArrayList<String>(), startDate, endDate, 1);
			scheduler.getProject(project1Name).getOpenActivityFromName(activity2Name).addEmployee(user1Initials);
		} catch (Exception e) {
			fail();
		}
		Employee Arndt = (Employee) scheduler.getEmployeesContainingString(user1Initials)
				 .stream()
				 .filter(x -> x.getInitials().equals(user1Initials))
				 .toArray()[0];		
		//Checking if the project and activities information is correct - it does not take into account... /TODO
		List<Project> employeesProjects =  Arndt.getProjects();
		assertTrue(employeesProjects.size() == 1);
		assertTrue(employeesProjects.stream().allMatch(x -> (x.getName() == project1Name)));
		List<Activity> employeesActivities = Arndt.getActivities();
		assertTrue(employeesActivities.size() == 2);	
		assertTrue(employeesActivities.stream().allMatch(x -> (x.getName() == activity1Name || x.getName() == activity2Name)));
		
		//History employeesHistory = hopefullyArndt.getHistory(); 
		//It is already checked that the history object works, so no need to check it again.			TODO history tests	
	}

	@Test
	public void SeeEmployeeInformationMultipleProjectsMultipleAcitivitiesTest() {		
		String project2Name = "Navision stat";
		String activity2Name = "TheSecondActivity";
		String activity3Name = "TheThirdAcitvity";
		String activity4Name = "TheFourthAcitvity";
		try {			
			Calendar startDate = new GregorianCalendar();
			startDate.set(2015, 4, 16);
			Calendar endDate = new GregorianCalendar();
			endDate.set(2016, 7, 18);
			scheduler.createProject(project2Name);
			scheduler.getProject(project2Name).addEmployee(user1Initials);
			scheduler.getProject(project1Name).forceAddAcitivity(activity2Name, "'Tis the second one", new ArrayList<String>(), startDate, endDate, 1);
			scheduler.getProject(project2Name).forceAddAcitivity(activity3Name, "'Tis the third one", new ArrayList<String>(), startDate, endDate, 1);
			scheduler.getProject(project2Name).forceAddAcitivity(activity4Name, "'Tis the fourth one", new ArrayList<String>(), startDate, endDate, 1);
			scheduler.getProject(project1Name).getOpenActivityFromName(activity2Name).addEmployee(user1Initials);
			scheduler.getProject(project2Name).getOpenActivityFromName(activity3Name).addEmployee(user1Initials);
			scheduler.getProject(project2Name).getOpenActivityFromName(activity4Name).addEmployee(user1Initials);			
		} catch (Exception e) {
			fail();
		} 				
		Employee Arndt = (Employee) scheduler.getEmployeesContainingString(user1Initials)
				 .stream()
				 .filter(x -> x.getInitials().equals(user1Initials))
				 .toArray()[0];		
		//Checking if the project and activities information is correct - it does not take into account... /TODO
		List<Project> employeesProjects =  Arndt.getProjects();
		assertTrue(employeesProjects.size() == 2);
		assertTrue(employeesProjects.stream().allMatch(x -> (x.getName() == project1Name || x.getName() == project2Name)));
		List<Activity> employeesActivities = Arndt.getActivities();
		assertTrue(employeesActivities.size() == 4);
		assertTrue(employeesActivities.stream().allMatch(x -> (x.getName() == activity1Name && x.getProjectName() == project1Name) ||
																																						(x.getName() == activity2Name && x.getProjectName() == project1Name) ||
																																						(x.getName() == activity3Name && x.getProjectName() == project2Name) ||
																																						(x.getName() == activity4Name && x.getProjectName() == project2Name)));
		//History employeesHistory = hopefullyArndt.getHistory(); 
		//It is already checked that the history object works, so no need to check it again.			/TODO history tests	
	}
	
}
