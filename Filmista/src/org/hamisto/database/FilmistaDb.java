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
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;

import org.hamisto.filmista.Serie;
import org.hamisto.filmista.Stagione;

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
						|| tableName.equals("ORDINAMENTO") == true) {

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

	public static void saveImage(Image image, String destinationFile)
			throws IOException {

		BufferedImage im = null;
		im = SwingFXUtils.fromFXImage(image, im);
		ImageIO.write(im, "png", new File(destinationFile));

	}

}
