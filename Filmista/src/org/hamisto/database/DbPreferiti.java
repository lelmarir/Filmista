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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

import org.hamisto.filmista.Serie;

public class DbPreferiti {

	private static final String DB_PATH = "DbPreferiti";
	
	static DbPreferiti instance;
	boolean table = true;
	
	public static DbPreferiti getInstance() {
		if (instance == null) {
			instance = new DbPreferiti();
		}
		return instance;
	}
	
	
	public DbPreferiti(){
		// TODO Auto-generated constructor stub
		  
		  try {
			Class.forName("org.h2.Driver");
		
	      Connection conn = DriverManager.getConnection("jdbc:h2:"+ DB_PATH, "sa", "");
	      Statement stat = conn.createStatement();
	      
	      DatabaseMetaData databaseMetaData = conn.getMetaData();
	      
	      
	      String   catalog          = null;
	      String   schemaPattern    = null;
	      String   tableNamePattern = "TABSERIES";
	      String[] types            = null;

	      ResultSet result = databaseMetaData.getTables(
	          catalog, schemaPattern, tableNamePattern, types );

	      while(result.next()) {
	          String tableName = result.getString(3);
	          System.out.println(tableName);
	          if (tableName.equals("TABSERIES") == true){
	        	  
	        	  table = false;
	          }
	        
	      }


	      if (table){
	      String table =  "CREATE TABLE TABSERIES(Id varchar(20), Name varchar(30),Overview text, Image varchar(30))";
	      stat.executeUpdate(table);
	      }
	      
	      stat.close();
	      conn.close();
	      result.close();
	      
	      } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	     public List<Serie> getData(){
	    List<Serie> list = new LinkedList<>();
	    try {
		 Class.forName("org.h2.Driver");
	     Connection conn = DriverManager.getConnection("jdbc:h2:"+ DB_PATH, "sa", "");
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
				;//TODO: caricare immagine di default
			}
	        s.setPoster(image);
	    	list.add(s);
		     
	        /*System.out.println(res.getString("Id"));*/
	        System.out.println(res.getString("Name"));
	        System.out.println(res.getString("Image"));
	        //System.out.println(res.getString("Overview"));
	      }
	      
	      res.close();
	      stat.close();
	      conn.close();
	      
	      return list;
	      
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return null;
	}
	
	
	public static void addToDbPreferiti(Serie s) throws IOException,
			ClassNotFoundException, SQLException {
		  
		 
		 
		 String destinationFile = "image/image-" + s.getId() + ".png";
		 saveImage(s.getPoster(), destinationFile);
		 
		 Class.forName("org.h2.Driver");
	     Connection conn = DriverManager.getConnection("jdbc:h2:"+ DB_PATH, "sa", "");
		 PreparedStatement pstmt =
			      conn.prepareStatement("insert into TABSERIES(Id, Name, Overview, Image) "+ "values(?,?,?,?)");
			      pstmt.setString(1,s.getId());
			      pstmt.setString(2,s.getNome());
			      pstmt.setString(3,s.getOverview());
			      pstmt.setString(4,destinationFile);
			      pstmt.executeUpdate();	  
			      
			     
			      conn.close();
			      pstmt.close();	      
		
	}
	
	public static void saveImage(Image image, String destinationFile) throws IOException {
		//URL url = new URL(imageUrl);
		BufferedImage im = null ;
	    im = SwingFXUtils.fromFXImage(image, im);
	    ImageIO.write(im, "png", new File(destinationFile));
		
	}
	
}
