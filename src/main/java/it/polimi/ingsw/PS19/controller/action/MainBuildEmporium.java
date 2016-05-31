package it.polimi.ingsw.PS19.controller.action;

import java.util.ArrayList;

import it.polimi.ingsw.PS19.model.Model;
import it.polimi.ingsw.PS19.model.Player;
import it.polimi.ingsw.PS19.model.card.BusinessCard;
import it.polimi.ingsw.PS19.model.map.City;
import it.polimi.ingsw.PS19.model.parameter.RegionType;

public class MainBuildEmporium implements Action 
{
	private int cityid;
	private int playerid;
	private BusinessCard businessCard;
	
	public MainBuildEmporium(int city, int playerid, BusinessCard card) 
	{
		this.cityid = city;
		this.playerid = playerid;
		this.businessCard = card;
	}
	
	@Override
	public Boolean execute(Model model) 
	{
		RegionType region = businessCard.getType();
		Player player = model.getPlayerById(playerid);
			//CHECK SE NON C'E' GIA L'EMPORIO DEL PLAYER SU QUESTA CITTA'
		
				//WRITE ME!
		
			//RETURN = numero di empori dentro la città di altri player
		int numberOfHelpers = getNumberOfHelper(model, region);
		
		model.getPlayerById(playerid).setHelpers(model.getPlayerById(playerid).getHelpers() - numberOfHelpers);
		
		model.getMap().getRegionByType(region).getCityById(cityid).buildEmporium(player);
		
		model.getMap().getRegionByType(region).getCityById(cityid).applyBonus(player);
		
		model.getMap().getRegionByType(region).getCityById(cityid).applyNetBonus(player, new ArrayList<City>());
		
		//CONTROLLO SE HO ALTRI BONUS DA APPLICARE
		
		return true;
	}

	@Override
	public Boolean isPossible(Model model) 
	{
		if(Action.ceckPlayerTurn(playerid, model))
			return false;
		RegionType region = businessCard.getType();
		if(model.getPlayerById(playerid).findMyEmporiaById(cityid))
			return false;
		if(getNumberOfHelper(model, region) > model.getPlayerById(playerid).getHelpers())
			return false;
		return true;
	}
	
	private int getNumberOfHelper(Model model, RegionType region)
	{
		return model.getMap().getRegionByType(region).getCityById(cityid).calculateMalusEmporium();
	}

}
