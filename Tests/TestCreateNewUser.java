import static org.junit.Assert.*;

import org.junit.Test;


public class TestCreateNewUser {


	/**
	 * Tests if a User can succesfully be created with the given initials.
	 * 
	 * @author Niklas Refsgaard
	 */
	@Test
	public void testUserConstruction(){
		String initials = "test";
		User user = new User(initials);
		assertEquals(user.getInitials(),initials);
		
	}
	
	

}
