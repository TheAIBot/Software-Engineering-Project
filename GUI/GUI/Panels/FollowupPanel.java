package GUI.Panels;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class FollowupPanel extends JPanel {
	private JScrollPane openActivitiesScrollPane;
	private JScrollPane closedActivitiesScrollPane;

	/**
	 * Create the panel.
	 */
	public FollowupPanel() {
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));
		setLayout(null);
		
		closedActivitiesScrollPane = new JScrollPane();
		closedActivitiesScrollPane.setBounds(438, 39, 350, 548);
		add(closedActivitiesScrollPane);
		
		openActivitiesScrollPane = new JScrollPane();
		openActivitiesScrollPane.setBounds(12, 39, 363, 548);
		add(openActivitiesScrollPane);
		
		JLabel lblbneAktiviteter = new JLabel("\u00C5bne aktiviteter");
		lblbneAktiviteter.setHorizontalAlignment(SwingConstants.CENTER);
		lblbneAktiviteter.setBounds(12, 10, 363, 16);
		add(lblbneAktiviteter);
		
		JLabel lblLukkedeAktiviteter = new JLabel("Lukkede aktiviteter");
		lblLukkedeAktiviteter.setHorizontalAlignment(SwingConstants.CENTER);
		lblLukkedeAktiviteter.setBounds(438, 10, 350, 16);
		add(lblLukkedeAktiviteter);
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
}
