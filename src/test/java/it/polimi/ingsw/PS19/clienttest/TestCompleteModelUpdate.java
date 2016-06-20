package it.polimi.ingsw.PS19.clienttest;

import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.List;

import org.junit.Test;

import it.polimi.ingsw.PS19.client.clientmodel.ClientUpdate;
import it.polimi.ingsw.PS19.client.clientmodel.ElectCouncillorUpdate;
import it.polimi.ingsw.PS19.client.clientmodel.ReplyVisitor;
import it.polimi.ingsw.PS19.client.clientmodel.ReplyVisitorImpl;
import it.polimi.ingsw.PS19.client.clientmodel.clientdata.ClientModel;
import it.polimi.ingsw.PS19.controller.GameController;
import it.polimi.ingsw.PS19.message.replies.ElectCouncillorReply;
import it.polimi.ingsw.PS19.message.replies.Reply;
import it.polimi.ingsw.PS19.message.replies.SendFullGameReply;
import it.polimi.ingsw.PS19.message.requests.ElectCouncillorMessage;
import it.polimi.ingsw.PS19.message.requests.SendFullGameMessage;
import it.polimi.ingsw.PS19.model.Model;
import it.polimi.ingsw.PS19.model.map.Region;
import it.polimi.ingsw.PS19.model.parameter.RegionType;

public class TestCompleteModelUpdate {

	@Test
	public void test() 
	{
		/**
		 * In questo test creo una richiesta (che solitamente avviene nel client)
		 * La richiesta la giro al server che ritorna il gioco completo (Testando il model del client)
		 * Creo sul client l'azione di cambiare un consigliere, 
		 * la invio al server che lo cambia e ritorna il model aggiornato.
		 * aggiorno il model e controllo che i dati aggiornati e vecchi del balcone siano coerenti.
		 */
		Model m = new Model(2);
		ClientModel clientModel = new ClientModel(0);
	
		SendFullGameMessage sendFullGame = new SendFullGameMessage(0);
		
		GameController g = new GameController(m);
		
		g.update(null, sendFullGame);
		
		ReplyVisitor visitor = new ReplyVisitorImpl();
		
		ClientUpdate update = g.getReply().display(visitor);
		
		assertTrue(g.getReply() instanceof SendFullGameReply);
		
		assertTrue(update != null);
		
		update.update(clientModel);
		
		assertTrue(clientModel.getActiveplayer() >=0 );
		assertTrue(clientModel.getAllCities() != null);
		assertTrue(clientModel.getAvailablecouncillor() != null);
		assertTrue(clientModel.getKing() != null);
		assertTrue(clientModel.getRegions() != null);
		assertTrue(clientModel.getPlayer() != null);
		
		Region mountain = clientModel.getRegionByType(RegionType.MOUNTAIN);
		List<Color> first = mountain.getBalcony().getCouncilcolor();
		
		ElectCouncillorMessage electCouncillor = new ElectCouncillorMessage(Color.decode("#FF0000"), 
				RegionType.MOUNTAIN);
		electCouncillor.setId(0);
		electCouncillor.setMainAction(true);
		
		g.update(null, electCouncillor);
		
		assertTrue(g.getReply() instanceof ElectCouncillorReply);
		
		Reply electCouncillorReply = g.getReply();
		
		ClientUpdate electCouncillorUpdate = electCouncillorReply.display(visitor);
		
		assertTrue(electCouncillorUpdate instanceof ElectCouncillorUpdate);
		electCouncillorUpdate.update(clientModel);
		
		Region mountainNew = clientModel.getRegionByType(RegionType.MOUNTAIN);
		
		List<Color> second = mountainNew.getBalcony().getCouncilcolor();
		assertTrue(first != second);
		assertTrue(first.get(1).equals(second.get(2)));
		assertTrue(first.get(2).equals(second.get(3)));
		assertTrue(Color.decode("#FF0000").equals(second.get(0)));
	}

}