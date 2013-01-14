package search.find;

import java.util.ArrayList;
import java.util.List;

import org.transdroid.search.SearchResult;
import org.transdroid.search.SortOrder;

import org.transdroid.search.ThePirateBay.ThePirateBayAdapter;

public class WebSearch {

	/**
	 * @param args
	 */
	public /*String*/List WebSearch(String query) {
		SearchResult result = null;
		ThePirateBayAdapter adapterPirate = new ThePirateBayAdapter();
		List<SearchResult> listPirate = new ArrayList<SearchResult>();
		List<SearchResult> finalList = new ArrayList<SearchResult>();

		try {
			//query="LOST";
			listPirate = adapterPirate.search(query, SortOrder.BySeeders, 10);
			
			/*--------------INIZIO LINEE DEBUG------------------*/
			System.out.println("\n\nLISTA PIRATEBAY\n");
		
			for(SearchResult r : listPirate){
				System.out.println("\n");
				System.out.println("\n\nTitle: " + r.getTitle());
				System.out.println("Seeds: " + r.getSeeds());
				System.out.println("URL: " + r.getTorrentUrl());
				System.out.println("Size: " + r.getSize());
				System.out.println("Details: " + r.getDetailsUrl());
				String id = r.getDetailsUrl();
				id=id.substring(32, 39);
				System.out.println(id);
				
				String fileListURL="http://thepiratebay.se/ajax_details_filelist.php?id=" + id;
				
			}
			/*--------------FINE LINEE DEBUG------------------*/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		if(!listPirate.isEmpty()) finalList.addAll(listPirate);
		return finalList;
		
	}
}
