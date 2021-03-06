package it.polimi.ingsw.ps19.model.bonus;

import it.polimi.ingsw.ps19.client.language.Language;
import it.polimi.ingsw.ps19.model.Player;

/**
 * Bonus to add more helpers
 */
public class MoreHelpers implements Bonus 
{
	private static final long serialVersionUID = -7916908846822869862L;
	
	int howMany; //how many helpers to give
	
	/**
	 * Constructor for a bonus adding n helpers
	 * @param n
	 */
	public MoreHelpers(int n) {
		howMany=n;
	}
	
	@Override
	public void giveBonus(Player p) 
	{
		p.setHelpers(p.getHelpers()+howMany);
	}
	
	@Override
	public String toString(Language l) {
		return l.getString(this, howMany);
	}
}
