package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.validator.PublicClassValidator;

import SoftwareHouse.Employee;
import SoftwareHouse.Project;
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
import SoftwareHouse.ExceptionTypes.TooManyCharsException;

/**
 * @author Niklas
 */
public class VariousProjectTests {

	public static Scheduler scheduler;
	private static final String PROJECT_NAME = "Navision Stat";
	private static final String DETAILED_TEXT = "Programfunktionalitet  udarbejdet for den danske stat";
	private static final String COMPANY_NAME = "Det offentlige";
	private static final int BUDGETED_TIME = 42;
	private static final List<Employee> EMPLOYEE_LIST_EMPTY = new ArrayList<>();
	private static final String EMPTY_NAME = "";
	private static final String PROJECT_MANAGER_INITIALS = "JSB";
	private TimePeriod VALID_TIME_PERIOD ;
	private static List<Employee> employeeListWithEmployees;
	

	/**
	 * Niklas
	 */
	@Before
	public void setup(){
		scheduler = new Scheduler();	
		TestTools.login(scheduler);
		try {
			VALID_TIME_PERIOD = new TimePeriod(new GregorianCalendar(2012, 3, 20), new GregorianCalendar(2013, 4, 1));
		} catch (InvalidInformationException e) {
			Assert.fail();
			throw new NullPointerException("VALID_TIME_PERIOD is null");
		}
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

	/**
	 * Niklas
	 */
	@Test
	public void testValidateInformationSchedulerIsNull(){
		try {
			new Project(null, "The best project", false);
		} catch (Exception e) {
			assertEquals("Scheduler can't be null",e.getMessage());
		}
	}

	/**
	 * Niklas
	 */
	@Test
	public void testProperProjectManagerEmployeesIsNull(){
		try {
			scheduler.createProject(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
																			null, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD);
			Assert.fail();
		} catch (Exception e) {
			assertEquals("The given manager JSB is not a part of the list of employees given.",e.getMessage());
		}
	}

	/**
	 * Niklas
	 */
	@Test
	public void testAddEmployee(){
		try {
			scheduler.createProject(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
																			employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD);
			scheduler.addEmployee("DIO");
			Employee employee = scheduler.getEmployeeFromInitials("DIO");
			Project project = scheduler.getProject(PROJECT_NAME);
			project.addEmployee(employee.getInitials());	
			employee.getProjects().remove(0);
			project.addEmployee(employee.getInitials());				
			Assert.fail();
		} catch (Exception e) {
			assertEquals("DIO" + " is already a part of the project " + "Navision Stat",e.getMessage());
		}
	}

	/**
	 * Niklas
	 */
	@Test
	public void testHasPermissiontoEditProjectManagerIsNull(){
		try {
			scheduler.createProject(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
					employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD);
			scheduler.login(PROJECT_MANAGER_INITIALS);
			scheduler.getProject(PROJECT_NAME).setProjectManager(null);
			scheduler.getProject(PROJECT_NAME).setProjectManager("JSB");
		} catch (Exception e) {
			Assert.fail();
		} 
	}
	
	
	/**
	 * Niklas
	 */
	@Test
	public void testValidatePermissionNameIsNull(){
		try {
			scheduler.createProject(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
					employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD);
			scheduler.login(PROJECT_MANAGER_INITIALS);
			scheduler.getProject(PROJECT_NAME).validateinformation(scheduler, "", BUDGETED_TIME, null, 
																																								VALID_TIME_PERIOD, new ArrayList<Employee>(), new ArrayList<String>());
		} catch(MissingInformationException e){
			assertEquals("Missing project name", e.getMessage());
		} catch (Exception e) {		
			Assert.fail();
		} 
	}
	
	/**
	 * Niklas
	 */
	@Test
	public void testGetProjectNumber(){
		try {
			scheduler.createProject(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
					employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD);
			scheduler.login(PROJECT_MANAGER_INITIALS);
			scheduler.getProject(PROJECT_NAME).getProjectNumber();
			} catch(MissingInformationException e){
			assertEquals("Missing project name", e.getMessage());
		} catch (Exception e) {		
			Assert.fail();
		} 
	}
	
	/**
	 * Niklas
	 */
	@Test
	public void testChangeProjectNameSame(){
		try {
			scheduler.createProject(PROJECT_NAME, COMPANY_NAME, DETAILED_TEXT, 
					employeeListWithEmployees, BUDGETED_TIME, PROJECT_MANAGER_INITIALS, VALID_TIME_PERIOD);
			scheduler.login(PROJECT_MANAGER_INITIALS);
			scheduler.getProject(PROJECT_NAME).setName(PROJECT_NAME);;
			} catch (Exception e) {		
			Assert.fail();
		} 
	}
	
	
	
	
}
