package it.polimi.ingsw.PS19.message.replies;

import it.polimi.ingsw.PS19.client.clientmodel.ClientUpdate;
import it.polimi.ingsw.PS19.client.clientmodel.ReplyVisitor;
import it.polimi.ingsw.PS19.message.Message;

public abstract class Reply extends Message
{
	private String result;
	private int activePlayer;
	
	private static final long serialVersionUID = -1984682021445434304L;
	
	public Reply(int activePlayer, String result)
	{
		this.activePlayer =activePlayer;
		this.result = result;
	}
	
	public abstract ClientUpdate display(ReplyVisitor replyvisitor);
	
	public void setActivePlayer(int activePlayer) 
	{
		this.activePlayer = activePlayer;
	}
	public int getActivePlayer() 
	{
		return activePlayer;
	}
	
	public String getResult() 
	{
		return result;
	}
}