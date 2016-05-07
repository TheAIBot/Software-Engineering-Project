
/**
 * The user of the internal system
 * Used for employees to login to their associated user when peforming operations in the system
 */
public class User {
	
	private String initials;
	
	public User(String initials) {
		this.initials = initials;
	}

	public String getInitials() {
		return initials;
	}
	
	
	
}
