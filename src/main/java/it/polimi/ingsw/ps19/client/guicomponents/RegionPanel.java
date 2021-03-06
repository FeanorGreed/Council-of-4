package it.polimi.ingsw.ps19.client.guicomponents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.polimi.ingsw.ps19.client.language.Language;
import it.polimi.ingsw.ps19.model.map.City;
import it.polimi.ingsw.ps19.model.map.Region;

public class RegionPanel extends JPanel implements MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2830688492660763889L;
	
	protected static final String COLORBG = "#55AE3A";
	private static final String INDENTATION = "    ";
	
	private List<DrawCity> citylist;
	private Region myregion;
	private List<Point> starts;
	private List<Point> ends;
	private Language language;
	JFrame info;
	
	/**
	 * create a panel for the received region
	 * @param re
	 * @param l
	 */
	protected RegionPanel(Region re, Language l)
	{
    	super();
    	language = l;
    	addMouseListener(this);
		citylist = new ArrayList<>();
		starts = new ArrayList<>();
		ends = new ArrayList<>();
		myregion = re;
		setVisible(true);
		setToolTipText(language.getRegion() + ": " + language.getString(myregion.getType()));
	}
	
	protected void initialize(){
		setBackground(Color.decode(COLORBG));
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		setLayout(new GridLayout(4, 2));
		
		for(City c : myregion.getCities()){
			citylist.add(new DrawCity(c, language));
		}
		
		for(DrawCity dc : citylist){
			add(dc);
		}
	}
	
	/**
	 * draws roads between the cities
	 */
	protected void drawRoads(){
		for(DrawCity dc : citylist){
			for(DrawCity dc2 : citylist){
				if(dc.getMycity().getNeighbours().contains(dc2.getMycity())){
					Rectangle r = dc.getBounds();
					starts.add(centerOf(r));
					Rectangle r2 = dc2.getBounds();
					ends.add(centerOf(r2));
				}
			}
		}
		repaint();	
	}
	
    /**
     * @param bounds
     * @return the center of the rectangle
     */
    protected static Point centerOf(Rectangle bounds) {
    	return new Point(bounds.x + (bounds.width / 2), bounds.y + (bounds.height / 2));
    }
    
    
	@Override
	public void mouseClicked(MouseEvent e) {
		if(info!=null){
			info.dispatchEvent(new WindowEvent(info, WindowEvent.WINDOW_CLOSING));
		}
		info = createInfo();
		info.setLocationRelativeTo(this);
		info.setAlwaysOnTop(true);
		info.setVisible(true);
	}

	/**
	 * @return a JFrame with the regions infos in text form
	 */
	protected JFrame createInfo(){
		JFrame f = new JFrame(language.getInfo() + language.getString(myregion.getType()));
		f.setSize(500, 250);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setLayout(new GridLayout(8, 1));
		
		//balcony info
		JLabel balconytitle = new JLabel(INDENTATION + language.getBalcony());
		JLabel balcony = new JLabel(INDENTATION+INDENTATION + language.getString(myregion.getBalcony()));
		JLabel title1 = new JLabel(INDENTATION + language.getFirstCard() + ": ");
		JLabel fcardname = new JLabel(INDENTATION+INDENTATION + language.getString(myregion.getFirstcard()));
		JLabel title2 = new JLabel(INDENTATION + language.getSecondCard() + ": ");
		JLabel scardname = new JLabel(INDENTATION+INDENTATION + language.getString(myregion.getSecondcard()));
		balcony.setVisible(true);
		f.add(balconytitle);
		f.add(balcony);
		f.add(title1);
		f.add(fcardname);
		f.add(title2);
		f.add(scardname);
		
		return f;
		
	}
	
    @Override
	public void paint(Graphics g) {
    	super.paint(g);
    	for(int i=0; i<starts.size(); i++){
    		g.drawLine(starts.get(i).x, starts.get(i).y, ends.get(i).x, ends.get(i).y);
    		g.setColor(Color.decode("#7F5217"));
    	}
    }
	
    protected void updateInfo(Region r){
		myregion = r;
		for(City c : myregion.getCities()){
			for(DrawCity dc : citylist){
				if(c.getId()==dc.getMycity().getId()){
					dc.setMycity(c);
				}
			}
		}
		repaint();
    }
    
	@Override
	public void mouseEntered(MouseEvent e) {
		//non usato
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//non usato
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//non usato
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//non usato
	}

	/**
	 * @return the starts
	 */
	public List<Point> getStarts() {
		return starts;
	}

	/**
	 * @return the ends
	 */
	public List<Point> getEnds() {
		return ends;
	}

	/**
	 * @return the citylist
	 */
	public List<DrawCity> getCitylist() {
		return citylist;
	}

	/**
	 * @return the myregion
	 */
	public Region getMyregion() {
		return myregion;
	}

	/**
	 * @param starts the starts to set
	 */
	public void setStarts(List<Point> starts) {
		this.starts = starts;
	}

	/**
	 * @param ends the ends to set
	 */
	public void setEnds(List<Point> ends) {
		this.ends = ends;
	}
	
	
	
}
