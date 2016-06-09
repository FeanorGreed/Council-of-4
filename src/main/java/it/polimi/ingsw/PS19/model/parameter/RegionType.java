package it.polimi.ingsw.PS19.model.parameter;

import java.util.ArrayList;
import java.util.List;

public enum RegionType 
{
	MOUNTAIN, HILL, PLAIN;
	
	public static RegionType valueOf(int n)
	{
		if(n == 0)
			return PLAIN;
		if(n == 1) 
			return HILL;
		if(n == 2) 
			return MOUNTAIN;
		return null;
	}
	
	public static List<RegionType> getValues()
	{
		List<RegionType> regions = new ArrayList<>();
		for(RegionType r : RegionType.values())
			regions.add(r);
		return regions;
	}
}
//INCOERENTE, da rivedere con file XML