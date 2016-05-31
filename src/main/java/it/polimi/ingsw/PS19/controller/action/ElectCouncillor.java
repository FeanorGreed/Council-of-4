package it.polimi.ingsw.PS19.controller.action;

import java.awt.Color;

import it.polimi.ingsw.PS19.model.Model;
import it.polimi.ingsw.PS19.model.map.King;
import it.polimi.ingsw.PS19.model.parameter.RegionType;

public class ElectCouncillor implements Action
{
	private static final int MONEY = 4;
	private static final int HELPERS = 1;
	private Boolean mainAction;
	private Color color;
	private int playerId;
	private RegionType region;
	private King king;

	public ElectCouncillor(Color color, int id, RegionType region, Boolean mainAction) 
	{
		this.color = color;
		this.playerId = id;
		this.region = region;
		this.mainAction = mainAction;
	}
	
	public ElectCouncillor(Color color, int id, King k, Boolean mainAction) 
	{
		this.color = color;
		this.playerId = id;
		this.king = k;
		this.mainAction = mainAction;
	}
	
	@Override
	public Boolean execute(Model model) 
	{
		Boolean result;
		if(king != null)
			result = model.getMap().getKing().getBalcony().setNewCouncill(color);
		else
			result = model.getMap().getRegionByType(region).getBalcony().setNewCouncill(color);
		if(result && mainAction)
			model.getPlayerById(playerId).addMoney(MONEY);
		else if(result && !mainAction)
			model.getPlayerById(playerId).setHelpers(model.getPlayerById(playerId).getHelpers() - HELPERS);
		return result;
	}

	@Override
	public Boolean isPossible(Model model) 
	{
		if(Action.ceckPlayerTurn(playerId, model))
			return false;
		
		if(mainAction == false)
			return model.getPlayerById(playerId).getHelpers() >= HELPERS;
		return true;
	}
	
}
