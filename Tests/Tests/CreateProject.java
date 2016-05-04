package Tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.sun.org.apache.xpath.internal.functions.FuncExtFunctionAvailable;

import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.TimePeriod;
import SoftwareHouse.Tools;
import SoftwareHouse.ExceptionTypes.AlreadyLoggedInException;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidProjectInitilizationInput;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;
import sun.net.www.content.audio.x_aiff;
import SoftwareHouse.ExceptionTypes.MissingInformationException;

public class CreateProject {	
	public static Scheduler scheduler;
	private static final String PROJECT_NAME = "Navision Stat";
	private static final String DETAILED_TEXT = "Programfunktionalitet  udarbejdet for den danske stat";
	private static final String COMPANY_NAME = "Det offentilige";
	private static final int BUDGETED_TIME = 42;
	private static final List<Employee> EMPLOYEE_LIST_EMPTY = new ArrayList<>();
	private static final String EMPTY_NAME = "";
	private static final String PROJECT_MANAGER_INITIALS = "JSB";
	private static final TimePeriod VALID_TIME_PERIOD = new TimePeriod(new GregorianCalendar(2012, 3, 20), new GregorianCalendar(2013, 4, 1));
	private static List<Employee> employeeListWithEmployees;
	
	/*TODO (*)Tilføjelse til blackboxtest
	 *  Alle ting der kan mangle
	 *  Om project manageren er en del af employeesne i gruppen.	
	 *   f
	 *  Andre:
	 *  Not logged in (*) /TODO
	 *  Hvad med løbenummer? (*)
	 *  	wrong manager
	 *  		Time period not filled
	 */ 
	
	@Before
	public void setup(){
		scheduler = new Scheduler();	
		try {
			scheduler.addEmployee("JSB");
			scheduler.addEmployee("AGC");
			employeeListWithEmployees = new ArrayList<>();
			employeeListWithEmployees.add(scheduler.getEmployeeFromInitials("JSB"));
			employeeListWithEmployees.add(scheduler.getEmployeeFromInitials("AGC"));
		} catch (MissingInformationException | DuplicateNameException | EmployeeNotFoundException e) {
			Assert.fail();
		}
	}

	private void loginIfNotLoggedIn(){
		if (!scheduler.isAnyoneLoggedIn()) {
			try {
				scheduler.login(PROJECT_MANAGER_INITIALS);
			} catch (EmployeeNotFoundException e) {
				Assert.fail();
			} catch (AlreadyLoggedInException e) {
				Assert.fail();
			}
		}
	}
	
	private boolean testSuccesOnProjectCreationIsCompleteProjectInformation(String projectName, String companyName, String detailedText, 
			List<Employee> employeesToAdd, int budgettedTime, String initialsProjectManager, TimePeriod timePeriod){
		loginIfNotLoggedIn();
		try {
			if (!Project.isCompleteProjectInformation(scheduler, projectName, companyName, detailedText, 
																													  employeesToAdd, budgettedTime, initialsProjectManager, timePeriod)) {
				return false;
			}
		} catch (NotLoggedInException e1) {
			Assert.fail(); //Should not happen.
			return false;
		}
		return true;
	}
	
	private boolean testSuccesOnProjectCreation(String projectName, String companyName, String detailedText, 
			                                          List<Employee> employeesToAdd, int budgettedTime, String initialsProjectManager, TimePeriod timePeriod){
		loginIfNotLoggedIn();
		try {
			scheduler.createProject(projectName, companyName, detailedText, 
                    employeesToAdd, budgettedTime, initialsProjectManager, timePeriod);
		} catch (InvalidProjectInitilizationInput e) {
			return false;
		} catch (NotLoggedInException e) {
			Assert.fail(); //Should not happen.
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
			if (!Project.isCompleteProjectInformation(scheduler, PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
																													  employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD)) {
				Assert.fail();
			}
			scheduler.createProject(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
																			employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD);
		} catch (Exception e1) {
			Assert.fail();
		}
		try {
			assertEquals(scheduler.getProjects().size(), 1);
			Project project = scheduler.getProject(PROJECT_NAME);
			assertEquals(project.getName(), PROJECT_NAME);
			assertEquals(COMPANY_NAME, project.getCompanyName());
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
			if (Project.isCompleteProjectInformation(scheduler, "", COMPANY_NAME, DETAILED_TEXT, 
																													  employeeListWithEmployees, 42, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD) ||
					 Project.isValidProjectInformation(scheduler, "", COMPANY_NAME, DETAILED_TEXT, 
			                                 employeeListWithEmployees, 42, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD)) {
				Assert.fail();
			}
			try {
				scheduler.createProject("", COMPANY_NAME, DETAILED_TEXT, 
						employeeListWithEmployees, 42, "JSB", VALID_TIME_PERIOD);
				Assert.fail();
			} catch (InvalidProjectInitilizationInput e) {
				//Succes!
			}
		} catch (NotLoggedInException e) {
			Assert.fail();
		} 			
	}
	
	@Test
	public void createProjectNameIsNullNameTest(){
		TestTools.login(scheduler);
		try {
			if (Project.isCompleteProjectInformation(scheduler, null, COMPANY_NAME, DETAILED_TEXT, 
																													  employeeListWithEmployees, 42, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD) ||
					 Project.isValidProjectInformation(scheduler, null, COMPANY_NAME, DETAILED_TEXT, 
			                                 employeeListWithEmployees, 42, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD)) {
				Assert.fail();
			}
			try {
				scheduler.createProject(null, COMPANY_NAME, DETAILED_TEXT, 
						employeeListWithEmployees, 42, "JSB", VALID_TIME_PERIOD);
				Assert.fail();
			} catch (InvalidProjectInitilizationInput e) {
				//Succes!
			}
		} catch (NotLoggedInException e) {
			Assert.fail();
		} 			
	}
	
	@Test
	public void createProjectDuplicateNameTest()
	{
		loginIfNotLoggedIn();
		try {
			if (!Project.isCompleteProjectInformation(scheduler, PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
																													  employeeListWithEmployees, 42, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD)) {
				Assert.fail();
			}
			scheduler.createProject(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
																			employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD);
		} catch (Exception e1) {
			Assert.fail();
		}
		try {
			if (!Project.isCompleteProjectInformation(scheduler, PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
					  employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD)) {
				scheduler.createProject(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
						employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD);
				Assert.fail();
			}
			Assert.fail();
		} catch (InvalidProjectInitilizationInput e) {
			//Succes!
		} catch (NotLoggedInException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void createProjectNegativeBudgetedTime(){
		assertFalse(testSuccesOnProjectCreationIsCompleteProjectInformation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				  employeeListWithEmployees, -BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
		assertFalse(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
																													  employeeListWithEmployees, -BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
	}
	
	@Test
	public void createProjectImpossibleTimePeriod(){
		TimePeriod impossibleTimePeriod = new TimePeriod(new GregorianCalendar(2013, 4, 1), new GregorianCalendar(2012, 3, 20));
		assertFalse(testSuccesOnProjectCreationIsCompleteProjectInformation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				  employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, impossibleTimePeriod));
		assertFalse(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
																													  employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, impossibleTimePeriod));
	}
	
	@Test
	public void createProjectNonExistentEmployees(){
		employeeListWithEmployees.add(new Employee(scheduler, "LeLa"));
		assertFalse(testSuccesOnProjectCreationIsCompleteProjectInformation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				  employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
		assertFalse(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
																													  employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
	}
		
	@Test
	public void createProjectNoDetailedDescription(){
		assertFalse(testSuccesOnProjectCreationIsCompleteProjectInformation(PROJECT_NAME, COMPANY_NAME, "", 
				  employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, "", 
																													  employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
	}
	
	@Test
	public void createProjectDetailedDescriptionIsNull(){
		assertFalse(testSuccesOnProjectCreationIsCompleteProjectInformation(PROJECT_NAME, COMPANY_NAME, null, 
				  employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, null, 
																													  employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
	}
	
	@Test
	public void createProjectNoCompanyName(){
		assertFalse(testSuccesOnProjectCreationIsCompleteProjectInformation(PROJECT_NAME, "", DETAILED_TEXT, 
				  employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, "", DETAILED_TEXT, 
																													  employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
	}
	
	@Test
	public void createProjectCompanyNameIsNull(){
		assertFalse(testSuccesOnProjectCreationIsCompleteProjectInformation(PROJECT_NAME, null, DETAILED_TEXT, 
				  employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, null, DETAILED_TEXT, 
																													  employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
	}
	
	@Test
	public void createProjectNoBudgetedTime(){
		assertFalse(testSuccesOnProjectCreationIsCompleteProjectInformation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				  employeeListWithEmployees, 0, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
																													  employeeListWithEmployees, 0, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
	}
	
	@Test
	public void createProjectNoEmployees(){
		assertFalse(testSuccesOnProjectCreationIsCompleteProjectInformation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				  EMPLOYEE_LIST_EMPTY, BUDGETED_TIME, "", VALID_TIME_PERIOD)); //There can be no project manager, in this case, as there are no people employees to chose from.
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
																													  EMPLOYEE_LIST_EMPTY, BUDGETED_TIME, "", VALID_TIME_PERIOD));
	}
	
	@Test
	public void createProjectEmployeesIsNull(){
		assertFalse(testSuccesOnProjectCreationIsCompleteProjectInformation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				  null, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				null, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD));
	}
	
	@Test
	public void createProjectTimePeriodIsNull(){ 
		assertFalse(testSuccesOnProjectCreationIsCompleteProjectInformation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, null));
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, null));
	}
	
	@Test
	public void createProjectNoManagerInitials(){
		assertFalse(testSuccesOnProjectCreationIsCompleteProjectInformation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				employeeListWithEmployees, BUDGETED_TIME, "", VALID_TIME_PERIOD));
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				employeeListWithEmployees, BUDGETED_TIME, "", VALID_TIME_PERIOD));
	}
	
	@Test
	public void createProjectManagerInitialsIsNull(){
		assertFalse(testSuccesOnProjectCreationIsCompleteProjectInformation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				employeeListWithEmployees, BUDGETED_TIME, null, VALID_TIME_PERIOD));
		assertTrue(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				employeeListWithEmployees, BUDGETED_TIME, null, VALID_TIME_PERIOD));
	}
		
	@Test
	public void createProjectNonexistentManagerInitials(){
		assertFalse(testSuccesOnProjectCreationIsCompleteProjectInformation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
				employeeListWithEmployees, BUDGETED_TIME, "LeLa", VALID_TIME_PERIOD));
		assertFalse(testSuccesOnProjectCreation(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, //True? TODO
				employeeListWithEmployees, BUDGETED_TIME, "LeLa", VALID_TIME_PERIOD));
	}
	
	
}
