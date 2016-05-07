package Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.*;
import org.junit.Before;
import org.junit.Test;

import com.sun.javafx.runtime.VersionInfo;

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

public class EditProject {
	private Scheduler scheduler;
	private Project project;
	private static final String EMPLOYEE1 = "JSB";
	private static final String EMPLOYEE2 = "ASB";
	private static final String START_PROJECT_NAME = "Kings of metal";
	private static final String NEW_PROJECT_NAME = "Core";
	private static final String START_COSTUMER_NAME = "ManoWar";
	private static final String NEW_COSTUMER_NAME = "Personefone";
	private static final String START_DETAILED_DESCRIPTION = "The kings of metal";
	private static final String NEW_DETAILED_DESCRIPTION = "Absolute perfection";
	private List<Employee> employeesToAdd;
	private static final int START_BUDGETTED_TIME = 120;
	private static final int NEW_BUDGETTED_TIME = 12000;
	private static final String START_INITIALS_PROJECT_MANAGER = "DIO";
	private static final String NEW_INITIALS_PROJECT_MANAGER = "JoJo";
	private static final TimePeriod START_TIME_PERIOD = new TimePeriod(new GregorianCalendar(2012,2,10), new GregorianCalendar(2012,2,11));
	
	//add test where we add bugetted time and verify it fd
	//add test where we add detailed text to project and verify it
	//add test where the project name is set to null or empty or to a name that already xists
	
	@Before
	public void setup(){
		try {
			scheduler = new Scheduler();
			TestTools.login(scheduler);
			scheduler.addEmployee(EMPLOYEE1);
			scheduler.addEmployee(EMPLOYEE2);
			scheduler.addEmployee(START_INITIALS_PROJECT_MANAGER);
			employeesToAdd = new ArrayList<Employee>();
			employeesToAdd.add(scheduler.getEmployeeFromInitials(EMPLOYEE1));
			employeesToAdd.add(scheduler.getEmployeeFromInitials(EMPLOYEE2));
			employeesToAdd.add(scheduler.getEmployeeFromInitials(START_INITIALS_PROJECT_MANAGER));
			scheduler.createProject(START_PROJECT_NAME, START_COSTUMER_NAME, START_DETAILED_DESCRIPTION, 
					employeesToAdd, START_BUDGETTED_TIME, START_INITIALS_PROJECT_MANAGER, START_TIME_PERIOD);
			project = scheduler.getProject(START_PROJECT_NAME);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}		
	}
	
	@Test
	public void testChangeNameToNull(){
		try {
			project.setName(null);
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail(e.getMessage());
		} catch (MissingInformationException e) {
			assertEquals("No name was specified",e.getMessage());
			assertEquals(START_PROJECT_NAME, project.getName());
		}
	}
	
	@Test
	public void testChangeNameToEmpty(){
		try {
			project.setName("");
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail(e.getMessage());
		} catch (MissingInformationException e) {
			assertEquals("No name was specified",e.getMessage());
			assertEquals(START_PROJECT_NAME, project.getName());
		}
	}
	
	@Test
	public void testChangeNameToDuplicateName(){
		try {
			scheduler.createProject(NEW_PROJECT_NAME);
		} catch (MissingInformationException | DuplicateNameException | NotLoggedInException
				| InvalidInformationException | EmployeeNotFoundException | EmployeeAlreadyAssignedException
				| ProjectManagerNotPartOfEmployeesAdded e1) {
			Assert.fail(e1.getMessage());
		}
		try {
			project.setName(NEW_PROJECT_NAME);
			Assert.fail();
		} catch (DuplicateNameException e) {
			assertEquals("A project with that name already exist",e.getMessage());
			assertEquals(START_PROJECT_NAME, project.getName());
		} catch (MissingInformationException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testChangeNameToNewName(){
		try {
			project.setName(NEW_PROJECT_NAME);
		} catch (DuplicateNameException e) {
			Assert.fail(e.getMessage());
		} catch (MissingInformationException e) {
			Assert.fail(e.getMessage());
		}
		assertEquals(NEW_PROJECT_NAME, project.getName());
	}
	
	@Test
	public void testChangeDetailedDescriptionToNewDetailedDescription(){
		project.setDetailedText(NEW_DETAILED_DESCRIPTION);
		assertEquals(NEW_DETAILED_DESCRIPTION, project.getDetailedText());
	}
	
	@Test
	public void testChangeCostumerToNewCostumer(){
		project.setCostumerName(NEW_DETAILED_DESCRIPTION);
		assertEquals(NEW_DETAILED_DESCRIPTION, project.getCostumerName());
	}
	
	@Test
	public void testChangeBudgettedTimeToNegativeTime(){
		try {
			project.setBudgetedTime(-1);
			Assert.fail();
		} catch (InvalidInformationException e) {
			assertEquals("Budgetted time can't be less than 0",e.getMessage());
			assertEquals(START_BUDGETTED_TIME, project.getBudgettedTime());
		}
	}
	
	@Test
	public void testChangeBudgettedTimeToNewTime(){
		try {
			project.setBudgetedTime(NEW_BUDGETTED_TIME);
		} catch (InvalidInformationException e) {
			Assert.fail(e.getMessage());
		}
		assertEquals(NEW_BUDGETTED_TIME, project.getBudgettedTime());
	}
	
	@Test
	public void testChangeProjectManagerWithNull(){
		try {
			project.setProjectManager(null);
		} catch (ProjectManagerNotPartOfEmployeesAdded e) {
			Assert.fail(e.getMessage());
		}
		assertEquals(null, project.getProjectManager());
	}
	
	@Test
	public void testChangeProjectManagerWithEmptyString(){
		try {
			project.setProjectManager("");
		} catch (ProjectManagerNotPartOfEmployeesAdded e) {
			Assert.fail(e.getMessage());
		}
		assertEquals(null, project.getProjectManager());
	}
	
	@Test
	public void testChangeProjectManagerToEmployeeNotPartOfTheProject(){
		//It does not matter if the employee exists or not.
		try {
			project.setProjectManager(NEW_INITIALS_PROJECT_MANAGER);
			Assert.fail();
		} catch (ProjectManagerNotPartOfEmployeesAdded e) {
			assertEquals("The project manager that is trying to be assignes, " + 
			NEW_INITIALS_PROJECT_MANAGER + " is not part of the project", e.getMessage());
			assertEquals(START_INITIALS_PROJECT_MANAGER, project.getProjectManager().getInitials());
		}
	}
	
	@Test
	public void testChangeProjectManagerToEmployeePartOfTheProject(){
		try {
			scheduler.addEmployee(NEW_INITIALS_PROJECT_MANAGER);
			project.addEmployee(NEW_INITIALS_PROJECT_MANAGER);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		try {
			project.setProjectManager(NEW_INITIALS_PROJECT_MANAGER);
			assertEquals(NEW_INITIALS_PROJECT_MANAGER, project.getProjectManager().getInitials());
		} catch (ProjectManagerNotPartOfEmployeesAdded e) {
			Assert.fail(e.getMessage());
		}
	}
	
	/*TODO Se paa TimePeriod efter ændringerne(*).
	@Test
	public void testChangeTimePeriodToNull(){
			
	}
	*/
	
	@Test
	public void testChangeListOfEmployeesToNull(){
		try {
			project.setEmployees(null);
		} catch (InvalidInformationException e) {
			Assert.fail(e.getMessage());
		}
		assertTrue(project.getEmployees().size() == 0);
	}
	
	@Test
	public void testChangeListOfEmployeesToEmptyList(){
		try {
			project.setEmployees(new ArrayList<Employee>());
		} catch (InvalidInformationException e) {
			Assert.fail(e.getMessage());
		}
		assertTrue(project.getEmployees().size() == 0);
	}
	
	@Test
	public void testChangeListOfEmployeesToListContainingNonExistingEmployees(){
		try {
			List<Employee> newEmployees = new ArrayList<Employee>();
			newEmployees.add(scheduler.getEmployeeFromInitials(START_INITIALS_PROJECT_MANAGER));
			 //To create an employee who is not registrated by the Scheduler, it is neccesary to make a new one.
			newEmployees.add(new Employee(new Scheduler(), NEW_INITIALS_PROJECT_MANAGER));
			project.setEmployees(newEmployees);
			Assert.fail();
		} catch (Exception e) {
			assertEquals("There cannot be added employees to a project, when some of them do not exist", e.getMessage());
		}
		assertEquals(project.getEmployees().size(),employeesToAdd.size());
		try {
			for (Employee employee : employeesToAdd) {
				assertTrue(scheduler.getEmployeeFromInitials(employee.getInitials()).equals(employee.getInitials()));
			}
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void testChangeListOfEmployeesToListContainingExistingEmployees(){
		try {
			scheduler.addEmployee(NEW_INITIALS_PROJECT_MANAGER);
			List<Employee> newEmployees = new ArrayList<Employee>();
			newEmployees.add(scheduler.getEmployeeFromInitials(START_INITIALS_PROJECT_MANAGER));
			newEmployees.add(scheduler.getEmployeeFromInitials(NEW_INITIALS_PROJECT_MANAGER));
			project.setEmployees(newEmployees);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		assertTrue(project.getEmployees().size() == 2);
		try {
			assertTrue(project.getEmployees().contains(scheduler.getEmployeeFromInitials(START_INITIALS_PROJECT_MANAGER)));
			assertTrue(project.getEmployees().contains(scheduler.getEmployeeFromInitials(NEW_INITIALS_PROJECT_MANAGER)));
		} catch (EmployeeNotFoundException e) {
			Assert.fail(e.getMessage());
		}
	}
	
	
	
}
