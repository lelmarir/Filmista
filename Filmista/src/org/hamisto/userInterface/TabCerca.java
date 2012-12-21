package org.hamisto.userInterface;

import java.util.List;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import org.hamisto.filmista.Serie;
import org.hamisto.filmista.SerieWorkerListener;
import org.hamisto.tabPaneFX.JFXTabPane.Tab;
import org.hamisto.userInterface.WorkMonitor.WorkListener;

public class TabCerca extends Tab {

	FlowPane searchLayout;// Panel a cui aggiungo la text e la progress bar
	org.hamisto.filmista.SearchBox search;// Barra di ricerca
	ProgressIndicator proIn;// progress bar
	VBox seriesContainer;
	
	public TabCerca() {
		super("Cerca");
		setImage(new Image("img/cerca.png", 70, 70, true, true,true));
		
		VBox mainLayout = new VBox();
		mainLayout.getStyleClass().add("tab-layout");

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
		mainLayout.getChildren().add(searchLayout);

		seriesContainer = new VBox(20);
		seriesContainer.setPadding(new Insets(25, 25, 25, 25));
		mainLayout.getChildren().add(seriesContainer);
		
		ScrollPane sPane = new ScrollPane();
		sPane.setContent(mainLayout);
		this.setLayout(sPane);
		
	}

	private void search(org.hamisto.filmista.SearchBox searchBox) {
		String searchText = searchBox.getTextBox().getText();

		searchBox.getTextBox().setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				proIn.setVisible(false);
			}
		});

		final WorkMonitor monitor = new WorkMonitor(new WorkListener() {
			@Override
			public void worked(WorkMonitor source) {
				final float progress = source.getProgress();
				System.out.println("Progress:" + progress + "["
						+ source.getCurrentWork() + "/" + source.getMaxWork()
						+ "]");
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						proIn.setVisible(true);
						proIn.setProgress(progress);
					}
				});

			}
		});
		Serie.CreateSeriesListWorker(searchText, new SerieWorkerListener() {

			@Override
			public void WorkerDone(final List<Serie> series) {
				Platform.runLater(new Runnable() {
					
					
					@Override
					public void run() {
						
						
						seriesContainer.getChildren().clear();
						monitor.addMaxWork(series.size());
						
						//prendo la dimensione del vettore che contiene i preferiti prima di svolgere una
						//nuova ricerca...
						//count2 = Preferiti.GetSeries().size();
						for (Serie s : series) {
							
							seriesContainer.getChildren().add(new SerieInfo(s));
							monitor.work();
						}
						monitor.setMaxWork(monitor.getCurrentWork()+1);//finito
						monitor.work();
						proIn.setProgress(1f);
					}
				});

			}
		}, monitor);

	}
}
