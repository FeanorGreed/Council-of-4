package it.polimi.ingsw.PS19.message.replies;

import java.util.List;

import it.polimi.ingsw.PS19.client.clientmodel.ClientUpdate;
import it.polimi.ingsw.PS19.client.clientmodel.ReplyVisitor;
import it.polimi.ingsw.PS19.model.Player;

public class SendFullPlayerReply extends Reply
{

	private static final long serialVersionUID = 1117123155364033902L;
	private List<Player> player;
	
	public SendFullPlayerReply(int activePlayer, String result, List<Player> player) 
	{
		super(activePlayer, result);
		this.player = player;
	}
	
	public List<Player> getPlayer() 
	{
		return player;
	}
	
	@Override
	public ClientUpdate display(ReplyVisitor replyvisitor) 
	{
		return replyvisitor.display(this);
	}

}