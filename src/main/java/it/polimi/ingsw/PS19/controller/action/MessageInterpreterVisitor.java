package it.polimi.ingsw.PS19.controller.action;

import it.polimi.ingsw.PS19.message.requests.BuyHelperMessage;
import it.polimi.ingsw.PS19.message.requests.ChangeKingPositionMessage;
import it.polimi.ingsw.PS19.message.requests.DrawPoliticsCardMessage;
import it.polimi.ingsw.PS19.message.requests.ElectCouncillorMessage;
import it.polimi.ingsw.PS19.message.requests.EndTurnMessage;
import it.polimi.ingsw.PS19.message.requests.GetBusinessCardMessage;
import it.polimi.ingsw.PS19.message.requests.SendFullGameMessage;

public interface MessageInterpreterVisitor 
{
	public Action visit(ElectCouncillorMessage message);
	
	public Action visit(SendFullGameMessage message);
	
	public Action visit(BuyHelperMessage message);
	
	public Action visit(GetBusinessCardMessage message);
	
	public Action visit(DrawPoliticsCardMessage message);
	
	public Action visit(EndTurnMessage message);
	
	public Action visit(ChangeKingPositionMessage message);
		
}
