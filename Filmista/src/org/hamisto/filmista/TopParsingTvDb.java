package org.hamisto.filmista;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class TopParsingTvDb {
	
	
	static List<TopElement>  tops;
	
	private static DocumentBuilder documentBuilder;
	
	private static DocumentBuilder getDocumentBuilder() {
		if (documentBuilder == null) {
			try {
				documentBuilder = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return documentBuilder;
	}
	
	
	
	
	public String GetIdTvDB(String idImdb){
		
		
		
		
		String requestUrl = "http://www.thetvdb.com/api/GetSeriesByRemoteID.php?imdbid=" + idImdb + "&language=en ";
		
		Node idNode = null;
		
		DocumentBuilder db = getDocumentBuilder();
		try {

			Document doc;
			doc = db.parse(requestUrl);
			doc.getDocumentElement().normalize();
			idNode = doc.getElementsByTagName("seriesid").item(0);
			

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String IdTvDB = idNode.toString();
		
		return IdTvDB;
		
		
		
		
	}
	
	
	
	public void CreateTopElement(String IdTvDB){
		
		
		if (tops == null) {
		
		tops = new ArrayList<TopElement>();
	} 
		
		
	}
	
	
	
	

}
