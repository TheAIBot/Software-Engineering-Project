package Tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


import org.junit.Assert;
import org.junit.Test;

import SoftwareHouse.Project;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.NotLoggedInException;
import SoftwareHouse.ExceptionTypes.ProjectNotFoundException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;

public class CreateProject {

	//add test where we add bugetted time and verify it
	//add test where we add detailed text to project and verify it
	
	@Test
	public void createProjectSuccessTest()
	{
		Scheduler scheduler = new Scheduler();
		TestTools.login(scheduler);
		try {
			scheduler.createProject("Derp");
		} catch (Exception e1) {
			Assert.fail();
		}
		try {
			assertEquals(scheduler.getProjects().size(), 1);
			assertEquals(scheduler.getProjects().get(0).getName(), "Derp");
		} catch (Exception e1) {
			Assert.fail();
		}
		try {
			Project project = scheduler.getProject("Derp");
			assertEquals(project.getName(), "Derp");
		} catch (ProjectNotFoundException e) {
			Assert.fail();
		} catch (NotLoggedInException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void createProjectDuplicateName()
	{
		Scheduler scheduler = new Scheduler();
		TestTools.login(scheduler);
		try {
			scheduler.createProject("Derp");
		} catch (Exception e1) {
			Assert.fail();
		}
		try {
			scheduler.createProject("Derp");
			Assert.fail();
		} catch (MissingInformationException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {		
		} catch (NotLoggedInException e) {
			Assert.fail();
		}
	}
	
	@Test
	public void createProjectNoNameTest()
	{
		Scheduler scheduler = new Scheduler();
		TestTools.login(scheduler);
		//need to refractor this into method calls
		try {
			scheduler.createProject("");
			Assert.fail();
		} catch (MissingInformationException  e) {
			assertEquals(e.getMessage(), "Missing project name");
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (NotLoggedInException e) {
			Assert.fail();
		}
		try {
			scheduler.createProject(" ");
			Assert.fail();
		} catch (MissingInformationException  e) {
			assertEquals(e.getMessage(), "Missing project name");
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (NotLoggedInException e) {
			Assert.fail();
		}
		try {
			scheduler.createProject("     ");
			Assert.fail();
		} catch (MissingInformationException  e) {
			assertEquals(e.getMessage(), "Missing project name");
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (NotLoggedInException e) {
			Assert.fail();
		}
		try {
			scheduler.createProject(null);
			Assert.fail();
		} catch (MissingInformationException  e) {
			assertEquals(e.getMessage(), "Missing project name");
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (NotLoggedInException e) {
			Assert.fail();
		}
	}
}
