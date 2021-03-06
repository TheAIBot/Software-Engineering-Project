package Tests;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;

import SoftwareHouse.Employee;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;

public class RegisterAbsenceTime {

	@Test
	public void registerAbsenceTimeSuccess()
	{
		Scheduler scheduler = new Scheduler();
		TestTools.login(scheduler);
		
		try {
			Employee batman = TestTools.addEmployee(scheduler, "BW");
			
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
	public void registerAbsenceTimeNegativeTime()
	{
		Scheduler scheduler = new Scheduler();
		TestTools.login(scheduler);
		
		try {
			Employee batman = TestTools.addEmployee(scheduler, "BW");
			
			batman.registerTime(scheduler.getAbsenceProject().getName(), 
								scheduler.getAbsenceProject().getOpenActivities().get(0).getName(), 
								"killed superman", 
								-1337);
			Assert.fail();
		} catch (InvalidInformationException e) {
			assertEquals(e.getMessage(), "Used time can't be less than 0");
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}
}
