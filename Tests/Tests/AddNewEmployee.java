package Tests;
import static org.junit.Assert.*;

import org.junit.Test;

import com.sun.xml.internal.bind.v2.TODO;

import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.IllegalCharException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.TooManyCharsException;

import org.junit.Assert;

public class AddNewEmployee {
	
	Scheduler scheduler = new Scheduler();
	
	@Test
	public void addEmployeeSuccessTest()
	{
		try {
			TestTools.addEmployee(scheduler, "AM");
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		
	}
	
	@Test
	public void addEmployeeDuplicateInitialsTest() 
	{
		Scheduler scheduler = new Scheduler();
		TestTools.login(scheduler);
		try {
			TestTools.addEmployee(scheduler, "AM");
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		}
		
		// Using TestTools to try adding employee duplicate with initials
		try {
			TestTools.addEmployee(scheduler, "AM");
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			Assert.fail(e.getMessage());
		} catch (MissingInformationException e) {
			Assert.fail(e.getMessage());
		} catch (DuplicateNameException e) {
			assertEquals(e.getMessage(), "An employee with those initials already exist");
		} catch (TooManyCharsException e){
			Assert.fail();
		} catch (IllegalCharException e){
			Assert.fail();
		}
		
	}
	
	@Test
	public void addEmployeeNoNameTest()
	{
		Scheduler scheduler = new Scheduler();
		try {
			scheduler.addEmployee("");
			Assert.fail();
		} catch (MissingInformationException  e) {
			assertEquals(e.getMessage(), "Missing employee initials");
		} catch (DuplicateNameException e) {
			Assert.fail(e.getMessage());
		} catch (TooManyCharsException e){
			Assert.fail();
		} catch (IllegalCharException e){
			Assert.fail();
		}
		
		try {
			scheduler.addEmployee(null);
			Assert.fail();
		} catch (MissingInformationException  e) {
			assertEquals(e.getMessage(), "Missing employee initials");
		} catch (DuplicateNameException e) {
			Assert.fail(e.getMessage());
		} catch (TooManyCharsException e){
			Assert.fail();
		} catch (IllegalCharException e){
			Assert.fail();
		}
	}
	
	@Test
	public void tooManyInitialsTest()
	{
		Scheduler scheduler = new Scheduler();
		try{
			scheduler.addEmployee("ABCDEFG");
		} catch (MissingInformationException  e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail(e.getMessage());
		} catch (TooManyCharsException e){
			assertEquals(e.getMessage(), "Number of characters has exceeded the maximum of 4");
		} catch (IllegalCharException e){
			Assert.fail();
		}
		
	}
	
	@Test
	public void illegalCharTest()
	{
		Scheduler scheduler = new Scheduler();
		try {
			scheduler.addEmployee("1234");
		} catch (MissingInformationException e) {
			Assert.fail(e.getMessage());
		} catch (DuplicateNameException e) {
			Assert.fail(e.getMessage());
		} catch (TooManyCharsException e) {
			Assert.fail(e.getMessage());
		} catch (IllegalCharException e) {
			assertEquals(e.getMessage(),"Only letters are allowed for initials");
		}
		try {
			scheduler.addEmployee("b3b?");
		} catch (MissingInformationException e) {
			Assert.fail(e.getMessage());
		} catch (DuplicateNameException e) {
			Assert.fail(e.getMessage());
		} catch (TooManyCharsException e) {
			Assert.fail(e.getMessage());
		} catch (IllegalCharException e) {
			assertEquals(e.getMessage(),"Only letters are allowed for initials");
		}
		try {
			scheduler.addEmployee("BoB_");
		} catch (MissingInformationException e) {
			Assert.fail(e.getMessage());
		} catch (DuplicateNameException e) {
			Assert.fail(e.getMessage());
		} catch (TooManyCharsException e) {
			Assert.fail(e.getMessage());
		} catch (IllegalCharException e) {
			assertEquals(e.getMessage(),"Only letters are allowed for initials");
		}
		
		try {
			scheduler.addEmployee("c@t?");
		} catch (MissingInformationException e) {
			Assert.fail(e.getMessage());
		} catch (DuplicateNameException e) {
			Assert.fail(e.getMessage());
		} catch (TooManyCharsException e) {
			Assert.fail(e.getMessage());
		} catch (IllegalCharException e) {
			assertEquals(e.getMessage(),"Only letters are allowed for initials");
		}
	}
}
