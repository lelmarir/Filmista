package org.hamisto.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.scene.image.Image;

import org.hamisto.filmista.Serie;
import org.hamisto.userInterface.Preferiti;

public class DbPreferiti {

	
	static DbPreferiti instance;
	boolean table = true;
	
	public static DbPreferiti getInstance() throws ClassNotFoundException, SQLException {
		if (instance == null) {
			instance = new DbPreferiti();
		}
		return instance;
	}
	
	
	public DbPreferiti() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated constructor stub
		  
		  Class.forName("org.h2.Driver");
	      String db_file_path = "~/Documents/workspace2/Filmista/DbPreferiti";
	      Connection conn = DriverManager.getConnection("jdbc:h2:"+ db_file_path, "sa", "");
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
	      String table =  "CREATE TABLE TABSERIES(Id varchar(20), Name varchar(30),Overview text, Image BLOB)";
	      stat.executeUpdate(table);
	      }
	      
	      stat.close();
	      conn.close();
	      result.close();
	      
	}
	
	     public static void getData() throws ClassNotFoundException, SQLException{
		
	     Preferiti.getInstance();
		 Class.forName("org.h2.Driver");
	     String db_file_path = "~/Documents/workspace2/Filmista/DbPreferiti";
	     Connection conn = DriverManager.getConnection("jdbc:h2:"+ db_file_path, "sa", "");
	     Statement stat = conn.createStatement();
	     
	     String query = "SELECT * FROM TABSERIES";
	     ResultSet res = stat.executeQuery(query);
	     InputStream imgData = null;
	      
	      while (res.next()) {
	        
	    	Serie s = new Serie();
	    	s.setId(res.getString("Id"));  
	    	s.setNome(res.getString("Name"));
	    	
	    	Blob blob = res.getBlob("Image");
		    imgData = blob.getBinaryStream();
		     
	    	s.setPoster(new Image(imgData, 220, 220, true,
					true));
	    	Preferiti.getInstance().addToPreferiti(s);
		     
	        /*System.out.println(res.getString("Id"));*/
	        System.out.println(res.getString("Name"));
	        //System.out.println(res.getString("Overview"));
	      }
	      
	      stat.close();
	      conn.close();
	      res.close();
	}
	
	
	public static void addToDbPreferiti(Serie s) throws IOException,
			ClassNotFoundException, SQLException {
		  
		 //poster
		 String imageUrl = s.getPoster().impl_getUrl();
		 String destinationFile = "/Users/mohamedchajii/Documents/workspace2/Filmista/Filmista/image/image-" + s.getId() + ".png";
		 saveImage(imageUrl, destinationFile);
		 
		 Class.forName("org.h2.Driver");
	     String db_file_path = "~/Documents/workspace2/Filmista/DbPreferiti";
	     Connection conn = DriverManager.getConnection("jdbc:h2:"+ db_file_path, "sa", "");
		 PreparedStatement pstmt =
			      conn.prepareStatement("insert into TABSERIES(Id, Name, Overview, Image) "+ "values(?,?,?,?)");
			      pstmt.setString(1,s.getId());
			      pstmt.setString(2,s.getNome());
			      pstmt.setString(3,s.getOverview());
			      
			      
			      File file = new File(destinationFile);
			      FileInputStream imageFile = new FileInputStream(file);
			      pstmt.setBinaryStream(4, (InputStream)imageFile, (int)(file.length()));
			      pstmt.executeUpdate();	  
			      
			     
			      conn.close();
			      pstmt.close();	      
		
	}
	
	public static void saveImage(String imageUrl, String destinationFile) throws IOException {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);
		
		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}

	
}
