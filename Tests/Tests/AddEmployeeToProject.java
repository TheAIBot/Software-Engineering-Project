package Tests;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import SoftwareHouse.Employee;
import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;

public class AddEmployeeToProject {
	Scheduler scheduler;
	private final String EMPLOYEE_1_INITIALS = "AM";
	private final String EMPLOYEE_2_INITIALS = "JSB";
	private final String PROJECT_NAME = "15-puzzle-spil";
	private Project project1;
	private Employee employee1;
	private Employee employee2;
	
	//TODO (*) Change
	// Add to different projects.
	// (*) rewrite blackbox tests completly
	//One cannot, by implementation, add an employee to an non-existing project. 
	//The only way to add an employee to a project, is through the project.
	
	@Before
	public void setup(){
		scheduler = new Scheduler();
		TestTools.login(scheduler);
		try {
			scheduler.addEmployee(EMPLOYEE_1_INITIALS);
			scheduler.addEmployee(EMPLOYEE_2_INITIALS);			
			employee1 = scheduler.getEmployeeFromInitials(EMPLOYEE_1_INITIALS);
			employee2 = scheduler.getEmployeeFromInitials(EMPLOYEE_2_INITIALS);
			scheduler.createProject(PROJECT_NAME);
			project1 = scheduler.getProject(PROJECT_NAME);
			//Asserts prior to test.
			assertTrue(project1.getEmployees().size() == 0);
			assertTrue(employee1.getProjects().size() == 0);
			} catch (Exception e) {
			Assert.fail(e.getMessage());
		}		
	}
	
	@Test
	public void addEmployeeToProject1EmployeeExisting(){
		assertTrue(project1.addEmployee(EMPLOYEE_1_INITIALS));
		assertTrue(project1.getEmployees().size() == 1);
		assertTrue(employee1.getProjects().size() == 1);
		assertTrue(project1.getEmployees().get(0).getInitials() == EMPLOYEE_1_INITIALS);
		assertTrue(employee1.getProjects().get(0).getName() == PROJECT_NAME);
	}
	
	@Test
	public void addEmployeeToProject1EmployeeNonExisting(){
		assertFalse(project1.addEmployee("LeLa"));
		assertTrue(project1.getEmployees().size() == 0);
		assertTrue(employee1.getProjects().size() == 0);
	}
	
	@Test
	public void addEmployeeToProject1EmployeeAlreadyPart(){
		assertTrue(project1.addEmployee(EMPLOYEE_1_INITIALS));
		assertFalse(project1.addEmployee(EMPLOYEE_1_INITIALS));
		assertTrue(project1.getEmployees().size() == 1);
		assertTrue(employee1.getProjects().size() == 1);
		assertTrue(project1.getEmployees().get(0).getInitials() == EMPLOYEE_1_INITIALS);
		assertTrue(employee1.getProjects().get(0).getName() == PROJECT_NAME);
	}
	
	@Test
	public void addEmployeeToProject2EmployeesExisting(){
		assertTrue(project1.addEmployee(EMPLOYEE_1_INITIALS));
		assertTrue(project1.addEmployee(EMPLOYEE_2_INITIALS));
		assertTrue(project1.getEmployees().size() == 2);
		assertTrue(employee1.getProjects().size() == 1);
		assertTrue(employee2.getProjects().size() == 1);
		assertTrue(employee1.getProjects().contains(project1));
		assertTrue(employee2.getProjects().contains(project1));
		assertTrue(project1.getEmployees().contains(employee1));
		assertTrue(project1.getEmployees().contains(employee2));
	}
	

	@Test
	public void addEmployeeToProject2EmployeesAlreadyPart(){
		assertTrue(project1.addEmployee(EMPLOYEE_1_INITIALS));
		assertTrue(project1.addEmployee(EMPLOYEE_2_INITIALS));
		assertFalse(project1.addEmployee(EMPLOYEE_1_INITIALS));
		assertFalse(project1.addEmployee(EMPLOYEE_2_INITIALS));
		assertTrue(project1.getEmployees().size() == 2);
		assertTrue(employee1.getProjects().size() == 1);
		assertTrue(employee2.getProjects().size() == 1);
		assertTrue(employee1.getProjects().contains(project1));
		assertTrue(employee2.getProjects().contains(project1));
		assertTrue(project1.getEmployees().contains(employee1));
		assertTrue(project1.getEmployees().contains(employee2));
	}
	
	@Test
	public void addEmployeeToProjectPartOfOtherProjects(){
		try {
			//Source - GloryHammer
			
			//Other projects
			scheduler.createProject("Tales From the Kingdom of Fife");
			scheduler.createProject("Space 1992: Rise Of the Chaos Wizards");
			//Other employees
			scheduler.addEmployee("AMF"); //Angus McFife
			scheduler.addEmployee("ZT"); //Zargothrax
			scheduler.addEmployee("GB"); //Goblins
			scheduler.addEmployee("UUC"); //Undead unicorns
			scheduler.addEmployee("HM"); //(The Hollywood) Hootsman
			scheduler.addEmployee("RT"); //The hermit Ralathor
			scheduler.addEmployee("CW"); //Chaos wizards
			scheduler.addEmployee("AMFT"); //Angus McFife the Thirteenth
			
			//Setup
			Project talesProject = scheduler.getProject("Tales From the Kingdom of Fife");
			Project spaceProject = scheduler.getProject("Space 1992: Rise Of the Chaos Wizards");
			
			talesProject.addEmployee("AMF");
			talesProject.addEmployee("ZT");
			talesProject.addEmployee("GB");
			talesProject.addEmployee("UUC");
			talesProject.addEmployee("HM");
			talesProject.addEmployee("RT");
			talesProject.addEmployee(EMPLOYEE_1_INITIALS);
			
			spaceProject.addEmployee("AMFT");
			spaceProject.addEmployee("ZT");
			spaceProject.addEmployee("CW");
			spaceProject.addEmployee("HM");
			spaceProject.addEmployee("RT");			
			spaceProject.addEmployee(EMPLOYEE_1_INITIALS);
			
			List<String> invasionArmy = Arrays.asList("AMF","ZT", "GB", "UUC",EMPLOYEE_1_INITIALS);			
			List<String> epicBattleArmy = Arrays.asList("AMF","ZT","HM", "RT", EMPLOYEE_1_INITIALS);
			List<String> chaosArmy = Arrays.asList("AMFT","ZT","CW", EMPLOYEE_1_INITIALS);
			List<String> hootsArmy = Arrays.asList("HM", EMPLOYEE_1_INITIALS);
			List<String> apocalypseArmy = Arrays.asList("AMFT","ZT","CW","RT","HM", EMPLOYEE_1_INITIALS);
			
			talesProject.addAcitivity("The Unicorn Invasion of Dundee", "They came with the first light of dawn, setting their sights on Dundee",
					invasionArmy, new GregorianCalendar(992,3,3),  new GregorianCalendar(992,3,4), 18);
			talesProject.addAcitivity(	"The Epic Rage of Furious Thunder", "The final battle has begun, for freedom and Dundee", epicBattleArmy,
					 new GregorianCalendar(992,12,31),  new GregorianCalendar(993,1,1), 32);
			spaceProject.addAcitivity(	"Rise Of the Chaos Wizards", "Across the galaxy, a new force of evil is rising, Wizards of Chaos fighting the throne of brave king of Dundee"
					, chaosArmy, new GregorianCalendar(1992,3,3), new GregorianCalendar(1992,3,4), 7);
			spaceProject.addAcitivity(	"Hollywood Hootsman", "Immortal warrior with armor made from wolf, His legend proves the centuries with the power of the hoots!"
					, hootsArmy, new GregorianCalendar(1992,4,3), new GregorianCalendar(1992,4,4), 12);
			spaceProject.addAcitivity(	"Apocalypse 1992", "The countdown to universal annihilation had begun"
					, apocalypseArmy, new GregorianCalendar(1992,12,31), new GregorianCalendar(1993,1,1), 18);		
			
			//Actual tests
			assertTrue(project1.addEmployee(EMPLOYEE_1_INITIALS));
			assertTrue(employee1.getProjects().size() == 3);
			assertTrue(employee1.getProjects().contains(project1));
			assertTrue(employee1.getProjects().contains(talesProject));
			assertTrue(employee1.getProjects().contains(spaceProject));
			assertTrue(project1.getEmployees().contains(employee1));
			assertTrue(scheduler.getProject("Tales From the Kingdom of Fife").getEmployees().contains(employee1));
			assertTrue(scheduler.getProject("Space 1992: Rise Of the Chaos Wizards").getEmployees().contains(employee1));			
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	
	
	
	@Test
	public void AddEmployeeSuccessTest()
	{
		Scheduler scheduler = new Scheduler();
		TestTools.login(scheduler);
		try {
			scheduler.createProject("Derp");
		} catch (Exception e) {
			Assert.fail();
		}
		Project project = null;
		try {
			project = scheduler.getProject("Derp");
		} catch (Exception e) {
			Assert.fail();
		}
		
		try {
			scheduler.addEmployee("LSB");
		} catch (Exception e) {
			Assert.fail();
		}
		try {
			project.addEmployee("LSB");
		} catch (Exception e) {
			Assert.fail();
		}
		assertEquals(project.getEmployees().size(), 1);
		
		Employee employee = null;
		try {
			employee = scheduler.getEmployeeFromInitials("LSB");
		} catch (Exception e) {
			Assert.fail();
		}
		
		assertEquals(employee.getProjects().size(), 1);
		assertEquals(employee.getProjects().get(0).getName(), "Derp");
	}
	
	@Test
	public void AddEmployeeIncorrectInitialsTest()
	{
		Scheduler scheduler = new Scheduler();
		TestTools.login(scheduler);
		try {
			scheduler.createProject("Derp");
		} catch (Exception e) {
			Assert.fail();
		}
		Project project = null;
		try {
			project = scheduler.getProject("Derp");
		} catch (Exception e) {
			Assert.fail();
		}

		assertFalse(project.addEmployee("LSB"));
	}
	
	
	/**
	 * Test case: Employee does not exists in the internal system
	 */
	@Test
	public void AddEmployeeNotExisting() {
		Scheduler scheduler = new Scheduler();
		TestTools.login(scheduler);
		try {
			scheduler.createProject("Battlefield 1");
		} catch (Exception e) {
			Assert.fail();
		}
		Project project = null;
		try {
			project = scheduler.getProject("Battlefield 1");
		} catch (Exception e) {
			Assert.fail();
		}
		
		Employee employeeX = null;
		try {
			employeeX = scheduler.getEmployeeFromInitials("XXXX");
			Assert.fail();
		} catch (Exception e) {
			assertEquals("No employee with those initials exists", e.getMessage());
		}
		
		assertFalse(project.addEmployee("XXXX"));
		assertEquals(0, project.getEmployees().size());

	}
	
}
