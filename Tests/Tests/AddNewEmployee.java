package Tests;
import static org.junit.Assert.*;

import org.junit.Test;

import SoftwareHouse.Employee;
import SoftwareHouse.Scheduler;
import SoftwareHouse.ExceptionTypes.DuplicateNameException;
import SoftwareHouse.ExceptionTypes.EmployeeNotFoundException;
import SoftwareHouse.ExceptionTypes.IllegalCharException;
import SoftwareHouse.ExceptionTypes.MissingInformationException;
import SoftwareHouse.ExceptionTypes.TooManyCharsException;

import org.junit.Assert;

public class AddNewEmployee {

	@Test
	public void addEmployeeSuccessTest()
	{
		try {
			TestTools.addEmployee(new Scheduler(), "DERP");
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void addEmployeeDuplicateInitialsTest() 
	{
		Scheduler scheduler = new Scheduler();
		TestTools.login(scheduler);
		try {
			TestTools.addEmployee(scheduler, "DERP");
		} catch (Exception e) {
			Assert.fail();
		}
		try {
			TestTools.addEmployee(scheduler, "DERP");
			Assert.fail();
		} catch (EmployeeNotFoundException e) {
			Assert.fail();
		} catch (MissingInformationException e) {
			Assert.fail();
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
		//need to refractor this into method calls
		try {
			scheduler.addEmployee("");
			Assert.fail();
		} catch (MissingInformationException  e) {
			assertEquals(e.getMessage(), "Missing employee initials");
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (TooManyCharsException e){
			Assert.fail();
		} catch (IllegalCharException e){
			Assert.fail();
		}
		
		try {
			scheduler.addEmployee(" ");
			Assert.fail();
		} catch (MissingInformationException  e) {
			assertEquals(e.getMessage(), "Missing employee initials");
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (TooManyCharsException e){
			Assert.fail();
		} catch (IllegalCharException e){
			Assert.fail();
		}
		
		try {
			scheduler.addEmployee("     ");
			Assert.fail();
		} catch (MissingInformationException  e) {
			assertEquals(e.getMessage(), "Missing employee initials");
		} catch (DuplicateNameException e) {
			Assert.fail();
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
			Assert.fail();
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
			scheduler.addEmployee("TOOMANYINITIALS");
		} 
		  catch (MissingInformationException  e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail();
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
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (TooManyCharsException e) {
			Assert.fail();
		} catch (IllegalCharException e) {
			assertEquals(e.getMessage(),"Only letters are allowed for initials");
		}
		try {
			scheduler.addEmployee("bob3");
		} catch (MissingInformationException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (TooManyCharsException e) {
			Assert.fail();
		} catch (IllegalCharException e) {
			assertEquals(e.getMessage(),"Only letters are allowed for initials");
		}
		try {
			scheduler.addEmployee("BoB_");
		} catch (MissingInformationException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (TooManyCharsException e) {
			Assert.fail();
		} catch (IllegalCharException e) {
			assertEquals(e.getMessage(),"Only letters are allowed for initials");
		}
		
		try {
			scheduler.addEmployee("c@t?");
		} catch (MissingInformationException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (TooManyCharsException e) {
			Assert.fail();
		} catch (IllegalCharException e) {
			assertEquals(e.getMessage(),"Only letters are allowed for initials");
		}
		
		try {
			scheduler.addEmployee("זרו");
		} catch (MissingInformationException e) {
			Assert.fail();
		} catch (DuplicateNameException e) {
			Assert.fail();
		} catch (TooManyCharsException e) {
			Assert.fail();
		} catch (IllegalCharException e) {
			Assert.fail();
		}
	}
}
