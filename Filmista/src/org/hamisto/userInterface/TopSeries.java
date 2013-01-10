package org.hamisto.userInterface;

import java.util.List;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.Light;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.hamisto.filmista.TopElement;
import org.hamisto.filmista.TopParsingTvDb;
import org.hamisto.filmista.TopSeriesWorkerListener;



public class TopSeries extends VBox{

	private Label update;
	private HBox  updateHbox;
	
	public TopSeries() {
		// TODO Auto-generated constructor stub
		Light.Point light = new Light.Point();
	
		light.setX(120);
		light.setY(100);
		light.setZ(80);
		

		 Distant distance = new Distant();
		 distance.setAzimuth(90);
		 distance.setElevation(60);
		 
		 
		final Lighting lighting = new Lighting();
		lighting.setLight(distance);
		lighting.setSurfaceScale(5.0);
		
		
		update  = new Label();
		update.setCursor(Cursor.HAND);
		Tooltip tooltip = new Tooltip("Update Series Top");
		update.setTooltip(tooltip);
		
		
		update.addEventHandler(MouseEvent.MOUSE_PRESSED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				
				update.setEffect(lighting);
				
			}
		});
		update.setGraphic(new ImageView(new Image("img/SymbolBlackRefresh.png", 80, 80, true, true, true)));
		
		update.addEventHandler(MouseEvent.MOUSE_RELEASED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
			
				update.setEffect(null);
				update.setDisable(true);
				TopParsingTvDb topsSeries = new TopParsingTvDb( new TopSeriesWorkerListener() {
					

					@Override
					public void WorkerDone(final List<TopElement> topseries) {
						Platform.runLater(new Runnable() {
							
							
							@Override
							public void run() {
								
								update.setDisable(false);
								System.out.println(topseries.size());
								
								for (int i = 0 ; i < topseries.size(); i++){
									
								
									System.out.println("NOME: " + topseries.get(i).getNome());
									System.out.println("SHORT OVERVIEW: " + topseries.get(i).getShortOverview());
									System.out.println("GENRE:" + topseries.get(i).getGenre());
									System.out.println("\n\n\n\n");
									
								}
						
							}
						});

					}
				});
		
			
				
			}
		});

		updateHbox = new HBox();
		updateHbox.setAlignment(Pos.TOP_RIGHT);
		updateHbox.setPadding(new Insets(30));
		updateHbox.getChildren().add(update);
		
		
		
		
	   this.getChildren().add(updateHbox);
		
		
		this.getStyleClass().add("tab-background");
	}
}
