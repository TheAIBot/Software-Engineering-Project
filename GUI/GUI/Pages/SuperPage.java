package GUI.Pages;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import GUI.GUIController;
import SoftwareHouse.Scheduler;

/**
 * @author Andreas
 * @param <T>
 */
public abstract class SuperPage<T> {
	protected T page;
	protected final Scheduler scheduler;
	protected final GUIController controller;
	private boolean isInitiated = false;
	
	public SuperPage(GUIController controller, Scheduler scheduler)
	{
		this.controller = controller;
		this.scheduler = scheduler;
	}
	
	public T getPage(GUIController controller)
	{
		if (!isInitiated) {
			page = createPage(controller);
		}
		return page;
	}
	
	public abstract T createPage(GUIController controlle);
	
	public abstract void loadInformation();
	
	
}
