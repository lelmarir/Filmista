package org.hamisto.userInterface;

import java.util.List;

import javafx.scene.layout.GridPane;

import org.hamisto.database.FilmistaDb;
import org.hamisto.filmista.Serie;

public class Preferiti extends GridPane {

	List<Serie> series;

	static Preferiti instance;

	public static Preferiti getInstance() {
		if (instance == null) {
			instance = new Preferiti();
		}
		return instance;
	}

	private Preferiti() {
		series = FilmistaDb.getInstance().getSeriesData();
		
	}

	public boolean addToPreferiti(Serie s) {
		if (series.contains(s) == false) {
			return series.add(s);
		}
		return false;
	}

	public List<Serie> getSeries() {
		return series;
		
	}
	
	

}
