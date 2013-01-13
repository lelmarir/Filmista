package org.hamisto.filmista;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.scene.image.Image;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hamisto.database.FilmistaDb;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class TopParsingTvDb {

	
	
	private static class CreateTopsElement extends Thread {
         
		private TopSeriesWorkerListener listener;
		
		
		public CreateTopsElement(TopSeriesWorkerListener listener){
			
			
			this.listener = listener;
			
		
		}
        
		
		protected void done(List<TopElement> topseries) {
			if (this.listener != null) {
				listener.WorkerDone(topseries);
			}

		}
		
		@Override
		public void run() {
			
		
			ExecutorService executorPool = TopParsingTvDb.getThreadPool();
			List<Callable<Object>> tasks = new ArrayList<Callable<Object>>();
			FilmistaDb.getInstance().resetTopSeriesDb();
			
			for (int i = 0; i < (TopParsingImdb.getInstance().Top.size()) && (i < 1); i++) {
               
				final TopElement element = new TopElement();
                  
			    
				String requestUrl = "http://www.thetvdb.com/api/55D4BDC0A1305510/series/"
						+ GetIdTvDB(TopParsingImdb
								.getInstance().Top.get(i)) + "/all/en.xml";

				Node nameNode = null;
				Node overviewNode = null;
				Node genreNode = null;
				Node runtimeNode = null;
				Node statusNode = null;
				Node ratingNode = null;

				DocumentBuilder db = TopParsingTvDb.getDocumentBuilder();
				try {

					Document doc;
					doc = db.parse(requestUrl);

					doc.getDocumentElement().normalize();
					nameNode = doc.getElementsByTagName("SeriesName").item(0);
					overviewNode = doc.getElementsByTagName("Overview").item(0);
					genreNode = doc.getElementsByTagName("Genre").item(0);
					runtimeNode = doc.getElementsByTagName("Runtime").item(0);
					statusNode = doc.getElementsByTagName("Status").item(0);
					ratingNode = doc.getElementsByTagName("Rating").item(0);

				} catch (SAXException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

				// setto parametri oggetto element da inserire nella lista top
				final String id = GetIdTvDB(TopParsingImdb
						.getInstance().Top.get(i));
				
				element.setIdImdb(TopParsingImdb
						.getInstance().Top.get(i));
				System.out.println(element.getIdImdb());
				element.setId(id);
				element.setNome(nameNode.getTextContent());
				element.setOverview(overviewNode.getTextContent());
				element.setGenre(genreNode.getTextContent());
				element.setRuntime(runtimeNode.getTextContent());
				element.setStatus(statusNode.getTextContent());
				element.setRating(ratingNode.getTextContent());
				element.setShortOverview(createShortOverview(overviewNode
						.getTextContent()));
				
				
				Runnable job = new Runnable() {
					@Override
					public void run() {
                        
						System.out.println("id tvdb:" + id + "id Imdb:" + element.getIdImdb());
						element.setPoster(LoadPoster(id));

					}
				};
				
				tasks.add(Executors.callable(job));
	            
			 
				tops.add(element);
				
			}
			
			try {
				executorPool.invokeAll(tasks);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			} finally{			
				executorPool.shutdownNow();
				TopParsingTvDb.clearExecutorPool();
				
			
			}

		done(tops);

	}
		private String createShortOverview(String overview) {
			// TODO Auto-generated method stub
			int i = 151;
			String ris = new String();
			ris = " ";
			char spazio = ' ';
			char term = overview.charAt(overview.length() - 1);
			if (overview.isEmpty() == true) {
				return overview;
			}
			if ((overview.isEmpty() != true) && (overview.length() > 150)) {
				if (overview.charAt(150) == spazio)
					;
				{
					ris = overview.substring(0, 149);
					ris.concat("......");
				}
				if (overview.charAt(150) != spazio) {
					while ((overview.charAt(i) != spazio)
							&& overview.charAt(i) != term) {
						i++;
					}
				}
				if (overview.charAt(i) != term) {
					ris = overview.substring(0, i);
					ris = ris.concat("......");
				}

				if (overview.charAt(i) == term) {
					int k = overview.length();
					k = k - 1;
					while (overview.charAt(k) != spazio) {
						k--;
					}
					ris = overview.substring(0, k);
					ris = ris.concat("......");
					return ris;
				}
			} else if (overview.length() <= 150) {
				return overview;
			}

			return ris;
		}

		private Image LoadPoster(String IdtvDB) {
			// TODO Auto-generated method stub

			

			
			Image image= new Image(
					"http://thetvdb.com/banners/_cache/fanart/original/"
							+ IdtvDB + "-5.jpg", 200, 200, true, true,
					true);

			return image;
		}
		
		
		
		private static String GetIdTvDB( String idImdb) {

		

			String requestUrl = "http://www.thetvdb.com/api/GetSeriesByRemoteID.php?imdbid="
					+ idImdb + "&language=en";
			Node idNode = null;

			DocumentBuilder db = TopParsingTvDb.getDocumentBuilder();
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

			
			return idNode.getTextContent();

		}

	}

	static List<TopElement> tops;
	private static DocumentBuilder documentBuilder;
	private static ExecutorService executorPool;
	
	// get per il thread
		private static ExecutorService getThreadPool() {
			if (executorPool == null) {
				executorPool = Executors.newFixedThreadPool(15);
			}

			return executorPool;

		}
		
		private static void clearExecutorPool(){
			executorPool=null;
		}

		
		
	public TopParsingTvDb(TopSeriesWorkerListener listener ) {

		if (tops == null) {
			
			tops = FilmistaDb.getInstance().getTopElementData();
		}
		
		if (tops.size() == 10){
			
			
		}
		
		else{
			
		
		new CreateTopsElement(listener).start();
		
		}

	}

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



	public static List<TopElement> getTops() {
		return tops;
	}

	public static void setTops(List<TopElement> tops) {
		TopParsingTvDb.tops = tops;
	}

}
