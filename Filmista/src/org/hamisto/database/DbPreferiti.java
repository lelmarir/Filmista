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
import java.util.LinkedList;
import java.util.List;

import javafx.scene.image.Image;

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
	      String table =  "CREATE TABLE TABSERIES(Id varchar(20), Name varchar(30),Overview text, Image BLOB)";
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
		
	     InputStream imgData = null;
	      
	      while (res.next()) {
	        
	    	Serie s = new Serie();
	    	s.setId(res.getString("Id"));  
	    	s.setNome(res.getString("Name"));
	    	
	    	Blob blob = res.getBlob("Image");
		    imgData = blob.getBinaryStream();
		     
	    	s.setPoster(new Image(imgData, 220, 220, true,
					true));
	    	list.add(s);
		     
	        /*System.out.println(res.getString("Id"));*/
	        System.out.println(res.getString("Name"));
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
		  
		 //poster
		 String imageUrl = s.getPoster().impl_getUrl();
		 String destinationFile = "image/image-" + s.getId() + ".png";
		 saveImage(imageUrl, destinationFile);
		 
		 Class.forName("org.h2.Driver");
	     Connection conn = DriverManager.getConnection("jdbc:h2:"+ DB_PATH, "sa", "");
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
