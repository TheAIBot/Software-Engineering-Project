package SoftwareHouse;

import java.util.ArrayList;
import java.util.List;

import SoftwareHouse.ExceptionTypes.DuplicateProjectNameException;
import SoftwareHouse.ExceptionTypes.MissingProjectException;
import SoftwareHouse.ExceptionTypes.NoNameException;

public class Scheduler {

	private List<Project> projects = new ArrayList<Project>();
	
	public void createProject(String projectName) throws NoNameException, DuplicateProjectNameException {
		if (projectName == null || projectName.trim().isEmpty()) {
			throw new NoNameException();
		}
		if (projects.stream().anyMatch(x -> x.getName().equals(projectName))) {
			throw new DuplicateProjectNameException();
		}
		projects.add(new Project(projectName));
	}

	/**
	 * @return the projects
	 */
	public List<Project> getProjects() {
		return projects;
	}

	public Project getProject(String projectName) throws MissingProjectException {
		for (Project project : projects) {
			if (project.getName().equals(projectName)) {
				return project;
			}
		}
		throw new MissingProjectException();
	}

}
