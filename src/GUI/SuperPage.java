package GUI;

import java.awt.GridBagLayout;

import javax.swing.JPanel;

import SoftwareHouse.Scheduler;

public abstract class SuperPage {
	protected final JPanel page = new JPanel(new GridBagLayout());
	protected final Scheduler scheduler;
	private boolean isInitiated = false;
	
	public SuperPage(Scheduler scheduler)
	{
		this.scheduler = scheduler;
	}
	
	public JPanel getPage(GUIController controller)
	{
		if (!isInitiated) {
			initiatePage(controller);
		}
		return page;
	}
	
	public abstract void initiatePage(GUIController controlle);
	
	
}
