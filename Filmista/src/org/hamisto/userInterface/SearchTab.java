package org.hamisto.userInterface;

import java.awt.List;
import java.util.ArrayList;

import org.hamisto.filmista.Serie;
import org.hamisto.seachBox.SearchBox;
import org.hamisto.tabPaneFX.JFXTabPane;
import org.hamisto.userInterface.WorkMonitor.WorkListener;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

public class SearchTab extends HBox{
	
		FlowPane flow;// Panel a cui aggiungo la text e la progress bar	
		SearchBox search;// Barra di ricerca
		ProgressIndicator proIn;// progress bar
		
	public SearchTab() {
		getStyleClass().add("tab-layout");
		
		flow = new FlowPane(Orientation.HORIZONTAL);
		flow.setAlignment(Pos.TOP_LEFT);
		flow.setHgap(20);
		{
			search = new SearchBox();
			search.setOnKeyReleased(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent arg0) {
					if(arg0.getCode() == KeyCode.ENTER){
						search((SearchBox) arg0.getSource());
					}
				}
			});
			proIn = new ProgressIndicator();
			proIn.setPrefSize(40, 40);
			proIn.setTranslateY(10);
		}		
		flow.getChildren().add(search);
		//flow.getChildren().add(proIn);
		getChildren().add(flow);
	}
	
	private void search(SearchBox searchBox){
		String searchText = searchBox.getTextBox().getText();
		
	   
		Serie.CreateSeriesListWorker(searchText, new WorkMonitor(1,new WorkListener() {
			@Override
			public void worked(WorkMonitor source) {
				// TODO Auto-generated method stub
				System.out.println("Hello World!");
				source.work();
				float count = source.getProgress();
				System.out.println("Progress:" + count);
				//ArrayList<Serie> lista = new ArrayList<Serie>();
				
			     
						}
		}) );
	  
	}
}
