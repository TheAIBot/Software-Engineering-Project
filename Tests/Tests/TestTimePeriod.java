package Tests;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

import SoftwareHouse.TimePeriod;
import SoftwareHouse.ExceptionTypes.InvalidInformationException;

/**
 * @author Jesper
 */
public class TestTimePeriod {

	/**
	 * Jesper
	 */
	@Test
	public void createTimePeriodSuccess()
	{
		Calendar startTime = null;
		try {
			startTime = TimePeriod.getCalendarFromString("1 1 1");
		} catch (ParseException e1) {
			Assert.fail();
		}
		Calendar endTime = null;
		try {
			endTime = TimePeriod.getCalendarFromString("2 2 2");
		} catch (ParseException e1) {
			Assert.fail();
		}
		endTime.set(2, 1, 2);
		TimePeriod timePeriod = null;
		try {
			timePeriod = new TimePeriod(startTime, endTime);
		} catch (InvalidInformationException e) {
			Assert.fail();
		}
		assertEquals(timePeriod.getStartDate(),startTime);
		assertEquals(timePeriod.getEndDate(), endTime);
		assertEquals("01 01 0001", timePeriod.getStartDateAsString());		
		assertEquals("02 02 0002", timePeriod.getEndDateAsString());
		assertEquals(startTime, timePeriod.getStartDate());
		assertEquals(endTime, timePeriod.getEndDate());
	}

	/**
	 * Jesper
	 */
	@Test
	public void createTimePeriodMissingStartDate()
	{
		Calendar endTime = Calendar.getInstance();
		endTime.set(2016, 12, 1);
		TimePeriod timePeriod = null;
		try {
			timePeriod = new TimePeriod(null, endTime);
			Assert.fail();
		} catch (InvalidInformationException e) {
			assertEquals("Start date doesn't exist", e.getMessage());
		}
	}

	/**
	 * Jesper
	 */
	@Test
	public void createTimePeriodMissingEndDate()
	{
		Calendar startTime = Calendar.getInstance();
		startTime.set(2016, 12, 1);
		TimePeriod timePeriod = null;
		try {
			timePeriod = new TimePeriod(startTime, null);
			Assert.fail();
		} catch (InvalidInformationException e) {
			assertEquals("End date doesn't exist", e.getMessage());
		}
	}

	/**
	 * Jesper
	 */
	@Test
	public void createTimePeriodInvalidDates()
	{
		Calendar startTime = new GregorianCalendar();
		startTime.set(2, 1, 2); // why does this work like this?
		Calendar endTime = new GregorianCalendar();
		endTime.set(1, 0, 1);
		TimePeriod timePeriod = null;
		try {
			timePeriod = new TimePeriod(startTime, endTime);
		} catch (InvalidInformationException e) {
			assertEquals("End date has to start after start date", e.getMessage());
		}
	}
}
