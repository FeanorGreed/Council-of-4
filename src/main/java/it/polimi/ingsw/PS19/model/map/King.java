package it.polimi.ingsw.PS19.model.map;

public class King 
{
	City currentcity;
	Balcony balcony;
	
	public King(City city) 
	{
		currentcity = city;
	}
	public void setBalcony(Balcony balcony) 
	{
		this.balcony = balcony;
	}
	
	public City getCurrentcity() 
	{
		return currentcity;
	}
	
	public Balcony getBalcony() 
	{
		return balcony;
	}
}
