package SoftwareHouse;

public class RegisteredTime {
	private int time;
	private String message;
	private Employee employee;
	
	public RegisteredTime(int time, String message, Employee employee) {
		this.time = time;
		this.message = message;
		this.employee = employee;
	}

	public int getTime() {
		return time;
	}

	public String getMessage() {
		return message;
	}

	public Employee getEmployee() {
		return employee;
	}
	
	
}
