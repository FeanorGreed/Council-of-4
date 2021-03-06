package it.polimi.ingsw.ps19.client.clientinput;

import it.polimi.ingsw.ps19.client.clientmodel.clientdata.ClientModel;
import it.polimi.ingsw.ps19.client.language.Language;

/**
 * Class that contains the main actions
 */
public class MainAction extends ClientActionChooser 
{	
	/**
	 * Constructor
	 * @param m
	 */
	public MainAction(ClientModel m) 
	{
		super(m);
		actions.add(new BuildEmporiumInputs(model));
		actions.add(new BuildWithKingInputs(model));
		actions.add(new ElectCouncillorInputs(model, true));
		actions.add(new GetBusinessPermitInput(model));
	}
	
	@Override
	public String toString(Language l)
	{
		return l.getString(this);
	}

	@Override
	public boolean isPossible() 
	{
		if(model.getMyPlayer().getMainActionCounter() > 0)
			return true;
		return false;
	}
}
