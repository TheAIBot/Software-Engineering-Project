package Tests;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import SoftwareHouse.Employee;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.EmployeeAlreadyAssignedException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;

public class AbsenseActivity {
	
	@Test
	public void AbsenseActivityAddEmployeeSuccess()
	{
		Scheduler scheduler = new Scheduler();
		TestTools.login(scheduler);
		
		try {
			Employee batman = TestTools.addEmployee(scheduler, "BW");
			TestTools.addEmployeeToProject(scheduler, batman.getInitials(), scheduler.getAbsenceProject().getName());
			scheduler.getAbsenceProject().getOpenActivities().get(0).addEmployee(batman.getInitials());
			batman.registerTime(scheduler.getAbsenceProject().getName(), 
								scheduler.getAbsenceProject().getOpenActivities().get(0).getName(), 
								"killed superman", 
								1337);
			assertEquals(scheduler.getTimeVault()
								  .getActivityTime(scheduler.getAbsenceProject()
										  					.getName(), 
						 						   scheduler.getAbsenceProject()
						 						   			.getOpenActivities()
						 						   			.get(0)
						 						   			.getName())
								  .size(), 
						 1);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}	
	}

	@Test
	public void AbsenseActivityAddInvalidEmployee()
	{
		Scheduler scheduler = new Scheduler();
		TestTools.login(scheduler);
		
		try {
			Employee batman = TestTools.addEmployee(scheduler, "BW");
			scheduler.getAbsenceProject().getOpenActivities().get(0).addEmployee("BBB");
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			assertEquals("Employee does not exists or is not part of this project", e.getMessage());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
	
	@Test
	public void AbsenseActivityAddEmployeeTwice()
	{
		Scheduler scheduler = new Scheduler();
		TestTools.login(scheduler);
		
		try {
			Employee batman = TestTools.addEmployee(scheduler, "BW");
			TestTools.addEmployeeToProject(scheduler, batman.getInitials(), scheduler.getAbsenceProject().getName());
			scheduler.getAbsenceProject().getOpenActivities().get(0).addEmployee(batman.getInitials());
			scheduler.getAbsenceProject().getOpenActivities().get(0).addEmployee(batman.getInitials());
			Assert.fail();
		} catch (EmployeeAlreadyAssignedException e) {
			assertEquals("BW is already assigned to this activity", e.getMessage());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
