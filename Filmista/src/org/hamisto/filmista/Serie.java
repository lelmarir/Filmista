package org.hamisto.filmista;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.scene.image.Image;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hamisto.userInterface.WorkMonitor;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Serie {

	private static class SerieWorker extends Thread {

		private String serieName;
		private SerieWorkerListener listener;
		private WorkMonitor monitor;

		public SerieWorker(String serieName, SerieWorkerListener listener,
				WorkMonitor monitor) {
			this.serieName = serieName;
			this.listener = listener;
			this.monitor = monitor;
		}

		protected void done(List<Serie> series) {
			if (this.listener != null) {
				listener.WorkerDone(series);
			}

		}

		@Override
		public void run() {
			System.out.println("doInBackground");
			if (monitor == null) {
				monitor = new WorkMonitor(null);
			}
			List<Serie> lista = Serie.createSeriesList(serieName, monitor);
			for (int i = 0; i < lista.size(); i++) {

				System.out.println("\n\n\nId:" + lista.get(i).id + "\nNome:"
						+ lista.get(i).nome);
			}

			done(lista);
		}
	}

	public static void CreateSeriesListWorker(String text,
			SerieWorkerListener listener, WorkMonitor monitor) {
		new SerieWorker(text, listener, monitor).start();
	}

	private static DocumentBuilder documentBuilder;
	private static ExecutorService executorPool;// dichiarazione threadpool per
												// gestire thread
	
	

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

	public static List<Serie> createSeriesList(String seriesName,
			final WorkMonitor monitor) {
		System.out.println("create");
		monitor.setMaxWork(20);
		monitor.work(0);
		ArrayList<Serie> listaSerie = new ArrayList<Serie>();

		String requestUrl = "http://www.thetvdb.com/api/GetSeries.php?seriesname="
				+ seriesName;
		System.out.println(requestUrl);
		NodeList idNodeList = null;
		NodeList nameNode = null;
		NodeList overviewNode = null;

		DocumentBuilder db = getDocumentBuilder();
		try {

			Document doc;// ottengo un file tipo documento vuoto
			doc = db.parse(requestUrl);// ottengo tramite il metodo parse sempre
			// un file di tipo document ma in questo caso nn vuoto come prima
			// ma costituente la radice del file XML
			doc.getDocumentElement().normalize();
			idNodeList = doc.getElementsByTagName("seriesid");
			nameNode = doc.getElementsByTagName("SeriesName");
			overviewNode = doc.getElementsByTagName("Overview");
			// bannerNode=doc.getElementsByTagName()

		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		monitor.work();
		// salvo in una variabile il threadpool
		ExecutorService executorPool = getThreadPool();

		monitor.setMaxWork(1 + idNodeList.getLength() * 2);
		List<Callable<Object>> tasks = new ArrayList<Callable<Object>>();
		for (int i = 0; (i <= idNodeList.getLength()) && (i < 14); i++) {// stampa tutti nomi
															// e descrizione

			if (idNodeList.item(i) != null && nameNode.item(i) != null
					&& overviewNode.item(i) != null) {

				final Serie s = new Serie();
				s.setId(idNodeList.item(i).getTextContent());
				s.setNome(nameNode.item(i).getTextContent());
				s.setOverview(overviewNode.item(i).getTextContent());
				// s.loadBanner();

				// creo il job e gli dico cosa fare tramiteil metodo runnable
				Runnable job = new Runnable() {
					@Override
					public void run() {

						s.loadStagioni();
						monitor.work();
					}
				};
				tasks.add(Executors.callable(job));
				Runnable job1 = new Runnable() {
					@Override
					public void run() {
						s.loadBanner();
						monitor.work();
					}
				};
				tasks.add(Executors.callable(job1));
            
				listaSerie.add(s);

			}
		}

		try {
			executorPool.invokeAll(tasks);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally{			
			executorPool.shutdownNow();
			clearExecutorPool();
		}

		return listaSerie;

	}

	private String id;
	private String nome;
	private String overview;
	private String shortOverview;
	private List<Stagione> stagioni;
	// banner
	private Image poster;

	

	public Serie() {
		
	}

	public Serie(String id) {// con questo costruttore lavoro sugli oggetti
		// serie tramite solo l'id
		this.id = id;
	}

	// algoritmo per spezzare il contenuto di una descrizione
	public String getShortOverview() {
		if (this.shortOverview == null) {
			this.shortOverview = createShortOverview();
		}
		return this.shortOverview;
	}

	public void setShortOverview(String shortOverview) {
		this.shortOverview = shortOverview;
	}

	private String createShortOverview() {
		int i = 151;
		String ris = new String();
		ris = " ";
		char spazio = ' ';// se nell'if al posto della variabile metto
							// direttamente lo spazio mi da errore
		char term = overview.charAt(overview.length() - 1);// in java nn esiste
															// il caratere
															// terminatore /0
		if (overview.isEmpty() == true) {
			return overview;
		}
		if ((overview.isEmpty() != true) && (overview.length() > 150)) {
			if (overview.charAt(150) == spazio)
				;
			{
				ris = overview.substring(0, 149);// copia in ris overview
													// dall'indice 0 al
													// trentesimo
				ris.concat("......");// concateno stringhe
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public List<Stagione> getStagioni() {
		if (stagioni == null) {
			loadStagioni();
		}
		return stagioni;
	}
	
	public void setStagioni( List<Stagione> stagioni){
		
		this.stagioni = stagioni;
	}

	public Image getPoster() {
		return poster;
	}
	
	public void setPoster(Image poster) {
		this.poster = poster;
	}

	// banners->nn devi salvarlo...

	public Image loadBanner() {

		URL u1 = null;
		javafx.scene.image.Image image = null;
		
		try {
			u1 = new URL("http://www.thetvdb.com/banners/_cache/posters/"
					+ this.getId() + "-1.jpg");
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// setto immagine postert
		try {
			if (u1.openConnection().getContentLength() > 0) {
				Image image2 = new Image(
						"http://www.thetvdb.com/banners/_cache/posters/"
								+ this.getId() + "-1.jpg", 220, 220, true,
						true);
				image = image2;
				
				System.out.println(u1);
			}

			else{
				Image image3 = new Image("img/Imagenotfound_v2.png", 220, 220, true,
						true);
				image = image3;
			} 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.poster = image;

		return this.poster;
	}

	public List<Stagione> loadStagioni() {

		// nn posso creare un istanza di un interfaccia
		this.stagioni = new ArrayList<Stagione>();

		URL u;
		try {
			u = new URL("http://www.thetvdb.com/api/55D4BDC0A1305510/series/"
					+ this.getId() + "/all/en.xml");
		     
			InputStream is = u.openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line;
			final String SEASON_NUMBER = "<SeasonNumber>";
			final int SEASON_NUMBER_LENGHT = SEASON_NUMBER.length();
			String preNum = null;
			final String ZERO = "0";
			while ((line = br.readLine()) != null) {
				int index = line.indexOf(SEASON_NUMBER);
				if (index < 0) {// not found
					continue;
				}
				int sNumber = index + SEASON_NUMBER_LENGHT;
				String number = line.substring(sNumber,
						line.indexOf("<", sNumber - 1));

				if (number.equals(ZERO)) {
					continue;
				}
				if (preNum != null && preNum.equals(number)) {// discard same
																// sequential
																// number
					continue;
				}
				preNum = number;

				//Stagione st1 = new Stagione(number);
				Stagione st1 = new Stagione(this, number);

				this.stagioni.add(st1);
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return this.stagioni;

	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Serie){
			return this.getId().equals(((Serie) obj).getId());
		}else{
			return false;
		}
	}
	@Override
	public int hashCode() {
		return this.getId().hashCode();
	}
}
