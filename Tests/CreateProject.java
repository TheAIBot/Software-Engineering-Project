import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Assert;
import org.junit.Test;

import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.DuplicateProjectNameException;
import SoftwareHouse.ExceptionTypes.MissingProjectException;
import SoftwareHouse.ExceptionTypes.NoNameException;

public class CreateProject {

	@Test
	public void createProjectSuccessTest()
	{
		Scheduler scheduler = new Scheduler();
		try {
			scheduler.createProject("Derp");
		} catch (Exception e1) {
			Assert.fail();
		}
		assertEquals(scheduler.getProjects().size(), 1);
		assertEquals(scheduler.getProjects().get(0).getName(), "Derp");
		try {
			Project project = scheduler.getProject("Derp");
			assertEquals(project.getName(), "Derp");
		} catch (MissingProjectException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void createProjectDuplicateName()
	{
		Scheduler scheduler = new Scheduler();
		try {
			scheduler.createProject("Derp");
		} catch (Exception e1) {
			Assert.fail();
		}
		try {
			scheduler.createProject("Derp");
			Assert.fail();
		} catch (NoNameException e) {
			Assert.fail();
		} catch (DuplicateProjectNameException e) {		
		}
	}
	
	@Test
	public void createProjectNoNameTest()
	{
		Scheduler scheduler = new Scheduler();
		try {
			scheduler.createProject("");
			Assert.fail();
		} catch (NoNameException  e) {
		} catch (DuplicateProjectNameException e) {
			Assert.fail();
		}
		//need to refractor this into a method call
		try {
			scheduler.createProject(" ");
			Assert.fail();
		} catch (NoNameException  e) {
		} catch (DuplicateProjectNameException e) {
			Assert.fail();
		}
		try {
			scheduler.createProject("     ");
			Assert.fail();
		} catch (NoNameException  e) {
		} catch (DuplicateProjectNameException e) {
			Assert.fail();
		}
		try {
			scheduler.createProject(null);
			Assert.fail();
		} catch (NoNameException  e) {
		} catch (DuplicateProjectNameException e) {
			Assert.fail();
		}
	}
}
