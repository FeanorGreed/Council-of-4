package it.polimi.ingsw.PS19.client.clientaction;

import it.polimi.ingsw.PS19.client.ClientUI;
import it.polimi.ingsw.PS19.client.clientmodel.clientdata.ClientModel;
import it.polimi.ingsw.PS19.exceptions.clientexceptions.InvalidInsertionException;
import it.polimi.ingsw.PS19.message.requests.BuyMainActionMessage;
import it.polimi.ingsw.PS19.message.requests.Request;

/**
 * Class that creates a new NewMainActionMessage from user inputs and local model
 */
public class BuyMainActionInput extends ClientAction 
{
	/**
	 * Constructor
	 * @param m
	 */
	public BuyMainActionInput(ClientModel m) 
	{
		model = m;
	}

	@Override
	public boolean isPossible() 
	{
		if(model.getMyPlayer().getHelpers() >= 3)
			return true;
		return false;
	}

	@Override
	public Request execute(ClientUI userInterface) throws InvalidInsertionException 
	{
		return buildMessage();
	}

	@Override
	protected Request buildMessage() 
	{
		return new BuyMainActionMessage();
	}

}