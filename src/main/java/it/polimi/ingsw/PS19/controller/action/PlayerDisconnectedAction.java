package it.polimi.ingsw.PS19.controller.action;

import it.polimi.ingsw.PS19.message.replies.PlayerDisconnectedReply;
import it.polimi.ingsw.PS19.message.replies.Reply;
import it.polimi.ingsw.PS19.model.Model;

public class PlayerDisconnectedAction implements Action {

	private int playerId;
	private String result;
	
	public PlayerDisconnectedAction(int id) 
	{
		playerId = id;
	}
	@Override
	public Boolean execute(Model model) 
	{
		model.getPlayerById(playerId).disconnectPlayer();
		result = ActionMessages.PLAYER_DISCONNECTED + playerId;
		return true;
	}

	@Override
	public Boolean isPossible(Model model) 
	{
		return true;
	}

	@Override
	public Reply createReplyMessage(Model model) 
	{
		return new PlayerDisconnectedReply(playerId, result);
	}

}
