package it.polimi.ingsw.ps19.controller.action;

import it.polimi.ingsw.ps19.controller.support.ActionMessages;
import it.polimi.ingsw.ps19.controller.support.SupportMethod;
import it.polimi.ingsw.ps19.message.replies.CompleteMarketReply;
import it.polimi.ingsw.ps19.message.replies.Reply;
import it.polimi.ingsw.ps19.message.replies.WaitingPlayerForMarketReply;
import it.polimi.ingsw.ps19.model.Model;
import it.polimi.ingsw.ps19.model.Order;

/**
 * Action to add an order to the market
 */
public class AddOrder implements Action 
{
	private Order order;
	private int playerId;
	private String result;
	
	/**
	 * Constructor
	 * @param o: order
	 * @param id: selling player id
	 */
	public AddOrder(Order o, int id) 
	{
		order = o;
		playerId = id;
	}
	@Override
	public Boolean execute(Model model) 
	{
		if(order!=null)
			model.getMarket().addOrder(order, playerId);
		else 
			model.getMarket().addNullOrder();
		model.getCurrentState().setPlayerTurnId(playerId);
		model.getCurrentState().setPlayerTurnId(model.getCurrentState().giveNextCorrectId(playerId));
		result = ActionMessages.EVERYTHING_IS_OK;
		return true;
	}

	@Override
	public Boolean isPossible(Model model) 
	{
		result = ActionMessages.EVERYTHING_IS_OK;
		if(order == null)
			return true;
		if(!model.getCurrentState().isTimeToMarket())
		{	
			result = ActionMessages.NO_MARKET_TIME;
			return false;
		}
		if(!SupportMethod.findPoliticCard(order.getPoliticscard(), model.getPlayerById(playerId)))
		{
			result = ActionMessages.NOT_HAVE_POLITIC_CARD;
			return false;
		}
		return true;
	}

	@Override
	public Reply createReplyMessage(Model model) 
	{
		int idTurn = model.getCurrentState().giveRandomTurn();
		
		if(model.getMarket().getSize() + model.getMarket().getNullOrder() >= 
				model.getCurrentState().getNumberOfPlayer() - model.getCurrentState().getNumberOfDisconnectedPlayer())
			return new CompleteMarketReply(model.getMarket(), result + ". Is Time to Buy", idTurn);
		
		return new WaitingPlayerForMarketReply(model.getCurrentState().getPlayerTurnId(), result + ". Waiting For Player");
	}
}
