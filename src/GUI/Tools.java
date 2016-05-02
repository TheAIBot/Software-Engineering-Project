package GUI;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Tools {
	
	//copied from our ealier projects we wrote the code that this is copied from!!!
	public static GridBagConstraints createConstraint(int gridx, int gridy, int gridWidth, int gridHeight, int anchor, int fill, int border, int weight)
	{
		return createConstraint(gridx, gridy, gridWidth, gridHeight, anchor, fill, border, weight, 0, 0);
	}
	
	public static GridBagConstraints createConstraint(int gridx, int gridy, int gridWidth, int gridHeight, int anchor, int fill, int border, int weight, int ipadx, int ipadY)
	{
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = gridx;
		gc.gridy = gridy;
		gc.weightx = weight;
		gc.weighty = weight;
		gc.fill = fill;
		gc.insets = new Insets(border, border, border, border);
		gc.gridwidth = gridWidth;
		gc.gridheight = gridHeight;
		gc.anchor = anchor;
		gc.ipadx = ipadx;
		gc.ipady = ipadY;
		return gc;
	}
}
