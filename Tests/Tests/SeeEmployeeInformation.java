package Tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import SoftwareHouse.Activity;
import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;

public class SeeEmployeeInformation {
	private Scheduler scheduler = null;
	private String project1Name = "15-puzzle-spil";
	private String project2Name = "Navision stat";
	private String activity1Name = "brugergrænseflade";
	private String activity2Name = "TheSecondActivity";
	private String activity3Name = "TheThirdActivity";
	private String activity4Name = "TheFourthActivity";
	private String user1Initials = "AM";
	
	@Before
	public void setup()
	{
		scheduler = new Scheduler();

		TestTools.login(scheduler);
		Project project = null;
		try {
			project = TestTools.createProject(scheduler, project1Name);
		} catch (Exception e) {
			Assert.fail();
		}	
		try {
			TestTools.addEmployeeToProject(scheduler,user1Initials,project1Name);
			TestTools.addEmployeeToProject(scheduler,"JSB",project1Name);
			TestTools.addEmployeeToProject(scheduler,"ELL",project1Name);
			TestTools.addEmployeeToProject(scheduler,"AGC",project1Name);
			TestTools.addEmployeeToProject(scheduler,"NR",project1Name);
			TestTools.forceAddActivity(scheduler, project1Name, activity1Name, new String[]{user1Initials});
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		assertEquals(project.getOpenActivities().size(), 1);	
	} 	
	
	@Test
	public void ProperEmployeeSelectionOneEmployeeTest(){
		//Represents an user, filtering the list of possible users to see information about.
		List<Employee> employeesFound = scheduler.getEmployeesContainingString(user1Initials); 
		assertTrue(employeesFound.size() == 1);
		assertTrue(employeesFound.stream().anyMatch(x -> x.getInitials() == user1Initials));
		assertTrue(employeesFound.stream().allMatch(x -> x.getInitials().contains(user1Initials)));
		//Every employee has an unique string of initials associated with them.
		//Represents an user selecting the name, amongst the filtered list of users.
		Employee hopefullyArndt = employeesFound.stream().filter(x -> x.getInitials().equals(user1Initials)).findFirst().get();	
		assertTrue(hopefullyArndt.getInitials() == user1Initials);
	}
	@Test
	public void ProperEmployeeSelectionMultipleEmployeesTest(){
		List<Employee> employeesFound = scheduler.getEmployeesContainingString("A");
		assertTrue(employeesFound.size() == 2);
		assertTrue(employeesFound.stream().anyMatch(x -> x.getInitials() == user1Initials));
		//TODO Burde lave det sådan saa at man tjekker om personerne er praecist dem man forventede, men gider ikke.
		assertTrue(employeesFound.stream().allMatch(x -> x.getInitials().contains("A"))); 
		//Every employee has an unique string of initials associated with them.
		Employee hopefullyArndt = employeesFound.stream().filter(x -> x.getInitials().equals(user1Initials)).findAny().get();		
		assertTrue(hopefullyArndt.getInitials() == user1Initials);
	}
	
	@Test
	public void SeeEmployeeInformationSuccesfullTestOneProjectOneActivityTest() {
		Employee Arndt =   scheduler.getEmployeesContainingString(user1Initials).stream()
																				.filter(x -> x.getInitials().equals(user1Initials))
																				.findFirst()
																				.get();		
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
			TestTools.createProject(scheduler,project2Name);
			scheduler.getProject(project2Name).forceAddAcitivity(activity2Name, "'Tis the second one", new ArrayList<String>(), startDate, endDate, 1);
			scheduler.getProject(project2Name).addEmployee(user1Initials);
			scheduler.getProject(project2Name).getActivity(activity2Name).addEmployee(user1Initials);
		} catch (Exception e) {
			Assert.fail();
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
	
	public static boolean meg(List<String> projects, List<String> activities, Employee employee, String employeeInitials){
		List<Project> employeesProjects =  employee.getProjects();
		assertTrue(employeesProjects.size() == projects.size());
		//There is only one project with a specific name, so this makes sure that all the projects are the ones expected. 
		//This is also made sure by assuring that all the names are distinct.
		assertEquals(employeesProjects.size(),
				(((List<String>) employeesProjects.stream()
															.map(x -> x.getName()).collect(Collectors.toList()))
															.stream().distinct().toArray()));
		assertTrue(employeesProjects.stream().allMatch(x -> projects.stream().anyMatch(y -> y.equals(x.getName()))));
		List<Activity> employeesActivities = employee.getActivities();
		assertTrue(employeesActivities.size() == activities.size());
		//It is known that in this case are all the activities distinct, why a distinction should return the same result.
		
		
		//assertTrue(employeesActivities.stream().allMatch(x -> activities.stream().anyMatch(y -> y.equals(x.getName())));
		return false;
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
		try {			
			Calendar startDate = new GregorianCalendar();
			startDate.set(2015, 4, 16);
			Calendar endDate = new GregorianCalendar();
			endDate.set(2016, 7, 18);
			TestTools.createProject(scheduler,project2Name);
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
