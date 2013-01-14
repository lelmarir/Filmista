package search.find;


import java.util.ArrayList;
import java.util.List;


import org.hamisto.filmista.Serie;
import org.hamisto.filmista.Stagione;

import search.daemon.DaemonManager;
import org.hamisto.filmista.SingleEpisode;
import org.transdroid.daemon.Torrent;



public class Search {

	public class SearchThread extends Thread{
		
		Serie serie;
		DaemonManager manager;
		String lingua;
		SearchListener listener;
		

		public SearchThread(Serie serie,DaemonManager manager,String lingua, SearchListener listener) {
			// TODO Auto-generated constructor stub

			this.serie=serie;
			this.manager=manager;
			this.lingua=lingua;
			this.listener=listener;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Cerca(this.serie,this.manager,this.lingua);
			System.out.println("ciao");
			done();
		}
		
		protected void done(){
			if(this.listener!=null){
				listener.SearchListener();
			}
		}

		
		
		
		
		

		public Serie Cerca(Serie serie, DaemonManager manager, String lingua){
			manager.PauseAllTorrents();
			String titolo = serie.getNome();
			/**/System.out.println("PRE loadStagioni");
			List<Stagione> stagioni= serie.getStagioni();
			System.out.println("STATIONI ToString = " + stagioni.toString());
			int totStagioni = stagioni.size();
			Torrent torrent = null;
			/**/System.out.println("POST loadStagioni");


			RicercaTorrent ricercaTorrent = new RicercaTorrent();
			for(int i=0;i<=totStagioni-1;i++){
				int numStagione = i+1;

				Stagione stagione = stagioni.get(i);

				System.out.println("STAGIONE ToString = " + stagione.toString());
				/*
				if (stagione.getNumero()!=((Integer)i).toString()){
					int j=0;
					do{
						stagione = stagioni.get(j);
						j++;
					}
					while (stagione.getNumero()!=((Integer)i).toString());
				}

				/**/System.out.println("PRE RicercaTorrent stagione " + (i+1));
				String result1 = ricercaTorrent.RicercaTorrent(titolo, numStagione, lingua);

				if(result1!=null) {


					if(result1.contains("magnet")) torrent = manager.AddMagnet(result1);
					else torrent = manager.AddTorrentUrl(result1);
					manager.PauseAllTorrents();

					System.out.println(torrent.getUniqueID());
					System.out.println(torrent.getName());

					System.out.println("PRE getEpisodi");
					ArrayList<SingleEpisode> episodiSerieCompleta = new ArrayList<SingleEpisode>();
					episodiSerieCompleta = stagione.getEpisodiStagione();
					System.out.println("POST getEpisodi");
					System.out.println(episodiSerieCompleta.isEmpty() + " " + episodiSerieCompleta.size());
					System.out.println(episodiSerieCompleta.toString());
					for(int k=0;k<episodiSerieCompleta.size(); k++) episodiSerieCompleta.get(k).setTorrent(torrent);
					stagione.setEpisodiStagione(episodiSerieCompleta);
				}

				else{
					ArrayList<SingleEpisode> episodiSerie = stagione.getEpisodiStagione();
					System.out.println(episodiSerie.size());
					int totPuntate = episodiSerie.size();
					for (int k = 0;k<=totPuntate-1;k++){
						int numPuntata=k+1;
						SingleEpisode puntata = episodiSerie.get(k);
						
						String result2 = ricercaTorrent.RicercaTorrent(titolo, numStagione, numPuntata, lingua);

						if (result2!=null) {
							if(result2.contains("magnet")) torrent=manager.AddMagnet(result2);
							else torrent=manager.AddTorrentUrl(result2);
							manager.PauseAllTorrents();
							puntata.setTorrent(torrent);
						}
						else puntata.setTorrent(null);
						episodiSerie.set(k, puntata);
					}
					stagione.setEpisodiStagione(episodiSerie);

				}
				stagioni.set(numStagione-1, stagione);
			}
			serie.setStagioni(stagioni);
			manager.ResumeAllTorrents();
			return serie;
		}







		SingleEpisode Cerca(Serie serie, SingleEpisode episodio, Stagione stagione, DaemonManager manager, String lingua){
			Torrent torrent;
			String nome = serie.getNome();
			String numEpisodio = episodio.getEpisodeNumber();
			String numStagione = stagione.getNumero();
			RicercaTorrent ricercaTorrent = new RicercaTorrent();
			String result2 = ricercaTorrent.RicercaTorrent(nome, Integer.parseInt(numStagione), Integer.parseInt(numEpisodio), lingua);
			if (result2!=null) {
				if(result2.contains("magnet")) torrent=manager.AddMagnet(result2);
				else torrent=manager.AddTorrentUrl(result2);
				episodio.setTorrent(torrent);
			}
			else episodio.setTorrent(null);
			return episodio;
		}
	}

	public Search(Serie serie,DaemonManager manager,String lingua, SearchListener listener){
	
		
		new SearchThread(serie, manager, lingua, listener).start();
	}
	
	

}
