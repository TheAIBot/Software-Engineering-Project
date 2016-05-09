package GUI.Panels;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import GUI.GUIController;

/**
 * @author Niklas
 */
public class FollowupPanel extends JPanel {
	private JScrollPane openActivitiesScrollPane;
	private JScrollPane closedActivitiesScrollPane;
	private JScrollPane registeredTimeScrollPane;
	private JLabel lblRegistreretTid;

	/**
	 * Create the panel.
	 */
	public FollowupPanel() {
		setMaximumSize(GUIController.DEFAULT_PANEL_SIZE);
		setMinimumSize(GUIController.DEFAULT_PANEL_SIZE);
		setPreferredSize(GUIController.DEFAULT_PANEL_SIZE);
		setLayout(null);
		
		closedActivitiesScrollPane = new JScrollPane();
		closedActivitiesScrollPane.setBounds(850, 42, 350, 548);
		add(closedActivitiesScrollPane);
		
		openActivitiesScrollPane = new JScrollPane();
		openActivitiesScrollPane.setBounds(475, 42, 363, 548);
		add(openActivitiesScrollPane);
		
		JLabel lblbneAktiviteter = new JLabel("\u00C5bne aktiviteter");
		lblbneAktiviteter.setHorizontalAlignment(SwingConstants.CENTER);
		lblbneAktiviteter.setBounds(475, 13, 363, 16);
		add(lblbneAktiviteter);
		
		JLabel lblLukkedeAktiviteter = new JLabel("Lukkede aktiviteter");
		lblLukkedeAktiviteter.setHorizontalAlignment(SwingConstants.CENTER);
		lblLukkedeAktiviteter.setBounds(850, 13, 350, 16);
		add(lblLukkedeAktiviteter);
		
		registeredTimeScrollPane = new JScrollPane();
		registeredTimeScrollPane.setBounds(187, 42, 276, 545);
		add(registeredTimeScrollPane);
		
		lblRegistreretTid = new JLabel("Registreret tid");
		lblRegistreretTid.setHorizontalAlignment(SwingConstants.CENTER);
		lblRegistreretTid.setBounds(187, 13, 276, 16);
		add(lblRegistreretTid);
	}

	/**
	 * @return the openActivitiesScrollPane
	 */
	public JScrollPane getOpenActivitiesScrollPane() {
		return openActivitiesScrollPane;
	}

	/**
	 * @return the closedActivitiesScrollPane
	 */
	public JScrollPane getClosedActivitiesScrollPane() {
		return closedActivitiesScrollPane;
	}

	/**
	 * @return the registeredTimeScrollPane
	 */
	public JScrollPane getRegisteredTimeScrollPane() {
		return registeredTimeScrollPane;
	}
}
