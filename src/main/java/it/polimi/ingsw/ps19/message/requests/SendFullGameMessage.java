/*
 * @author Andrea Milanta 
 */
package it.polimi.ingsw.PS19.message.requests;

import it.polimi.ingsw.PS19.controller.action.Action;
import it.polimi.ingsw.PS19.controller.action.MessageInterpreterVisitor;

/*
 * 
 */
public class SendFullGameMessage extends Request 
{
	private static final long serialVersionUID = -1349912240299946517L;

	public SendFullGameMessage(int iD) 
	{
		id = iD;
	}
	/* (non-Javadoc)
	 * @see it.polimi.ingsw.PS19.message.Message#getString()
	 */
	
	@Override
	public Action accept(MessageInterpreterVisitor messageInterpreter) {
		return messageInterpreter.visit(this);
	}

}