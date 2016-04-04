import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Assert;
import org.junit.Test;

public class DeleteActivity {

	@Test
	public void DeleteActivitySuccessTest()
	{
		Scheduler.createProject("Derp");
	}
}
