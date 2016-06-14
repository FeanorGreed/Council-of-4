package it.polimi.ingsw.PS19.client;

import java.util.Observable;
import java.util.Observer;
import it.polimi.ingsw.PS19.client.clientaction.FastAction;
import it.polimi.ingsw.PS19.client.clientaction.MainAction;
import it.polimi.ingsw.PS19.client.clientmodel.ClientUpdate;
import it.polimi.ingsw.PS19.client.clientmodel.ReplyVisitor;
import it.polimi.ingsw.PS19.client.clientmodel.ReplyVisitorImpl;
import it.polimi.ingsw.PS19.client.clientmodel.clientdata.ClientModel;
import it.polimi.ingsw.PS19.message.Message;
import it.polimi.ingsw.PS19.message.replies.GameStartedMessage;
import it.polimi.ingsw.PS19.message.replies.Reply;
import it.polimi.ingsw.PS19.message.replies.StringMessage;
import it.polimi.ingsw.PS19.message.requests.Request;

/**
 * Reads the messages from the server and act consequentially
 */
public class ClientInterpreter extends Observable implements Observer
{
	ClientUI userInterface;
	ClientModel model;
	Integer playerId;
	MainAction mainAction;
	FastAction fastAction;
	ReplyVisitor visitor;
	
	/**
	 * @param ui: user interface
	 */
	public ClientInterpreter(ClientUI ui) 
	{
		userInterface = ui;
	}
	
	private void loadInterpreter()
	{
		visitor = new ReplyVisitorImpl();
		model = new ClientModel(playerId);
		mainAction = new MainAction(model);
		fastAction = new FastAction(model);
		ClientUpdate.loadTypeOfAction(mainAction);
		ClientUpdate.loadTypeOfAction(fastAction);
	}

	@Override
	public void update(Observable o, Object arg) 
	{
		if(arg instanceof GameStartedMessage)
		{
			playerId = ((GameStartedMessage)arg).getPlayerNumber();
			loadInterpreter();
			userInterface.showNotification(((GameStartedMessage)arg).toString());
		}
		else if(arg instanceof StringMessage)
		{
			userInterface.showNotification(((StringMessage)arg).toString());
			return;
		}
		else if(arg instanceof Reply)
		{
			Reply reply = (Reply) arg;
			ClientUpdate updateAction = reply.display(visitor);
			updateAction.update(model);
			userInterface.drawModel(model);
			userInterface.showNotification("Active player: " + reply.getActivePlayer());
			if(reply.getActivePlayer() == playerId)
			{
				Request mex = updateAction.execute(userInterface, model);
				notify(mex);
			}
		}
		else
			userInterface.showNotification("Invalid Object received");
	}	
	
	private void notify(Message mex)
	{
		if(mex == null)
			return;
		mex.setId(playerId);
		setChanged();
		notifyObservers(mex);
	}
}