package org.hamisto.filmista;

import java.util.ArrayList;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.layout.VBox;

public class DownloadElementsLayout extends VBox{

	
	ArrayList<SingleEpisode> serie;
	
	
	public DownloadElementsLayout(ArrayList<SingleEpisode> serie){
		
		
		
		this.serie = serie;
		
		
	}
	
	
}
