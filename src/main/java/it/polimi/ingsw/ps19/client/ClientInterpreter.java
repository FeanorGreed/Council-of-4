package it.polimi.ingsw.ps19.client;

import java.util.Observable;
import java.util.Observer;

import it.polimi.ingsw.ps19.client.clientinput.FastAction;
import it.polimi.ingsw.ps19.client.clientinput.MainAction;
import it.polimi.ingsw.ps19.client.clientmodel.ClientUpdate;
import it.polimi.ingsw.ps19.client.clientmodel.ReplyVisitor;
import it.polimi.ingsw.ps19.client.clientmodel.ReplyVisitorImpl;
import it.polimi.ingsw.ps19.client.clientmodel.clientdata.ClientModel;
import it.polimi.ingsw.ps19.exceptions.PlayerDisconnectedException;
import it.polimi.ingsw.ps19.exceptions.clientexceptions.InvalidInsertionException;
import it.polimi.ingsw.ps19.message.Message;
import it.polimi.ingsw.ps19.message.replies.ConnectionReply;
import it.polimi.ingsw.ps19.message.replies.GameStartedMessage;
import it.polimi.ingsw.ps19.message.replies.Reply;
import it.polimi.ingsw.ps19.message.replies.StringMessage;
import it.polimi.ingsw.ps19.message.requests.Request;

/**
 * Reads the messages from the server and act consequentially
 */
public class ClientInterpreter extends Observable implements Observer
{
	boolean loaded = false;
	ClientUI userInterface;
	ClientModel model;
	Integer playerId;
	MainAction mainAction;
	FastAction fastAction;
	ReplyVisitor visitor;
	
	/**
	 * @param pId 
	 * @param activePlayer 
	 * @param ui: user interface
	 */
	public ClientInterpreter(ClientUI ui, Integer pId) 
	{
		if(pId != null)
			playerId = pId;
		userInterface = ui;
	}
	
	private void loadInterpreter(Integer pId)
	{
		if(pId != null)
		{
			playerId = pId;
			model = new ClientModel(pId);
		}
		if(loaded)
			return;
		visitor = new ReplyVisitorImpl();
		mainAction = new MainAction(model);
		fastAction = new FastAction(model);
		ClientUpdate.loadTypeOfAction(mainAction);
		ClientUpdate.loadTypeOfAction(fastAction);
		loaded = true;
	}

	@Override
	public void update(Observable o, Object arg) 
	{
		if(arg == null)
		{
			userInterface.showNotification(userInterface.getLanguage().getServerQuits() + "\n" + userInterface.getLanguage().getKillClient());
			return;
		}
		if(arg instanceof GameStartedMessage)
		{
			playerId = ((GameStartedMessage)arg).getPlayerNumber();
			loadInterpreter(playerId);
			userInterface.showNotification(((GameStartedMessage)arg).toString());
		}
		else if(arg instanceof StringMessage)
		{
			userInterface.showNotification(((StringMessage)arg).toString());
			return;
		}
		else if(arg instanceof ConnectionReply)
		{
			if(((ConnectionReply)arg).getSuccessful())
			{
				userInterface.showNotification(userInterface.getLanguage().getReconnected());
				loadInterpreter(((ConnectionReply)arg).getPassword());
			}
			userInterface.showNotification(userInterface.getLanguage().getConnPass() + ": " + ((ConnectionReply)arg).getPassword());
			return;
		}
		else if(arg instanceof Reply)
		{
			Reply reply = (Reply) arg;
			ClientUpdate updateAction = reply.display(visitor);
			updateAction.update(model, userInterface);
			if(reply.getActivePlayer() == playerId || reply.getActivePlayer() == -1)
			{
				boolean valid;
				Request mex = null;
				do
				{
					try 
					{
						mex = updateAction.execute(userInterface, model);
						valid = true;
					} catch (InvalidInsertionException e) 
					{
						userInterface.showNotification(userInterface.getLanguage().getInvalidInsertion());
						ClientStarter.log.log(e);
						valid = false;
					}
				}while(!valid);
				try 
				{
					notify(mex);
				} catch (PlayerDisconnectedException e)
				{
					ClientStarter.log.log(e);
					userInterface.showNotification(userInterface.getLanguage().getServerQuits());
					userInterface.showNotification(userInterface.getLanguage().getKillClient());
					return;
				}
			}
		}
		else
			userInterface.showNotification(userInterface.getLanguage().getInvalidObj());
		}	
	
	private void notify(Message mex) throws PlayerDisconnectedException
	{
		if(mex == null)
			return;
		mex.setId(playerId);
		setChanged();
		notifyObservers(mex);
	}
}