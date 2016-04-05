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
import SoftwareHouse.ExceptionTypes.NoNameException;

public class DeleteActivity {

	@Test
	public void DeleteActivitySuccessTest()
	{
		Scheduler scheduler = new Scheduler();
		try {
			scheduler.createProject("Derp");
		} catch (Exception e) {
			Assert.fail();
		}
		Project project = scheduler.getProject("Derp");
		project.addActivity();
		project.deleteActivity();
	}
}
