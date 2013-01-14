package org.hamisto.database;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;

import org.hamisto.filmista.Serie;
import org.hamisto.filmista.SingleEpisode;
import org.hamisto.filmista.Stagione;
import org.hamisto.filmista.TopElement;
import org.hamisto.userInterface.TabImpostazioni;
import org.transdroid.daemon.Torrent;

import search.daemon.DaemonManager;

public class FilmistaDb {

	private static final String DB_PATH = "FilmistaDb";

	static FilmistaDb instance;
	boolean tablesNotExist = true;
	static Connection conn = null;

	public static FilmistaDb getInstance() {
		if (instance == null) {
			
			instance = new FilmistaDb();
			
		}
		
		return instance;
	}

	public FilmistaDb() {
		// TODO Auto-generated constructor stub

		try {

			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2:" + DB_PATH, "sa", "");
			Statement stat = conn.createStatement();

			DatabaseMetaData databaseMetaData = conn.getMetaData();

			String catalog = null;
			String schemaPattern = null;
			String tableNamePattern = null;
			String[] types = { "TABLE" };

			ResultSet result = databaseMetaData.getTables(catalog,
					schemaPattern, tableNamePattern, types);

			while (result.next()) {
				String tableName = result.getString("TABLE_NAME");
				System.out.println(tableName);
				if (tableName.equals("TABSERIES") == true
						|| tableName.equals("ORDINAMENTO") == true
						|| tableName.equals("TOPSERIES") == true || tableName.equals("TABSETTINGS") == true) {

					tablesNotExist = false;
				}

			}

			if (tablesNotExist) {

				createTables();
			}

			stat.close();
			result.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createTables() {
		// TODO Auto-generated method stub
		try {
			Statement stat = conn.createStatement();

			String tabSeries = "CREATE TABLE TABSERIES(Id varchar(20) NOT NULL,"
					+ "Name varchar(30),"
					+ "Overview text,"
					+ "Image varchar(30)," + "primary key(Id))";
			stat.executeUpdate(tabSeries);

			String tabChoiceBox = "CREATE TABLE ORDINAMENTO(orderBy varchar(30))";
			stat.executeUpdate(tabChoiceBox);

			
			String tabTopSeries = "CREATE TABLE TOPSERIES(Id varchar(20) NOT NULL," +
					"Imdb varchar(30)," +
					"Nome varchar(50)," +
					"Overview text," +
					"Genre varchar(50)," +
					"Runtime varchar(30)," +
					"Status varchar(30)," +
					"Rating varchar (10)," +
					"Image varchar(30)," +
					"primary key(Id))";
			stat.executeUpdate(tabTopSeries);
			
			
			String tabSettings = "CREATE TABLE TABSETTINGS(Choosepath BOOLEAN," +
					"Pathdownload text," +
					"Createfolder BOOLEAN," +
					"Createfolderseason BOOLEAN," +
					"Language BOOLEAN," +
					"Indirizzo text," +
					"Porta varchar(30)," +
					"Enablelanconfig BOOLEAN)";
				
			stat.executeUpdate(tabSettings);
			
			//tabelle torrent
			
			String tabSeason = "CREATE TABLE SEASON(Id int NOT NULL AUTO_INCREMENT," 
					+ "IdSerie varchar(20) NOT NULL,"
					+ "Number varchar(3) NOT NULL,"
					+ "primary key(Id))";
			stat.executeUpdate(tabSeason);

			String tabEpisode = "CREATE TABLE EPISODE(Id int NOT NULL AUTO_INCREMENT," 
					+ "IdSeason int NOT NULL,"
					+ "EpNumber varchar(3) NOT NULL,"
					+ "EpName varchar(50) NOT NULL,"
					+ "TorName varchar(80),"
					+ "primary key(Id))";
			stat.executeUpdate(tabEpisode);
			

			stat.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<Serie> getSeriesData() {

		List<Serie> list = new LinkedList<>();

		try {

			Statement stat = conn.createStatement();
			String query = "SELECT * FROM TABSERIES";
			ResultSet res;
			res = stat.executeQuery(query);

			while (res.next()) {

				Serie s = new Serie();
				s.setId(res.getString("Id"));
				s.setNome(res.getString("Name"));
				String imagePath = res.getString("Image");

				Image image = null;
				try {
					image = new Image(new FileInputStream(imagePath));
				} catch (FileNotFoundException e) {
					;// TODO: caricare immagine di default
				}
				s.setPoster(image);
				list.add(s);

			}

			res.close();
			stat.close();

			return list;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
    
	public void setTabSettings(){
		
		
		try {
		Statement stat = conn.createStatement();
		String query = "SELECT * FROM TABSETTINGS";
		ResultSet res;
		res = stat.executeQuery(query);
		while (res.next()) {
          
			System.out.println(res.getString("Pathdownload"));
			System.out.println(res.getString("Createfolder"));
			System.out.println(res.getString("Choosepath"));
			System.out.println(res.getString("Createfolderseason"));
			System.out.println(res.getString("Language"));
			System.out.println(res.getString("Enablelanconfig"));
			System.out.println(res.getString("Porta"));
			System.out.println(res.getString("Indirizzo"));
			TabImpostazioni.chooser.setInitialDirectory(new File(res.getString("Pathdownload")));
			TabImpostazioni.seriesFolder.setSelected(res.getBoolean("Createfolder"));
			TabImpostazioni.enableDirectoryChooser.setSelected(res.getBoolean("Choosepath"));
			TabImpostazioni.seasonFolder.setSelected(res.getBoolean("Createfolderseason"));
			TabImpostazioni.english.setSelected(res.getBoolean("Language"));
			TabImpostazioni.checkLanConfig.setSelected(res.getBoolean("Enablelanconfig"));
			TabImpostazioni.fieldPorta.setText(res.getString("Porta"));
			TabImpostazioni.fieldIndirizzo.setText(res.getString("Indirizzo"));
			
			

			}
		stat.close();
		res.close();
		

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
	
	public void updateSettingPar(){
		
	
		try {
			
			if(numbRecordSettings() == 0){
			PreparedStatement prest;
		    String sql = "INSERT into TABSETTINGS(Choosepath, Pathdownload, Createfolder, " +
                "Createfolderseason, Language, Indirizzo, Porta , Enablelanconfig)"
                + "values(?,?,?,?,?,?,?,?)";
		 
		    prest = conn.prepareStatement(sql);
	        prest.setBoolean(1, TabImpostazioni.enableDirectoryChooser.isSelected());
	        prest.setString(2, TabImpostazioni.downloadPath.getText().toString());
	        prest.setBoolean(3, TabImpostazioni.seriesFolder.isSelected());
	        prest.setBoolean(4, TabImpostazioni.seasonFolder.isSelected());
	        prest.setBoolean(5, TabImpostazioni.english.isSelected());
	        prest.setString(6, TabImpostazioni.fieldIndirizzo.getText());
	        prest.setString(7, TabImpostazioni.fieldPorta.getText());
	        prest.setBoolean(8, TabImpostazioni.checkLanConfig.isSelected());
	        
	        
		    prest.executeUpdate();
		    prest.close();
			}
			
			else{
			PreparedStatement prest;
			String sql= "UPDATE TABSETTINGS SET Choosepath = ?, " +
					"Pathdownload = ?, " +
					"Createfolder = ?, " +
					"Createfolderseason = ?," +
					"Language = ?," +
					"Indirizzo = ?, " +
					"Porta = ?, " +
					"Enablelanconfig = ?";
			
			prest = conn.prepareStatement(sql);
			prest.setBoolean(1, TabImpostazioni.enableDirectoryChooser.isSelected());
		    prest.setString(2, TabImpostazioni.downloadPath.getText().toString());
		    prest.setBoolean(3, TabImpostazioni.seriesFolder.isSelected());
		    prest.setBoolean(4, TabImpostazioni.seasonFolder.isSelected());
		    prest.setBoolean(5, TabImpostazioni.english.isSelected());
		    prest.setString(6, TabImpostazioni.fieldIndirizzo.getText());
		    prest.setString(7, TabImpostazioni.fieldPorta.getText());
		    prest.setBoolean(8, TabImpostazioni.checkLanConfig.isSelected());
		    prest.executeUpdate();
		    prest.close();
		                
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
		
	}
	public void updateOrdinamentoFilmistaDb(String order) {

		try {
			PreparedStatement prest;
			if (numbRecordOrdinamento() == 0) {
				String sql = "INSERT into ORDINAMENTO(OrderBy)" + "values(?)";
				prest = conn.prepareStatement(sql);
				prest.setString(1, order);
				prest.executeUpdate();
				prest.close();
			} else {

				String sql = "UPDATE ORDINAMENTO SET orderBy =?";
				prest = conn.prepareStatement(sql);
				prest.setString(1, order);
				prest.executeUpdate();
				prest.close();

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
    public int numbRecordSettings(){
    	
    	int count = 0;
		try {

			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM TABSETTINGS");
			while (rs.next()) {
				count++;
			}
			System.out.println("tab settings:" + count);
			stat.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return count;
    	
    }
	public int numbRecordOrdinamento() {

		int count = 0;
		try {

			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("SELECT * FROM ORDINAMENTO");
			while (rs.next()) {
				count++;
			}
			System.out.println(count);
			stat.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return count;

	}
	


	public List<String> setChoiceBox() {

		List<String> items = new ArrayList<String>();

		try {

			Statement stat = conn.createStatement();
			String query = "SELECT * FROM ORDINAMENTO";
			ResultSet res;
			res = stat.executeQuery(query);
			while (res.next()) {

				System.out.println(res.getString("OrderBy"));
				if (res.getString("OrderBy").equals("Last Added") == true) {

					String string = res.getString("OrderBy");
					items.add(0, string);
					items.add(1, "Series Name");
					stat.close();
					res.close();
					return items;

				} else {

					items.add(0, res.getString("OrderBy"));
					items.add(1, "Last Added");
					stat.close();
					res.close();
					return items;

				}

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return items;
	}

	public static void addSeriesToFilmistaDb(Serie s) throws IOException,
			ClassNotFoundException, SQLException {

		String destinationFile = "image/image-" + s.getId() + ".png";
		saveImage(s.getPoster(), destinationFile);

		PreparedStatement pstmt = conn
				.prepareStatement("INSERT into TABSERIES(Id, Name, Overview, Image) "
						+ "values(?,?,?,?)");
		pstmt.setString(1, s.getId());
		pstmt.setString(2, s.getNome());
		pstmt.setString(3, s.getOverview());
		pstmt.setString(4, destinationFile);
		pstmt.executeUpdate();

		pstmt.close();

	}

	public void CloseDb() {

		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void resetTopSeriesDb(){
		
		try{
			  Statement st = conn.createStatement();
			  String sql = "DELETE FROM TOPSERIES";
			  int delete = st.executeUpdate(sql);
			  st.close();
			  if(delete == 0){
			  System.out.println("All rows are completelly deleted!");
			  }
			  }
			  catch(SQLException s){
			  System.out.println("SQL statement is not executed!");
			  }
		
		
	}
	
	public static void view(){
		
		 try{
			 
			  Statement st = conn.createStatement();
			  ResultSet rs = st.executeQuery("SELECT * FROM TOPSERIES");
			  ResultSetMetaData md = rs.getMetaData();
			  int col = md.getColumnCount();
			  System.out.println("Number of Column : "+ col);
			  System.out.println("Columns Name: ");
			  for (int i = 1; i <= col; i++){
			  String col_name = md.getColumnName(i);
			  System.out.println(col_name);
			  }
			  rs.close();
			  st.close();
			  }
			  catch (SQLException s){
			  System.out.println("SQL statement is not executed!");
			  }
		
	}
	public static void addTopElementDb(TopElement element) throws IOException, SQLException{
		
		 
		  
		
		String destinationFile = "imageTop/top-" + element.getId() + ".png";
		System.out.println(destinationFile);
		saveImage(element.getPoster(), destinationFile);

		PreparedStatement pstmt = conn
				.prepareStatement("INSERT into TOPSERIES(Id, Imdb, Nome, Overview, Genre, Runtime, Status, Rating, Image)"
						+ "values(?,?,?,?,?,?,?,?,?)");
		pstmt.setString(1, element.getId());
		pstmt.setString(2, element.getIdImdb());
		pstmt.setString(3, element.getNome());
		pstmt.setString(4, element.getOverview());
		pstmt.setString(5, element.getGenre());
		pstmt.setString(6, element.getRuntime());
		pstmt.setString(7, element.getStatus());
		pstmt.setString(8, element.getRating());
		pstmt.setString(9, destinationFile);
		
		pstmt.executeUpdate();

		pstmt.close();
		
		
	}
	
	
	public List<TopElement> getTopElementData() {

		List<TopElement> list = new LinkedList<>();

		try {

			Statement stat = conn.createStatement();
			String query = "SELECT * FROM TOPSERIES";
			ResultSet res;
			res = stat.executeQuery(query);

			while (res.next()) {

				TopElement element = new TopElement();
				element.setId(res.getString("Id"));
				element.setIdImdb(res.getString("Imdb"));
				element.setNome(res.getString("Nome"));
				element.setOverview(res.getString("Overview"));
				element.setGenre(res.getString("Genre"));
				element.setRuntime(res.getString("Runtime"));
				element.setStatus(res.getString("Status"));
				element.setRating(res.getString("Rating"));
				String imagePath = res.getString("Image");

				Image image = null;
				try {
					image = new Image(new FileInputStream(imagePath));
				} catch (FileNotFoundException e) {
					;// TODO: caricare immagine di default
				}
				element.setPoster(image);
				list.add(element);

			}

			res.close();
			stat.close();

			return list;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public static void saveImage(Image image, String destinationFile)
			throws IOException {

		BufferedImage im = null;
		im = SwingFXUtils.fromFXImage(image, im);
		ImageIO.write(im, "png", new File(destinationFile));
		

	}

	//metodo recupero informazioni download per ogni serie
	
	
	public Serie getData(Serie s) {

		try {
			//Statement stat = conn.createStatement();

			String idSerie = s.getId();

			String query1 = "SELECT * FROM SEASON WHERE IdSerie = '" + idSerie + "'";
			ResultSet res1;
			Statement stat1 = conn.createStatement();
			res1 = stat1.executeQuery(query1);
			System.out.println(res1.toString());
			List<Stagione> stagioni = new ArrayList<Stagione>(); 

			while(res1.next()) {
				Stagione season = new Stagione();
				String numero = res1.getString("Number");
				season.setNumero(numero);
				int idSeason = res1.getInt("Id");

				System.out.println(idSeason + numero);

				String query2 = "SELECT * FROM EPISODE WHERE IdSeason = " + idSeason;
				ResultSet res2;
				Statement stat2 = conn.createStatement();
				res2 = stat2.executeQuery(query2);
				System.out.println(res2.toString());
				ArrayList<SingleEpisode> episodi = new ArrayList<SingleEpisode>();

				while(res2.next()){
					SingleEpisode episode = new SingleEpisode();
					String epNumber = res2.getString("EpNumber");
					episode.setEpisodeNumber(epNumber);

					String epName = res2.getString("EpName");
					episode.setEpisodeName(epName);


					String torName = res2.getString("TorName");

					System.out.println(epNumber + epName + torName);

					if(torName.equals("-1")) episode.setTorrent(null);
					else {
						DaemonManager manager = new DaemonManager();
						List<Torrent> torList = manager.retrieveTorrentList();
						for (int i=0; i<torList.size();i++){
							if (torName.equals(torList.get(i).getName())){
								episode.setTorrent(torList.get(i));
								i=torList.size() + 1;
							}
						}

					}

					episodi.add(episode);
				}
				res2.close();
				season.setEpisodiStagione(episodi);

				stagioni.add(season);
			}
			res1.close();
			s.setStagioni(stagioni);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return s;
	}
	

}
