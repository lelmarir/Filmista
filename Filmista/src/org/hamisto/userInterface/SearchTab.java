package org.hamisto.userInterface;

import java.util.List;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

import org.hamisto.filmista.Serie;
import org.hamisto.filmista.SerieWorkerListener;
import org.hamisto.userInterface.WorkMonitor.WorkListener;

public class SearchTab extends HBox {

	FlowPane searchLayout;// Panel a cui aggiungo la text e la progress bar
	org.hamisto.filmista.SearchBox search;// Barra di ricerca
	ProgressIndicator proIn;// progress bar

	public SearchTab() {
		getStyleClass().add("tab-layout");

		searchLayout = new FlowPane(Orientation.HORIZONTAL);
		searchLayout.setAlignment(Pos.TOP_LEFT);
		searchLayout.setHgap(0);
		{
			search = new org.hamisto.filmista.SearchBox();
			search.setOnKeyReleased(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent arg0) {
					if (arg0.getCode() == KeyCode.ENTER) {
						search((org.hamisto.filmista.SearchBox) arg0
								.getSource());
					}
				}
			});
			
		}
		searchLayout.getChildren().add(search);
		proIn = new ProgressIndicator();
		proIn.setPrefSize(40, 40);
		proIn.setTranslateY(8);
		proIn.setVisible(false);
		searchLayout.getChildren().add(proIn);
		getChildren().add(searchLayout);
	}

	private void search(org.hamisto.filmista.SearchBox searchBox) {
		String searchText = searchBox.getTextBox().getText();
		
		searchBox.getTextBox().setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				proIn.setVisible(false);
			}
		});

		Serie.CreateSeriesListWorker(searchText, new SerieWorkerListener() {

			@Override
			public void WorkerDone(List<Serie> series) {
				System.out.println(series);
				proIn.setProgress(1f);
			}
		}, new WorkMonitor(new WorkListener() {
			@Override
			public void worked(WorkMonitor source) {
				final float progress = source.getProgress();
				System.out.println("Progress:" + progress + "["+source.getCurrentWork()+"/"+source.getMaxWork()+"]");
				Platform.runLater(new Runnable() {
			        @Override
			        public void run() {
			        	proIn.setVisible(true);
			        	proIn.setProgress(progress);
			        }
			   });
				
			}
		}));

	}
}
