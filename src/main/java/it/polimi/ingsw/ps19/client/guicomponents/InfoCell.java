package it.polimi.ingsw.ps19.client.guicomponents;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.polimi.ingsw.ps19.client.ClientGUI;
import it.polimi.ingsw.ps19.client.clientmodel.clientdata.ClientModel;
import it.polimi.ingsw.ps19.client.language.Language;
import it.polimi.ingsw.ps19.model.Player;
import it.polimi.ingsw.ps19.model.bonus.Bonus;
import it.polimi.ingsw.ps19.model.card.BusinessCard;
import it.polimi.ingsw.ps19.model.card.PoliticsCard;
import it.polimi.ingsw.ps19.model.map.NobilityPath;

/**
 * Cell for information
 */
public class InfoCell extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8704625760479949960L;
	
	private static final String INDENTATION = "    ";
	private static final String INDENTATION_2ND_POLITIC ="                                         ";
	
	transient ClientGUI listener;
	
	private List<JLabel> infos;
	private JFrame nobility;
	private ActionTypeChooserPanel actionsType;
	private ActionChooserPanel actions;
	private Language language;
	
	/**
	 * create an infocell of language l
	 * @param l
	 */
	protected InfoCell(Language l)
	{
		language = l;
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		setVisible(true);
		infos = new ArrayList<>();
	}
	
	/**
	 * create an InfoCell for the general informations
	 * @param m informations from the model
	 */
	protected void setInfo(ClientModel m){
		setLayout(new GridLayout(7, 0));
		setToolTipText(language.getInfoGame());
		infos = constructInfoCell(m);
		for(JLabel jl : infos){
			jl.setVisible(true);
			add(jl);
		}
		NobilityButton nb = new NobilityButton(m.getNobilitypath());
		nb.addActionListener(this);
		add(nb);
		
		actionsType = new ActionTypeChooserPanel(language);
		add(actionsType);

		actions = new ActionChooserPanel(language);
		add(actions);
	}
	
	/**
	 * set the actionlistener
	 * @param g actionlistener
	 */
	public void setListerner(ClientGUI g){
		listener=g;
		actionsType.setListener(g);
	}
	
	/**
	 * Enable button for Action type
	 */
	public void enableActionType(){
		actionsType.enableButtons();
	}
	
	/**
	 * Disables buttons for action type
	 */
	public void disableActionType(){
		actionsType.disableButtons();
	}
	
	
	
	/**
	 * @param m updated clientmodel
	 */
	protected void updateInfo(ClientModel m){
		removeAll();
		infos.clear();
		setInfo(m);
	}

	/**
	 * create an infocell for the player information
	 * @param p the player
	 */
	protected void setInfo(Player p){
		setLayout(new GridLayout(8, 0));
		setToolTipText(language.getInfoOthers());
		infos = constructPlayerCell(p);
		for(JLabel jl : infos){
			jl.setVisible(true);
			add(jl);
		}
	}
	
	/**
	 * udpdate player information
	 * @param p the player
	 */
	protected void updateInfo(Player p){
		removeAll();
		infos.clear();
		setInfo(p);
	}
	
	/**
	 * set the information of the own player
	 * @param p the player himself
	 */
	protected void setMyInfo(Player p){
		setLayout(new GridLayout(12, 0));
		setToolTipText(language.getInfoYou());
		infos = constructMyPlayerCell(p);
		for(JLabel jl : infos){
			jl.setVisible(true);
			add(jl);
		}
	}
	
	/**
	 * update infos of the player
	 * @param p the player himself
	 */
	protected void updateMyInfo(Player p){
		removeAll();
		infos.clear();
		setMyInfo(p);
	}
	
	private ArrayList<JLabel> constructInfoCell(ClientModel m){
		ArrayList<JLabel> lst = new ArrayList<>();
		lst.add(new JLabel(INDENTATION + "--- " + language.getInfoGame() +" ---"));
		lst.add(new JLabel(INDENTATION + language.getKing() + ": " + language.getCurrentCity() + ": " + language.getString(m.getKing().getCurrentcity())));
		String s = language.getString(m.getKing().getBalcony());
		lst.add(new JLabel(INDENTATION + language.getBalcony() + ": "+s));
		lst.add(new JLabel(INDENTATION + language.getActivePlayerId() + ": " + m.getActiveplayer()));
		return lst;
	}
	
	private ArrayList<JLabel> constructPlayerCell(Player p){
		ArrayList<JLabel> lst = new ArrayList<>();
		lst.add(new JLabel(INDENTATION + "--- " + language.getInfoPlayer() + ": " + p.getId() + " ---"));
		lst.add(new JLabel(INDENTATION + language.getVictoryPoints() + ": " + p.getVictoryPoints()));
		lst.add(new JLabel(INDENTATION + language.getNobilityPoints() + ": " + p.getNobilityPoints()));
		lst.add(new JLabel(INDENTATION + language.getMoney() + ": " + p.getMoney()));
		lst.add(new JLabel(INDENTATION + language.getNumEmporiaLeft() + ": " + (p.getMaxemporia())));
		lst.add(new JLabel(INDENTATION + language.getFreeBusiness() + ": " + p.getFreebusinesscard().size()));
		lst.add(new JLabel(INDENTATION + language.getPoliticCards() + ": " + p.getPoliticcard().size()));
		lst.add(new JLabel(INDENTATION + language.getHelpers() + ": " + p.getHelpers()));
		return lst;
	}
	
	private ArrayList<JLabel> constructMyPlayerCell(Player p){
		ArrayList<JLabel> lst = new ArrayList<>();
		String s="";
		String reserve= "";
		int count=0;
		lst.add(new JLabel(INDENTATION+"--- " + language.getInfoYou() + " --- "));
		lst.add(new JLabel(INDENTATION + language.getVictoryPoints() + ": " + p.getVictoryPoints()));
		lst.add(new JLabel(INDENTATION + language.getNobilityPoints() + ": " + p.getNobilityPoints()));
		lst.add(new JLabel(INDENTATION + language.getMoney() + ": " + p.getMoney()));
		lst.add(new JLabel(INDENTATION + language.getNumEmporiaLeft() + ": " + (p.getMaxemporia())));
		for(PoliticsCard c : p.getPoliticcard()){
			count++;
			if(count<10){
				s = s.concat(language.getString(c) + ", ");
			}
			else{
				reserve = reserve.concat(language.getString(c) + ", ");
			}
		}
		lst.add(new JLabel(INDENTATION + language.getPoliticCards() + ": " + s));
		if(reserve!=""){
			lst.add(new JLabel(INDENTATION + INDENTATION_2ND_POLITIC + reserve));
		}
		lst.add(new JLabel(INDENTATION + language.getHelpers() + ": " + p.getHelpers()));
		lst.add(new JLabel(INDENTATION + language.getBusinessCards() + ": "));
		for(BusinessCard c : p.getFreebusinesscard()){
			lst.add(new JLabel(INDENTATION+INDENTATION+INDENTATION + language.getString(c)));
		}
		return lst;
		
	}

	private void showNobility(NobilityPath p){
		if(nobility!=null){
			nobility.dispatchEvent(new WindowEvent(nobility, WindowEvent.WINDOW_CLOSING));
		}
		nobility = createNobility(p);
		nobility.setLocationRelativeTo(this);
		nobility.setAlwaysOnTop(true);
		nobility.setVisible(true);
		nobility.setResizable(false);
	}
	
	private JFrame createNobility(NobilityPath p){
		JFrame f = new JFrame("Nobility Path");
		f.setSize(550, 600);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setLayout(new GridLayout(30, 2));
		
		JLabel pos = new JLabel(INDENTATION + "position:");
		JLabel title = new JLabel(language.getBonuses());
		pos.setVisible(true);
		title.setVisible(true);
		
		f.add(pos);
		f.add(title);
		
		for(int i = 0; i <= p.getMaxKey(); i++){
			String str = INDENTATION;
			pos = new JLabel(INDENTATION+i);
			if(p.getBonusByPosition(i)!=null){
				for(Bonus b : p.getBonusByPosition(i)){
					str = str.concat(language.getString(b) + ", ");
				}
				title = new JLabel(str);
			}
			else{
				title = new JLabel(str);
			}
			pos.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			title.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			pos.setVisible(true);
			title.setVisible(true);
			f.add(pos);
			f.add(title);
		}
		f.pack();
		return f;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals(NobilityButton.NOBILITY_COMMAND)){
			showNobility(((NobilityButton) e.getSource()).getPath());
		}
		
	}

	/**
	 * @return the actions
	 */
	public ActionChooserPanel getActions() {
		return actions;
	}

}
