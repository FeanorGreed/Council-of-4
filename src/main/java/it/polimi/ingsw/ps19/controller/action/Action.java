package it.polimi.ingsw.ps19.controller.action;

import it.polimi.ingsw.ps19.message.replies.Reply;
import it.polimi.ingsw.ps19.model.Model;

/**
 * The Interface Action.
 */
public interface Action
{

	/**
	 * Execute che action on model.
	 * This method mody model
	 *
	 * @param model the model of a single game.
	 * @return true if the action is corrected.
	 */
	public Boolean execute(Model model);
	
	/**
	 * Checks if the action is possible.
	 *
	 * @param model the model of a single game.
	 * @return true if a specific action is possible, fase if the action is not possible.
	 */
	public Boolean isPossible(Model model);
	
	/**
	 * Creates the reply message to send to view
	 *
	 * @param model the model
	 * @return the specific reply message
	 */
	public Reply createReplyMessage(Model model);

}
