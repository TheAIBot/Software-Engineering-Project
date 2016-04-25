package SoftwareHouse.ExceptionTypes;

import java.util.ArrayList;

import SoftwareHouse.RegisteredTime;

public class ProjectAlreadyClosedException extends Exception {
	public ProjectAlreadyClosedException(String message)
	{
		super(message);
	}
}
