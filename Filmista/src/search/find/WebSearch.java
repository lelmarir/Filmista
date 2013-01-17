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
	public List WebSearch(String query) {
		SearchResult result = null;
		ThePirateBayAdapter adapterPirate = new ThePirateBayAdapter();
		List<SearchResult> listPirate = new ArrayList<SearchResult>();
		List<SearchResult> finalList = new ArrayList<SearchResult>();

		try {
		
			listPirate = adapterPirate.search(query, SortOrder.BySeeders, 10);
			
		
			for(SearchResult r : listPirate){
				String id = r.getDetailsUrl();
				id=id.substring(32, 39);
				String fileListURL="http://thepiratebay.se/ajax_details_filelist.php?id=" + id;
				
			}
	
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		if(!listPirate.isEmpty()) finalList.addAll(listPirate);
		return finalList;
		
	}
}
