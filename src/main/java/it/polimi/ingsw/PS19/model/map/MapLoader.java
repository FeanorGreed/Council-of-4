package it.polimi.ingsw.PS19.model.map;


import java.util.ArrayList;
import java.util.Random;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.polimi.ingsw.PS19.model.FileNames;


public class MapLoader {
	//fornisce metodi per leggere file, check di integrità dello stesso, csp delle regioni, ritorna le triplette di regioni configurabili.
	
	int type;
	int id;
	int outRight;
	int outLeft;
	
	private MapLoader(int t, int i, int dx, int sx){
		type=t;
		id=i;
		outRight=dx;
		outLeft=sx;
	}
	

	//change return type to map
	public static Map builder() throws IllegalMapException{
		ArrayList<MapLoader> map;
		ArrayList<ArrayList<City>> regioncitylist;
		
		map = MapLoader.mapFileReading(FileNames.MAP_FILE);
		if(map.isEmpty()){
			throw new IllegalMapException("Incorrect map file!");
		}
		map = MapLoader.findrandomLegalMap(map);
		if(map.isEmpty()){
			throw new IllegalMapException("Incorrect map file!");
		}
		regioncitylist = CityLoader.CitiesReader(map);
		
		return Map.finalMapBuilder(regioncitylist);
	}
	
	
	private static ArrayList<MapLoader> findrandomLegalMap(ArrayList<MapLoader> regionlist){

		ArrayList<MapLoader> legalMap;
		ArrayList<ArrayList<MapLoader>> maplist = new ArrayList<ArrayList<MapLoader>>();
		Random rand = new Random();
		
		for(int i=0; i<3; i++){
			ArrayList<MapLoader> typecountertemp = new ArrayList<MapLoader>();
			for(MapLoader el : regionlist){
				if(el.type==i){
					typecountertemp.add(el);
					
				}
			}
			maplist.add(typecountertemp);
		}
		
		maplist = MapLoader.searchLegalMaps(maplist);
		int k = maplist.size();
		int i = Math.abs(rand.nextInt()%k);
		legalMap = maplist.get(i);
		
		return legalMap;
	}	
	
	
	
	private static ArrayList<ArrayList<MapLoader>> searchLegalMaps(ArrayList<ArrayList<MapLoader>> maplist){
		ArrayList<MapLoader> type1 = maplist.get(0);
		ArrayList<MapLoader> type2 = maplist.get(1);
		ArrayList<MapLoader> type3 = maplist.get(2);
		ArrayList<MapLoader> legal; 
		ArrayList<ArrayList<MapLoader>> allLegals = new ArrayList<ArrayList<MapLoader>>();
		for(MapLoader el1 : type1){
			legal = new ArrayList<MapLoader>();
			legal.add(el1);
			for(MapLoader el2 : type2){
				if(el1.outRight==el2.outLeft){
					legal.add(el2);
					for(MapLoader el3 : type3){
						if(el2.outRight==el3.outLeft){
							legal.add(el3);
							allLegals.add(legal);
						}
					}
				}
			}
			
		}
		return allLegals;
	}
	

		
	
	
	private  static ArrayList<MapLoader> mapFileReading(String xmlfile){
		
		ArrayList<MapLoader> maplist = new ArrayList<MapLoader>();
		
		NodeList nList = FileReader.XMLReader(xmlfile, "region");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) nNode;
					
				int ty=Integer.parseInt(e.getElementsByTagName("type").item(0).getTextContent());
				int id = Integer.parseInt(e.getAttribute("id"));
				int outLeft = Integer.parseInt(e.getElementsByTagName("roadsLeft").item(0).getTextContent());
				int outRight = Integer.parseInt(e.getElementsByTagName("roadsRight").item(0).getTextContent());					
					
				maplist.add(new MapLoader(ty, id, outRight, outLeft));

			}
		}	
			
		return maplist;
			

	}
	
}
