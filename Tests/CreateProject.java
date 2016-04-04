import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Assert;
import org.junit.Test;

public class CreateProject {

	@Test
	public void createProjectSuccessTest()
	{
		Scheduler.createProject("Derp");
		assertEquals(Scheduler.getProjectes().count, 1);
		assertEquals(Scheduler.getProjectes().get(0).getName(), "Derp");
		Project project = Scheduler.getProject("Derp");
		assertEquals(project.getName(), "Derp");
	}
	
	@Test
	public void createProjectDuplicateName()
	{
		Scheduler.createProject("Derp");
		try {
			Scheduler.createProject("Derp");
			Assert.fail();
		} catch (DuplicateProjectNameException e) {
		}
	}
	
	@Test
	public void createProjectNoNameTest()
	{
		try {
			Scheduler.createProject("");
			Assert.fail();
		} catch (NoNameException  e) {
			// TODO: handle exception
		}
		//need to refractor this into a method call
		try {
			Scheduler.createProject(" ");
			Assert.fail();
		} catch (NoNameException  e) {
			// TODO: handle exception
		}
		try {
			Scheduler.createProject("     ");
			Assert.fail();
		} catch (NoNameException  e) {
			// TODO: handle exception
		}
		try {
			Scheduler.createProject(null);
			Assert.fail();
		} catch (NoNameException  e) {
			// TODO: handle exception
		}
	}
}
