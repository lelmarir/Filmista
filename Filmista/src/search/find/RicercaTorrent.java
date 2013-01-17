package search.find;


import java.util.List;
import org.transdroid.search.SearchResult;

public class RicercaTorrent {

	public String RicercaTorrent(String titoloSerie, int numStagione, int numPuntata, String lingua) {
	
		List<SearchResult> listResult;
		String url = null;
		String stagione = String.format("%02d", numStagione);
		String puntata = String.format("%02d", numPuntata);
		String titolo = this.convertSpaces(this.removeSimbols(titoloSerie));
		String query = titolo + " s" + stagione + "e" + puntata;
		if(lingua!="ENG") query += " " + lingua;

		WebSearch webSearch= new WebSearch(); 
		//url = webSearch.WebSearch(query);
		listResult = webSearch.WebSearch(query);

		//System.out.println("\n\nQUERY = " + query);

		//if(url == null) {
		
		if (! listResult.isEmpty()){
			int i=0;
			boolean go = true;
			SearchResult listItem;
			while(go){
				listItem=listResult.get(i);
				if (this.ResultControl(listItem, titoloSerie, numStagione, numPuntata, lingua))  return url=listItem.getTorrentUrl();
				if (listResult.listIterator(i+1).hasNext()) i++;
				else go = false;
			}
		}
		{
			if (lingua=="ITA") query=titoloSerie + " stagione " + numStagione + " episodio " + numPuntata + " " + lingua;
			else query=titoloSerie + " season " + numStagione + " episode " + numPuntata;
			query= this.convertSpaces(query);
			listResult = webSearch.WebSearch(query);
		}
		if (! listResult.isEmpty()){
			int i=0;
			boolean go = true;
			SearchResult listItem;
			while(go){
				listItem=listResult.get(i);
				if (this.ResultControl(listItem, titoloSerie, numStagione, numPuntata, lingua))  return url=listItem.getTorrentUrl();
				if (listResult.listIterator(i+1).hasNext()) i++;
				else go = false;
			}
		}

		return url;
	}

	public String RicercaTorrent(String titoloSerie, int numStagione, String lingua) {
		//Ricerca singola stagione completa
		List<SearchResult> listResult;
		String url = null;
		//costruzione query ricerca
		String stagione = String.format("%02d", numStagione);
		String titolo = this.convertSpaces(titoloSerie);
		//System.out.println("TITOLO:"+titolo);

		String query = titolo + " s" + stagione;
		if(lingua == "ITA") query += " " + lingua;
		WebSearch webSearch= new WebSearch(); 
		//url = webSearch.WebSearch(query);
		listResult = webSearch.WebSearch(query);

		//if(url == null) {
		if (! listResult.isEmpty()){
			int i=0;
			boolean go = true;
			SearchResult listItem;
			while(go){
				listItem=listResult.get(i);
				if (this.ResultControl(listItem, titoloSerie, numStagione, lingua))  return url=listItem.getTorrentUrl();
				if (listResult.listIterator(i+1).hasNext()) i++;
				else go = false;
			}
		}
		{
			if (lingua=="ITA") query=titoloSerie + " stagione " + stagione + " " + lingua;
			else query=titolo + " season " + stagione;
			//url = webSearch.WebSearch(query);
			listResult = webSearch.WebSearch(query);
		}
		if (! listResult.isEmpty()){
			int i=0;
			boolean go = true;
			SearchResult listItem;
			while(go){
				listItem=listResult.get(i);
				if (this.ResultControl(listItem, titoloSerie, numStagione, lingua))  return url=listItem.getTorrentUrl();
				if (listResult.listIterator(i+1).hasNext()) i++;
				else go = false;
			}
		}
		return url;
	}

	public boolean ResultControl(SearchResult result, String titolo, int numStagione, String lingua){
		//Metodo utilizzato per controllare la coerenza del torrent trovato con ci� che si vuole trovare (titolo, dimensioni,...)
		//System.out.println("\n\nINIZIO CONTROLLO COERENZA");
		//System.out.println("TITOLO RISULTATO:" + result.getTitle());

		//if (lingua!="ITA"){
			if (	(
					result.getTitle().contains(titolo) ||
					result.getTitle().contains(titolo.toUpperCase()) ||
					result.getTitle().contains(titolo.toLowerCase()) ||
					result.getTitle().contains(this.convertSpaces(titolo)) ||
					result.getTitle().toLowerCase().contains(this.convertSpaces(titolo).toLowerCase()) ||
					result.getTitle().toUpperCase().contains(this.convertSpaces(titolo).toUpperCase()) ||
					result.getTitle().contains(this.convertSpaces2(titolo)) ||
					result.getTitle().toLowerCase().contains(this.convertSpaces2(titolo).toLowerCase()) ||
					result.getTitle().toUpperCase().contains(this.convertSpaces2(titolo).toUpperCase()) ||
					result.getTitle().contains(this.removeSimbols(titolo)) ||
					result.getTitle().toLowerCase().contains(this.removeSimbols(titolo).toLowerCase()) ||
					result.getTitle().toUpperCase().contains(this.removeSimbols(titolo).toUpperCase()) ||
					result.getTitle().contains((this.convertSpaces(this.removeSimbols(titolo)))) ||
					result.getTitle().toLowerCase().contains((this.convertSpaces(this.removeSimbols(titolo))).toLowerCase()) ||
					result.getTitle().toUpperCase().contains((this.convertSpaces(this.removeSimbols(titolo))).toUpperCase()) ||
					result.getTitle().contains((this.convertSpaces2(this.removeSimbols(titolo)))) ||
					result.getTitle().toLowerCase().contains((this.convertSpaces2(this.removeSimbols(titolo))).toLowerCase()) ||
					result.getTitle().toUpperCase().contains((this.convertSpaces2(this.removeSimbols(titolo))).toUpperCase()) 
					) 
					&&
					(
							result.getTitle().contains("complete") || 
							result.getTitle().contains("Complete") ||
							result.getTitle().contains("COMPLETE") ||
							(result.getTitle().contains("season") && !result.getTitle().contains("episode")) ||
							(result.getTitle().contains("Season") && !result.getTitle().contains("Episode")) ||
							(result.getTitle().contains("SEASON") && !result.getTitle().contains("EPISODE")) ||
							result.getTitle().contains("full") ||
							result.getTitle().contains("Full") ||
							result.getTitle().contains("FULL") ||
							(
							!result.getTitle().contains("s%de%d")&&
							!result.getTitle().contains("S%de%d")&&
							!result.getTitle().contains("S%dE%d")&&
							!result.getTitle().contains("s%d%de%d%d")&&
							!result.getTitle().contains("S%d%de%d%d")&&
							!result.getTitle().contains("S%d%dE%d%d")&&
							!result.getTitle().contains("s%d_e%d")&&
							!result.getTitle().contains("S%d_e%d")&&
							!result.getTitle().contains("s%d%d_e%d%d")&&
							!result.getTitle().contains("S%d%d_e%d%d")&&
							!result.getTitle().contains("S%d_E%d")&&
							!result.getTitle().contains("S%d%d_E%d%d")&&
							!result.getTitle().contains("S%d E%d")&&
							!result.getTitle().contains("S%d%d E%d%d")&&
							!result.getTitle().contains("s%d e%d")&&
							!result.getTitle().contains("S%d e%d")&&
							!result.getTitle().contains("s%d%d e%d%d")&&
							!result.getTitle().contains("S%d%d e%d%d")&&
							!result.getTitle().contains("S%d%d E%d%d")&&
							!result.getTitle().contains("s%d.e%d")&&
							!result.getTitle().contains("S%d.e%d")&&
							!result.getTitle().contains("s%d%d.e%d%d")&&
							!result.getTitle().contains("S%d%d.e%d%d")&&
							!result.getTitle().contains("S%d.E%d")&&
							!result.getTitle().contains("S%d%d.E%d%d")&&
							//---
							//------
							!result.getTitle().contains("s%dep%d")&&
							!result.getTitle().contains("S%dep%d")&&
							!result.getTitle().contains("s%d%dep%d%d")&&
							!result.getTitle().contains("S%d%dep%d%d")&&
							!result.getTitle().contains("s%d_ep%d")&&
							!result.getTitle().contains("S%d_ep%d")&&
							!result.getTitle().contains("s%d%d_ep%d%d")&&
							!result.getTitle().contains("S%d%d_ep%d%d")&&
							!result.getTitle().contains("S%d_EP%d")&&
							!result.getTitle().contains("S%d%d_EP%d%d")&&
							!result.getTitle().contains("S%d EP%d")&&
							!result.getTitle().contains("S%d%d EP%d%d")&&
							!result.getTitle().contains("S%d_Ep%d")&&
							!result.getTitle().contains("S%d%d_Ep%d%d")&&
							!result.getTitle().contains("S%d Ep%d")&&
							!result.getTitle().contains("S%d%d Ep%d%d")&&
							!result.getTitle().contains("s%d ep%d")&&
							!result.getTitle().contains("S%d ep%d")&&
							!result.getTitle().contains("s%d%d ep%d%d")&&
							!result.getTitle().contains("S%d%d ep%d%d")&&
							!result.getTitle().contains("s%d.ep%d")&&
							!result.getTitle().contains("S%d.ep%d")&&
							!result.getTitle().contains("s%d%d.ep%d%d")&&
							!result.getTitle().contains("S%d%d.ep%d%d")&&
							!result.getTitle().contains("S%d.EP%d")&&
							!result.getTitle().contains("S%d%d.EP%d%d")&&
							!result.getTitle().contains("S%d.Ep%d")&&
							!result.getTitle().contains("S%d%d.Ep%d%d")&&
							//-------------
							!result.getTitle().contains("s.%d.e.%d")&&
							!result.getTitle().contains("S.%d.e.%d")&&
							!result.getTitle().contains("s.%d%d.e.%d%d")&&
							!result.getTitle().contains("S.%d%d.e%d%d")&&
							!result.getTitle().contains("s.%d_e.%d")&&
							!result.getTitle().contains("S.%d_e.%d")&&
							!result.getTitle().contains("s.%d%d_e.%d%d")&&
							!result.getTitle().contains("S.%d%d_e.%d%d")&&
							!result.getTitle().contains("S.%d_E.%d")&&
							!result.getTitle().contains("S.%d%d_E.%d%d")&&
							!result.getTitle().contains("S.%d E.%d")&&
							!result.getTitle().contains("S.%d%d E.%d%d")&&
							!result.getTitle().contains("s.%d e.%d")&&
							!result.getTitle().contains("S.%d e.%d")&&
							!result.getTitle().contains("s.%d%d e.%d%d")&&
							!result.getTitle().contains("S.%d%d e.%d%d")&&
							
							//------
							!result.getTitle().contains("s.%d.ep.%d")&&
							!result.getTitle().contains("S.%d.ep.%d")&&
							!result.getTitle().contains("s.%d%d.ep.%d%d")&&
							!result.getTitle().contains("S.%d%d.ep.%d%d")&&
							!result.getTitle().contains("s.%d_ep.%d")&&
							!result.getTitle().contains("S.%d_ep.%d")&&
							!result.getTitle().contains("s.%d%d_ep.%d%d")&&
							!result.getTitle().contains("S.%d%d_ep.%d%d")&&
							!result.getTitle().contains("S.%d_EP.%d")&&
							!result.getTitle().contains("S.%d%d_EP.%d%d")&&
							!result.getTitle().contains("S.%d EP.%d")&&
							!result.getTitle().contains("S.%d%d EP.%d%d")&&
							!result.getTitle().contains("S.%d_Ep.%d")&&
							!result.getTitle().contains("S.%d%d_Ep.%d%d")&&
							!result.getTitle().contains("S.%d Ep.%d")&&
							!result.getTitle().contains("S.%d%d Ep.%d%d")&&
							!result.getTitle().contains("s.%d ep.%d")&&
							!result.getTitle().contains("S.%d ep.%d")&&
							!result.getTitle().contains("s.%d%d ep.%d%d")&&
							!result.getTitle().contains("S.%d%d ep.%d%d")
							)
							)
					);//System.out.println ("Controllo titolo stagione completa superato");
			else {
				//System.out.println ("Controllo titolo stagione completa FALLITO");
				return false;
			}
			
		//Filtro speciale Lost
		if(titolo.equals("Lost")){
			String girl = "Girl";
			String tapes = "Tapes";
			if(result.getTitle().contains(girl) || result.getTitle().contains(girl.toLowerCase()) || result.getTitle().contains(girl.toUpperCase()) || result.getTitle().contains(tapes) || result.getTitle().contains(tapes.toLowerCase()) || result.getTitle().contains(tapes.toUpperCase())) {
				//System.out.println ("Controllo titolo stagione completa FALLITO");
				return false;
			}
		}
		
		//check size
		

		int indexCut=result.getSize().indexOf(" ");
		//int indexCut=result.getSize().lastIndexOf(".");
		if (result.getSize().contains("."))
			if (result.getSize().lastIndexOf(".") < indexCut) indexCut=result.getSize().lastIndexOf(".");
		
		if (result.getSize().contains("KB") || 
				(result.getSize().contains("MiB") && (Integer.parseInt(result.getSize().substring(0, indexCut) ) < 800) ) ) {
			//System.out.println("Controllo dimensioni FALLITO");
			return false;
		}
		else //System.out.println("Controllo dimensioni superato");
		//cerca numero stagione
		{
			
		}
		String stagione = String.format("%d", numStagione);
		String stagione2 = String.format("%02d", numStagione);
		if (result.getTitle().contains(stagione) || result.getTitle().contains(stagione2)){
			//System.out.println ("Controllo numero stagione completa superato");
			//return true;
		}
		else {
			//System.out.println ("Controllo numero stagione completa FALLITO");
			return false;
		}
		//controllo lingua
		int i;
		String[] lingue={"Italian","Italiano","Francais","French","German","Deutche","Russian","Spanish","Espanol","Espa�ol","Portuguese","Portugu�s"};
		String[] lingueShort={"ITA","IT","FR","FRA","GER","DEU","DE","RU","RUS","SPA","POR","PT","ita","deu","rus",/*"ru",*//*"pt",*/"por"};
		if(lingua=="ITA") i=2;
		else i=0;
		for(;i<lingue.length-1;i++) {
			if(result.getTitle().contains(lingue[i]) || result.getTitle().contains(lingue[i].toLowerCase()) || result.getTitle().contains(lingue[i].toUpperCase())){
				//System.out.println("Controllo lingua FALLITO");
				return false;
			}
		}
		if(lingua=="ITA") i=2;
		else i=0;
		for(;i<lingueShort.length-1;i++)
			if(result.getTitle().contains(lingueShort[i])){
				//System.out.println("Controllo lingua FALLITO");
				return false;
			}
		//System.out.println("Controllo lingua superato");
		//controlla elenco puntate

		return true;



		//else System.out.println ("Controllo stagione completa non passato");
		//System.out.println("\n\n");

		//return false;
	}



	public boolean ResultControl(SearchResult result, String titolo, int numStagione, int numEpisodio, String lingua){
		//Metodo utilizzato per controllare la coerenza del torrent trovato con ci� che si vuole trovare (titolo, dimensioni,...)
		//System.out.println("\n\nINIZIO CONTROLLO COERENZA");
		//System.out.println("TITOLO RISULTATO:" + result.getTitle());

		if (lingua!="ITA"){
			if (	(	
					result.getTitle().contains(titolo) ||
					result.getTitle().contains(titolo.toUpperCase()) ||
					result.getTitle().contains(titolo.toLowerCase()) ||
					result.getTitle().contains(this.convertSpaces(titolo)) ||
					result.getTitle().toLowerCase().contains(this.convertSpaces(titolo).toLowerCase()) ||
					result.getTitle().toUpperCase().contains(this.convertSpaces(titolo).toUpperCase()) ||
					result.getTitle().contains(this.convertSpaces2(titolo)) ||
					result.getTitle().toLowerCase().contains(this.convertSpaces2(titolo).toLowerCase()) ||
					result.getTitle().toUpperCase().contains(this.convertSpaces2(titolo).toUpperCase()) ||
					result.getTitle().contains(this.removeSimbols(titolo)) ||
					result.getTitle().toLowerCase().contains(this.removeSimbols(titolo).toLowerCase()) ||
					result.getTitle().toUpperCase().contains(this.removeSimbols(titolo).toUpperCase()) ||
					result.getTitle().contains((this.convertSpaces(this.removeSimbols(titolo)))) ||
					result.getTitle().toLowerCase().contains((this.convertSpaces(this.removeSimbols(titolo))).toLowerCase()) ||
					result.getTitle().toUpperCase().contains((this.convertSpaces(this.removeSimbols(titolo))).toUpperCase()) ||
					result.getTitle().contains((this.convertSpaces2(this.removeSimbols(titolo)))) ||
					result.getTitle().toLowerCase().contains((this.convertSpaces2(this.removeSimbols(titolo))).toLowerCase()) ||
					result.getTitle().toUpperCase().contains((this.convertSpaces2(this.removeSimbols(titolo))).toUpperCase()) 
					) 
					&&
					(
							!result.getTitle().contains("complete") || 
							!result.getTitle().contains("Complete") ||
							!result.getTitle().contains("COMPLETE") ||
							(result.getTitle().contains("season") && result.getTitle().contains("episode")) ||
							(result.getTitle().contains("Season") && result.getTitle().contains("Episode")) ||
							(result.getTitle().contains("SEASON") && result.getTitle().contains("EPISODE")) ||
							!result.getTitle().contains("full") ||
							!result.getTitle().contains("Full") ||
							!result.getTitle().contains("FULL") ||	
							result.getTitle().contains("s%de%d")||
							result.getTitle().contains("S%de%d")||
							result.getTitle().contains("s%d%de%d%d")||
							result.getTitle().contains("S%d%de%d%d")||
							result.getTitle().contains("s%d_e%d")||
							result.getTitle().contains("S%d_e%d")||
							result.getTitle().contains("s%d%d_e%d%d")||
							result.getTitle().contains("S%d%d_e%d%d")||
							result.getTitle().contains("S%d_E%d")||
							result.getTitle().contains("S%d%d_E%d%d") ||
							result.getTitle().contains("S%d E%d")||
							result.getTitle().contains("S%d%d E%d%d") ||
							result.getTitle().contains("s%d e%d")||
							result.getTitle().contains("S%d e%d")||
							result.getTitle().contains("s%d%d e%d%d")||
							result.getTitle().contains("S%d%d e%d%d")||
							result.getTitle().contains("s%d.e%d")||
							result.getTitle().contains("S%d.e%d")||
							result.getTitle().contains("s%d%d.e%d%d")||
							result.getTitle().contains("S%d%d.e%d%d")||
							result.getTitle().contains("S%d.E%d")||
							result.getTitle().contains("S%d%d.E%d%d")||
							//------
							result.getTitle().contains("s%dep%d")||
							result.getTitle().contains("S%dep%d")||
							result.getTitle().contains("s%d%dep%d%d")||
							result.getTitle().contains("S%d%dep%d%d")||
							result.getTitle().contains("s%d_ep%d")||
							result.getTitle().contains("S%d_ep%d")||
							result.getTitle().contains("s%d%d_ep%d%d")||
							result.getTitle().contains("S%d%d_ep%d%d")||
							result.getTitle().contains("S%d_EP%d")||
							result.getTitle().contains("S%d%d_EP%d%d") ||
							result.getTitle().contains("S%d EP%d")||
							result.getTitle().contains("S%d%d EP%d%d") ||
							result.getTitle().contains("S%d_Ep%d")||
							result.getTitle().contains("S%d%d_Ep%d%d") ||
							result.getTitle().contains("S%d Ep%d")||
							result.getTitle().contains("S%d%d Ep%d%d") ||
							result.getTitle().contains("s%d ep%d")||
							result.getTitle().contains("S%d ep%d")||
							result.getTitle().contains("s%d%d ep%d%d")||
							result.getTitle().contains("S%d%d ep%d%d")||
							result.getTitle().contains("s%d.ep%d")||
							result.getTitle().contains("S%d.ep%d")||
							result.getTitle().contains("s%d%d.ep%d%d")||
							result.getTitle().contains("S%d%d.ep%d%d")||
							result.getTitle().contains("S%d.EP%d")||
							result.getTitle().contains("S%d%d.EP%d%d")||
							result.getTitle().contains("S%d.Ep%d")||
							result.getTitle().contains("S%d%d.Ep%d%d")||
							//-------------
							result.getTitle().contains("s.%d.e.%d")||
							result.getTitle().contains("S.%d.e.%d")||
							result.getTitle().contains("s.%d%d.e.%d%d")||
							result.getTitle().contains("S.%d%d.e%d%d")||
							result.getTitle().contains("s.%d_e.%d")||
							result.getTitle().contains("S.%d_e.%d")||
							result.getTitle().contains("s.%d%d_e.%d%d")||
							result.getTitle().contains("S.%d%d_e.%d%d")||
							result.getTitle().contains("S.%d_E.%d")||
							result.getTitle().contains("S.%d%d_E.%d%d") ||
							result.getTitle().contains("S.%d E.%d")||
							result.getTitle().contains("S.%d%d E.%d%d") ||
							result.getTitle().contains("s.%d e.%d")||
							result.getTitle().contains("S.%d e.%d")||
							result.getTitle().contains("s.%d%d e.%d%d")||
							result.getTitle().contains("S.%d%d e.%d%d")||
							
							//------
							result.getTitle().contains("s.%d.ep.%d")||
							result.getTitle().contains("S.%d.ep.%d")||
							result.getTitle().contains("s.%d%d.ep.%d%d")||
							result.getTitle().contains("S.%d%d.ep.%d%d")||
							result.getTitle().contains("s.%d_ep.%d")||
							result.getTitle().contains("S.%d_ep.%d")||
							result.getTitle().contains("s.%d%d_ep.%d%d")||
							result.getTitle().contains("S.%d%d_ep.%d%d")||
							result.getTitle().contains("S.%d_EP.%d")||
							result.getTitle().contains("S.%d%d_EP.%d%d") ||
							result.getTitle().contains("S.%d EP.%d")||
							result.getTitle().contains("S.%d%d EP.%d%d") ||
							result.getTitle().contains("S.%d_Ep.%d")||
							result.getTitle().contains("S.%d%d_Ep.%d%d") ||
							result.getTitle().contains("S.%d Ep.%d")||
							result.getTitle().contains("S.%d%d Ep.%d%d") ||
							result.getTitle().contains("s.%d ep.%d")||
							result.getTitle().contains("S.%d ep.%d")||
							result.getTitle().contains("s.%d%d ep.%d%d")||
							result.getTitle().contains("S.%d%d ep.%d%d")
							)
					);//System.out.println ("Controllo titolo episodio superato");
			else {
				//System.out.println ("Controllo titolo episodio FALLITO");
				return false;
			}
		}
		else{
			if (	
					(
							result.getTitle().contains(titolo) ||
							result.getTitle().contains(titolo.toUpperCase()) ||
							result.getTitle().contains(titolo.toLowerCase()) ||
							result.getTitle().contains(this.convertSpaces(titolo)) ||
							result.getTitle().toLowerCase().contains(this.convertSpaces(titolo).toLowerCase()) ||
							result.getTitle().toUpperCase().contains(this.convertSpaces(titolo).toUpperCase()) ||
							result.getTitle().contains(this.convertSpaces2(titolo)) ||
							result.getTitle().toLowerCase().contains(this.convertSpaces2(titolo).toLowerCase()) ||
							result.getTitle().toUpperCase().contains(this.convertSpaces2(titolo).toUpperCase()) ||
							result.getTitle().contains(this.removeSimbols(titolo)) ||
							result.getTitle().toLowerCase().contains(this.removeSimbols(titolo).toLowerCase()) ||
							result.getTitle().toUpperCase().contains(this.removeSimbols(titolo).toUpperCase()) ||
							result.getTitle().contains((this.convertSpaces(this.removeSimbols(titolo)))) ||
							result.getTitle().toLowerCase().contains((this.convertSpaces(this.removeSimbols(titolo))).toLowerCase()) ||
							result.getTitle().toUpperCase().contains((this.convertSpaces(this.removeSimbols(titolo))).toUpperCase()) ||
							result.getTitle().contains((this.convertSpaces2(this.removeSimbols(titolo)))) ||
							result.getTitle().toLowerCase().contains((this.convertSpaces2(this.removeSimbols(titolo))).toLowerCase()) ||
							result.getTitle().toUpperCase().contains((this.convertSpaces2(this.removeSimbols(titolo))).toUpperCase()) 
							
							) 
							&&
							(
									!result.getTitle().contains("complete") || 
									!result.getTitle().contains("Complete") ||
									!result.getTitle().contains("COMPLETE") ||
									!result.getTitle().contains("completa") || 
									!result.getTitle().contains("Completa") ||
									!result.getTitle().contains("COMPLETA") ||
									(result.getTitle().contains("season") && result.getTitle().contains("episode")) ||
									(result.getTitle().contains("Season") && result.getTitle().contains("Episode")) ||
									(result.getTitle().contains("SEASON") && result.getTitle().contains("EPISODE")) ||
									(result.getTitle().contains("stagione") && result.getTitle().contains("episodio")) ||
									(result.getTitle().contains("Stagione") && result.getTitle().contains("Episodio")) ||
									(result.getTitle().contains("STAGIONE") && result.getTitle().contains("EPISODIO")) ||
									!result.getTitle().contains("full") ||
									!result.getTitle().contains("Full") ||
									!result.getTitle().contains("FULL") ||
									result.getTitle().contains("s%de%d")||
									result.getTitle().contains("S%de%d")||
									result.getTitle().contains("s%d%de%d%d")||
									result.getTitle().contains("S%d%de%d%d")||
									result.getTitle().contains("s%d_e%d")||
									result.getTitle().contains("S%d_e%d")||
									result.getTitle().contains("s%d%d_e%d%d")||
									result.getTitle().contains("S%d%d_e%d%d")||
									result.getTitle().contains("S%d_E%d")||
									result.getTitle().contains("S%d%d_E%d%d") ||
									result.getTitle().contains("S%d E%d")||
									result.getTitle().contains("S%d%d E%d%d") ||
									result.getTitle().contains("s%d e%d")||
									result.getTitle().contains("S%d e%d")||
									result.getTitle().contains("s%d%d e%d%d")||
									result.getTitle().contains("S%d%d e%d%d")||
									result.getTitle().contains("s%d.e%d")||
									result.getTitle().contains("S%d.e%d")||
									result.getTitle().contains("s%d%d.e%d%d")||
									result.getTitle().contains("S%d%d.e%d%d")||
									result.getTitle().contains("S%d.E%d")||
									result.getTitle().contains("S%d%d.E%d%d")||
									//------
									result.getTitle().contains("s%dep%d")||
									result.getTitle().contains("S%dep%d")||
									result.getTitle().contains("s%d%dep%d%d")||
									result.getTitle().contains("S%d%dep%d%d")||
									result.getTitle().contains("s%d_ep%d")||
									result.getTitle().contains("S%d_ep%d")||
									result.getTitle().contains("s%d%d_ep%d%d")||
									result.getTitle().contains("S%d%d_ep%d%d")||
									result.getTitle().contains("S%d_EP%d")||
									result.getTitle().contains("S%d%d_EP%d%d") ||
									result.getTitle().contains("S%d EP%d")||
									result.getTitle().contains("S%d%d EP%d%d") ||
									result.getTitle().contains("S%d_Ep%d")||
									result.getTitle().contains("S%d%d_Ep%d%d") ||
									result.getTitle().contains("S%d Ep%d")||
									result.getTitle().contains("S%d%d Ep%d%d") ||
									result.getTitle().contains("s%d ep%d")||
									result.getTitle().contains("S%d ep%d")||
									result.getTitle().contains("s%d%d ep%d%d")||
									result.getTitle().contains("S%d%d ep%d%d")||
									result.getTitle().contains("s%d.ep%d")||
									result.getTitle().contains("S%d.ep%d")||
									result.getTitle().contains("s%d%d.ep%d%d")||
									result.getTitle().contains("S%d%d.ep%d%d")||
									result.getTitle().contains("S%d.EP%d")||
									result.getTitle().contains("S%d%d.EP%d%d")||
									result.getTitle().contains("S%d.Ep%d")||
									result.getTitle().contains("S%d%d.Ep%d%d")||
									//-------------
									result.getTitle().contains("s.%d.e.%d")||
									result.getTitle().contains("S.%d.e.%d")||
									result.getTitle().contains("s.%d%d.e.%d%d")||
									result.getTitle().contains("S.%d%d.e%d%d")||
									result.getTitle().contains("s.%d_e.%d")||
									result.getTitle().contains("S.%d_e.%d")||
									result.getTitle().contains("s.%d%d_e.%d%d")||
									result.getTitle().contains("S.%d%d_e.%d%d")||
									result.getTitle().contains("S.%d_E.%d")||
									result.getTitle().contains("S.%d%d_E.%d%d") ||
									result.getTitle().contains("S.%d E.%d")||
									result.getTitle().contains("S.%d%d E.%d%d") ||
									result.getTitle().contains("s.%d e.%d")||
									result.getTitle().contains("S.%d e.%d")||
									result.getTitle().contains("s.%d%d e.%d%d")||
									result.getTitle().contains("S.%d%d e.%d%d")||
									
									//------
									result.getTitle().contains("s.%d.ep.%d")||
									result.getTitle().contains("S.%d.ep.%d")||
									result.getTitle().contains("s.%d%d.ep.%d%d")||
									result.getTitle().contains("S.%d%d.ep.%d%d")||
									result.getTitle().contains("s.%d_ep.%d")||
									result.getTitle().contains("S.%d_ep.%d")||
									result.getTitle().contains("s.%d%d_ep.%d%d")||
									result.getTitle().contains("S.%d%d_ep.%d%d")||
									result.getTitle().contains("S.%d_EP.%d")||
									result.getTitle().contains("S.%d%d_EP.%d%d") ||
									result.getTitle().contains("S.%d EP.%d")||
									result.getTitle().contains("S.%d%d EP.%d%d") ||
									result.getTitle().contains("S.%d_Ep.%d")||
									result.getTitle().contains("S.%d%d_Ep.%d%d") ||
									result.getTitle().contains("S.%d Ep.%d")||
									result.getTitle().contains("S.%d%d Ep.%d%d") ||
									result.getTitle().contains("s.%d ep.%d")||
									result.getTitle().contains("S.%d ep.%d")||
									result.getTitle().contains("s.%d%d ep.%d%d")||
									result.getTitle().contains("S.%d%d ep.%d%d")
									)
					);//System.out.println ("Controllo titolo episodio superato");
			else {
				//System.out.println ("Controllo titolo episodio FALLITO");
				return false;
			}
		}
		
		//Filtro speciale Lost
		if(titolo.equals("Lost")){
			String girl = "Girl";
			String tapes = "Tapes";
			if(result.getTitle().contains(girl) || result.getTitle().contains(girl.toLowerCase()) || result.getTitle().contains(girl.toUpperCase()) || result.getTitle().contains(tapes) || result.getTitle().contains(tapes.toLowerCase()) || result.getTitle().contains(tapes.toUpperCase())) {
				//System.out.println ("Controllo titolo episodio FALLITO");
				return false;
			}
		}

		//check size
		
		int indexCut=result.getSize().indexOf(" ");
		//int indexCut=result.getSize().lastIndexOf(".");
		if (result.getSize().contains("."))
			if (result.getSize().lastIndexOf(".") < indexCut) indexCut=result.getSize().lastIndexOf(".");
		
		if ((result.getSize().contains("KB") || result.getSize().contains("KiB"))
				|| ((result.getSize().contains("MiB") || result.getSize().contains("MB")) && (Integer.parseInt(result.getSize().substring(0, indexCut/*result.getSize().length() - 8*/) ) > 800 || (Integer.parseInt(result.getSize().substring(0, indexCut /*result.getSize().length() - 8*/) ) < 100)) ) 
				|| (result.getSize().contains("GiB") || result.getSize().contains("GB"))
				) {
	
			return false;
		}
		else; 
		String stagione = String.format("%d", numStagione);
		String stagione2 = String.format("%02d", numStagione);
		String episodio = String.format("%d", numEpisodio);
		String episodio2 = String.format("%02d", numEpisodio);

		if ((result.getTitle().contains(stagione) && result.getTitle().contains(episodio)) || (result.getTitle().contains(stagione2) && result.getTitle().contains(episodio2)))
	
		{
			
		}
		else {
			
			return false;
		}
		//controllo lingua
		int i;
		String[] lingue={"Italian","Italiano","Francais","French","German","Deutche","Russian","Spanish","Espanol","Espa�ol","Portuguese","Portugu�s"};
		String[] lingueShort={"ITA","IT","FR","FRA","GER","DEU","DE","RU","RUS","SPA","POR","PT","ita","deu","rus","ru","pt","por"};
		if(lingua=="ITA") i=2;
		else i=0;
		for(;i<lingue.length-1;i++) {
			if(result.getTitle().contains(lingue[i]) || result.getTitle().contains(lingue[i].toLowerCase()) || result.getTitle().contains(lingue[i].toUpperCase())){
				//System.out.println("Controllo lingua FALLITO");
				return false;
			}
		}
		if(lingua=="ITA") i=2;
		else i=0;
		for(;i<lingueShort.length-1;i++)
			if(result.getTitle().contains(lingueShort[i])){
				//System.out.println("Controllo lingua FALLITO");
				return false;
			}
		

		return true;


	}

	private String convertSpaces(String string){
		String string1;
		string1 = string.replace(" ",".");
		return string1;
	}
	
	private String convertSpaces2(String string){
		String string1;
		string1 = string.replace(" ","_");
		return string1;
	}
	
	private String removeSimbols(String string){
		String string1 = string;
		string1.replace(":", "");
		string1.replace(".", "");
		string1.replace(",", "");
		string1.replace(";", "");
		string1.replace("(%s)", "");
		return string1;
	}
	
	
}
