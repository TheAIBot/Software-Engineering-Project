package Tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.RegisteredTime;
import SoftwareHouse.Scheduler;
import SoftwareHouse.TimePeriod;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeAlreadyAssignedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.IllegalCharException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectManagerNotPartOfEmployeesAdded;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;
import SoftwareHouse.ExceptionTypes.TooManyCharsException;

public class CreateProject {	
	public static Scheduler scheduler;
	private static final String PROJECT_NAME = "Navision Stat";
	private static final String DETAILED_TEXT = "Programfunktionalitet  udarbejdet for den danske stat";
	private static final String COMPANY_NAME = "Det offentlige";
	private static final int BUDGETED_TIME = 42;
	private static final List<Employee> EMPLOYEE_LIST_EMPTY = new ArrayList<>();
	private static final String EMPTY_NAME = "";
	private static final String PROJECT_MANAGER_INITIALS = "JSB";
	private final TimePeriod VALID_TIME_PERIOD;
	private static List<Employee> employeeListWithEmployees;
	
	public CreateProject() {
		try {
			VALID_TIME_PERIOD = new TimePeriod(new GregorianCalendar(2012, 3, 20), new GregorianCalendar(2013, 4, 1));
		} catch (InvalidInformationException e) {
			Assert.fail();
		}
		throw new NullPointerException("VALID_TIME_PERIOD is null");
	}
	
	@Before
	public void setup(){
		scheduler = new Scheduler();	
		try {
			TestTools.addEmployee(scheduler, "JSB");
			TestTools.addEmployee(scheduler,"AGC");
			employeeListWithEmployees = new ArrayList<>();
			employeeListWithEmployees.add(scheduler.getEmployeeFromInitials("JSB"));
			employeeListWithEmployees.add(scheduler.getEmployeeFromInitials("AGC"));
		} catch (MissingInformationException | DuplicateNameException | EmployeeNotFoundException | TooManyCharsException | IllegalCharException e) {
			Assert.fail();
		}
	}

	private void loginIfNotLoggedIn(){
		if (!scheduler.isAnyoneLoggedIn()) {
			try {
				scheduler.login(PROJECT_MANAGER_INITIALS);
			} catch (EmployeeNotFoundException e) {
				Assert.fail();
			}
		}
	}
	
	private boolean testSuccesOnProjectCreation(String projectName, String customerName, String detailedText, 
			                                          List<Employee> employeesToAdd, int budgettedTime, String initialsProjectManager, TimePeriod timePeriod){
		loginIfNotLoggedIn();
		try {
			scheduler.createProject(projectName, customerName, detailedText, 
                    employeesToAdd, budgettedTime, initialsProjectManager, timePeriod);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
		
	@Test
	public void accessProjectTest(){
		TestTools.login(scheduler);
		try {
			assertEquals(scheduler.getProjects().size(), 0);
		} catch (NotLoggedInException e2) {
			Assert.fail();
		}
		try {
			scheduler.createProject(PROJECT_NAME);
			scheduler.createProject("Foo");
			scheduler.createProject("Bar");
		} catch (Exception e) {
			Assert.fail();
		}		
		List<Project> projects = null;
		try {
			projects = scheduler.getProjects();
		} catch (NotLoggedInException e) {
			Assert.fail();
		}
		assertEquals(3, projects.size());
		projects = projects.stream().filter(x -> x.getName().contains("vision")).collect(Collectors.toList());
		assertEquals(1, projects.size());
		try {
			assertEquals(projects.get(0), scheduler.getProject(PROJECT_NAME));
		} catch (Exception e) {
			Assert.fail();
		} 
	}
	
	@Test
	public void createProjectPermissionLoggedInTest(){
		loginIfNotLoggedIn();
		try {
			scheduler.createProject(PROJECT_NAME);
		} catch (Exception e1) {
			Assert.fail();
		}
	}
	
	@Test
	public void createProjectPermissionNotLoggedInTest(){
		try {
			scheduler.createProject(PROJECT_NAME);
			Assert.fail();
		} catch (Exception e1) {
		}
	}
	
	@Test
	public void createProjectAllCorrectInformationAllFilledInTest()
	{
		loginIfNotLoggedIn();
		try {
			scheduler.createProject(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
																			employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD);
		} catch (Exception e1) {
			Assert.fail();
		}
		try {
			assertEquals(scheduler.getProjects().size(), 1);
			Project project = scheduler.getProject(PROJECT_NAME);
			assertEquals(project.getName(), PROJECT_NAME);
			assertEquals(COMPANY_NAME, project.getCostumerName());
			assertTrue(project.getEmployees().stream().allMatch(x -> employeeListWithEmployees.contains(x)));
			assertEquals(employeeListWithEmployees.size(), project.getEmployees().size());
			assertEquals(project.getProjectManager(), scheduler.getEmployeeFromInitials(PROJECT_MANAGER_INITIALS));
			assertEquals(project.getTimePeriod(), VALID_TIME_PERIOD);
			assertEquals(BUDGETED_TIME, project.getBudgetedTime());
		} catch (Exception e1) {
			Assert.fail();
		}
	}
	
	@Test
	public void createProjectMissingNameNotNullNameTest(){
		loginIfNotLoggedIn();
		try {
			scheduler.createProject("", COMPANY_NAME, DETAILED_TEXT, 
					employeeListWithEmployees, 42, "JSB", VALID_TIME_PERIOD);
			Assert.fail();
		} catch (MissingInformationException e){
			assertEquals("Missing project name", "Missing project name");
		} catch (Exception e) {
			Assert.fail(e.getMessage());	
		}	
	}
	
	@Test
	public void createProjectNameIsNullNameTest(){
		TestTools.login(scheduler);
		try {
			scheduler.createProject(null, COMPANY_NAME, DETAILED_TEXT, 
					employeeListWithEmployees, 42, "JSB", VALID_TIME_PERIOD);
			Assert.fail();
		} catch (MissingInformationException e){
			assertEquals("Missing project name", e.getMessage());
		} catch (Exception e) {
			Assert.fail(e.getMessage());	
		}		
	}
	
	@Test
	public void createProjectDuplicateNameTest()
	{
		loginIfNotLoggedIn();
		try {
			scheduler.createProject(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
																			employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD);
		} catch (Exception e1) {
			Assert.fail();
		}
		try {
			scheduler.createProject(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
						employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD);
			Assert.fail();
		} catch (DuplicateNameException e){
			assertEquals("A project with that title already exists", e.getMessage());
		} catch (Exception e) {
			Assert.fail(e.getMessage());	
		}		
	}
	
	@Test
	public void createProjectNegativeBudgetedTime(){
		assertFalse(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
																													  employeeListWithEmployees, -BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
	}
	
	@Test
	public void createProjectImpossibleTimePeriod(){
		TimePeriod impossibleTimePeriod = null;
		try {
			impossibleTimePeriod = new TimePeriod(new GregorianCalendar(2013, 4, 1), new GregorianCalendar(2012, 3, 20));
		} catch (InvalidInformationException e) {
			Assert.fail();
		}
		assertFalse(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, impossibleTimePeriod));
	}
	
	@Test
	public void createProjectNonExistentEmployees(){
		employeeListWithEmployees.add(new Employee(scheduler, "LeLa"));
		assertFalse(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
																													  employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
	}
		
	@Test
	public void createProjectNoDetailedDescription(){
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, "", 
																													  employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
	}
	
	@Test
	public void createProjectDetailedDescriptionIsNull(){
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, null, 
																													  employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
	}
	
	@Test
	public void createProjectNoCostumerName(){
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, "", DETAILED_TEXT, 
																													  employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
	}
	
	@Test
	public void createProjectCustomerNameIsNull(){
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, null, DETAILED_TEXT, 
																													  employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
	}
	
	@Test
	public void createProjectNoBudgetedTime(){
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
																													  employeeListWithEmployees, 0, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
	}
	
	@Test
	public void createProjectNoEmployees(){
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
																													  EMPLOYEE_LIST_EMPTY, BUDGETED_TIME, "", VALID_TIME_PERIOD));
	}
	
	@Test
	public void createProjectEmployeesIsNull(){
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				null, BUDGETED_TIME, "", VALID_TIME_PERIOD)); //There can be no project manager, in this case
	}
	
	@Test
	public void createProjectTimePeriodIsNull(){ 
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, null));
	}
	
	@Test
	public void createProjectNoManagerInitials(){
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				employeeListWithEmployees, BUDGETED_TIME, "", VALID_TIME_PERIOD));
	}
	
	@Test
	public void createProjectManagerInitialsIsNull(){
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				employeeListWithEmployees, BUDGETED_TIME, null, VALID_TIME_PERIOD));
	}
		
	@Test
	public void createProjectNonexistentManagerInitials(){
		assertFalse(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				employeeListWithEmployees, BUDGETED_TIME, "LeLa", VALID_TIME_PERIOD));
	}
	
	@Test
	public void createProjecManagerNotPartOfUserAdded(){
		try {
			scheduler.addEmployee("LeLa");
		} catch (MissingInformationException | DuplicateNameException | TooManyCharsException
				| IllegalCharException e) {
			Assert.fail(e.getMessage());
		}
		assertFalse(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				employeeListWithEmployees, BUDGETED_TIME, "LeLa", VALID_TIME_PERIOD));
	}
	
	@Test
	public void testNoProjectTime()
	{
		TestTools.login(scheduler);
		try {
			TestTools.createProject(scheduler, "Navision Stat");
		} catch (Exception e){
			Assert.fail();
		}
		try {
			List<RegisteredTime> list = scheduler.getTimeVault().getProjectTime("Navision Stat");
			assertEquals(list.size(),0);
		} catch (Exception e){
			Assert.fail();
		}
		
	}
	
	
}
