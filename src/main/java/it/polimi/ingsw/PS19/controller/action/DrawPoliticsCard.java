package it.polimi.ingsw.PS19.controller.action;

import it.polimi.ingsw.PS19.message.replies.Reply;
import it.polimi.ingsw.PS19.message.replies.SendFullPlayerReply;
import it.polimi.ingsw.PS19.model.Model;

public class DrawPoliticsCard implements Action 
{
	private int playerId;
	private String result;
	
	public DrawPoliticsCard(int id) 
	{
		playerId = id;
	}
	@Override
	public Boolean execute(Model model) 
	{
		model.getPlayerById(playerId).addCardToHand(model.getMap().getPoliticdeck().getFirstCard());
		return true;
	}

	@Override
	public Boolean isPossible(Model model) 
	{
		if(Action.checkPlayerTurn(playerId, model))
		{
			result = ActionMessages.NOT_YOUR_TURN;
			return false;
		}
		
		if(model.getMap().getPoliticdeck().getSize() == 0)
		{
			result = ActionMessages.POLITIC_DECK_IS_OVER;
			return false;
		}
		result = ActionMessages.EVERYTHING_IS_OK;
		return true;
	}
	@Override
	public String getStringResult() 
	{
		return result;
	}
	@Override
	public void checkAlreadyTurn() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Reply createReplyMessage(Model model) 
	{
		return new SendFullPlayerReply(model.getPlayerById(playerId), result);
	}

}
