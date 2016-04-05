package SoftwareHouse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Project {
	private Scheduler scheduler;
	private String name;
	
	private List<Activity> openActivities = new ArrayList<Activity>();
	private List<Activity> closedActivities = new ArrayList<Activity>();
	private List<Activity> deletedActivities = new ArrayList<Activity>();
	
	public Project(Scheduler scheduler, String projectName)
	{
		this.scheduler = scheduler;
		this.name = projectName;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the openActivities
	 */
	public List<Activity> getOpenActivities() {
		return openActivities;
	}

	/**
	 * @return the closedActivities
	 */
	public List<Activity> getClosedActivities() {
		return closedActivities;
	}

	/**
	 * @return the deletedActivities
	 */
	public List<Activity> getDeletedActivities() {
		return deletedActivities;
	}

	
	public void addAcitivity(String activityName, String activityDetailedDescription, int i, Calendar startTime,
			Calendar endTime) {
		if (condition) {
			
		}		
	}
}
