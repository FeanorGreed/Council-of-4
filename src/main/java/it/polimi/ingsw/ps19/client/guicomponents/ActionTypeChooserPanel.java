package it.polimi.ingsw.ps19.client.guicomponents;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import it.polimi.ingsw.ps19.client.ClientGUI;
import it.polimi.ingsw.ps19.client.language.Language;

public class ActionTypeChooserPanel extends JPanel{
	
	private static final long serialVersionUID = -2127659641196167037L;
		
	private JButton mainButton;
	private JButton fastButton;
	private Language language;
	
	public ActionTypeChooserPanel(Language l) 
	{
		super();
		language = l;
		setLayout(new GridLayout(1,2));

		mainButton = new JButton(language.getMain());
		mainButton.setToolTipText(language.getMain());

		mainButton.setEnabled(false);
		
		fastButton = new JButton(language.getQuick());
		fastButton.setToolTipText(language.getQuick());

		fastButton.setEnabled(false);
		
		add(mainButton);
		add(fastButton);
		
	}
	
	protected void setListener(ClientGUI al){
		mainButton.setActionCommand(ClientGUI.getMainActionCommand());
		mainButton.addActionListener(al);
		fastButton.setActionCommand(ClientGUI.getFastActionCommand());
		fastButton.addActionListener(al);
	}
	
	protected void enableButtons(){
		mainButton.setEnabled(true);
		fastButton.setEnabled(true);
		repaint();
	}
	
	protected void disableButtons(){
		mainButton.setEnabled(false);
		fastButton.setEnabled(false);
		repaint();
	}
	
}
