package org.hamisto.userInterface;



import java.awt.Robot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import org.hamisto.database.FilmistaDb;
import org.hamisto.filmista.Serie;


public class TabPreferiti extends ScrollPane{

	private static FlowPane mainLayout;
	private FlowPane paneCb;
	private BorderPane border;
	private static int lastInsertedSerieIndex = -1;
	private static int countLastAdded = 1;
	private Label order;
	@SuppressWarnings("rawtypes")
	static ChoiceBox cb;
	static Robot robot;
	

	static boolean updatedb = false;

	static String name;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TabPreferiti() {
		
		
		
		this.getStyleClass().add("tab-background");
		this.setPrefHeight(700.0); 
		this.setPrefWidth(1035.0);
		//super("Preferiti");

		mainLayout = new FlowPane();
		cb = new ChoiceBox();
		cb.setStyle(" -fx-background-color:" + 
        "#000000," + 
        "linear-gradient(#7ebcea, #2f4b8f)," + 
        "linear-gradient(#426ab7, #263e75)," + 
        "linear-gradient(#395cab, #223768);" + 
        "-fx-text-base-color: white;");
	
		order = new Label();
		border = new BorderPane();

		if (FilmistaDb.getInstance().numbRecordOrdinamento() == 0) {

			cb.getItems().addAll("Last Added", "Series Name");
		} else {

			List<String> items = new ArrayList<String>();
			items = FilmistaDb.getInstance().setChoiceBox();
			cb.getItems().addAll(items);
		}
		

		cb.getSelectionModel().selectFirst();
		cb.getSelectionModel().selectedIndexProperty()
				.addListener(new ChangeListener<Number>() {

					@Override
					public void changed(
							ObservableValue<? extends Number> value,
							Number numb, Number new_numb) {

						// TODO Auto-generated method stub

						if (cb.getItems().get((Integer) new_numb).toString()
								.equals("Series Name")) {

							orderBySeriesName();

						}// chiusura if Series Name

						if (cb.getItems().get((Integer) new_numb).toString()
								.equals("Last Added")) {

							orderByLastAdded();
						}
					}

				});

		order.setTextFill(Color.GREY);
		order.setFont(Font.font("Helvetica", 16));
		order.setText("Order By:");

		paneCb = new FlowPane(Orientation.HORIZONTAL);
		paneCb.setHgap(8);
		paneCb.setAlignment(Pos.CENTER_RIGHT);
		paneCb.getChildren().add(order);
		paneCb.getChildren().add(cb);
		paneCb.setPadding(new Insets(0, 20, 20, 20));
		border.setTop(paneCb);
		

		//setImage(new Image("img/preferiti.png", 70, 70, true, true, true));

		mainLayout.setOrientation(Orientation.HORIZONTAL);
		mainLayout.setHgap(30);
		mainLayout.setVgap(30);
		// mainLayout.setPadding(new Insets(15, 30, 15, 30));
		border.setCenter(mainLayout);
		border.setPadding(new Insets(22, 30, 30, 30));

		
		this.setContent(border);
		this.setFitToWidth(true);
		
		updateTab();
	}

	
	/*public void afterActivate() {
		
		updateTab();
	}*/

	public static void updateTab() {

		if (cb.getSelectionModel().getSelectedItem().toString()
				.equals("Last Added") == true) {

			orderByLastAdded();
	
		}

		if (cb.getSelectionModel().getSelectedItem().toString()
				.equals("Series Name") == true) {

			orderBySeriesName();

		}

	}

	private static void orderBySeriesName() {

		List<Serie> list = new ArrayList<Serie>();
		List<Serie> list3 = Preferiti.getInstance().getSeries();
		lastInsertedSerieIndex = -1;
		if (list3.size() > 0) {

			for (int i = lastInsertedSerieIndex + 1; i < list3.size(); i++) {
				Serie serie = Preferiti.getInstance().getSeries().get(i);

				list.add(serie);

				TabPreferiti.OrderByName(list);
				mainLayout.getChildren().clear();
				lastInsertedSerieIndex = i;
				
				
				System.out.println(serie.getNome());
				System.out.println(lastInsertedSerieIndex);

				for (int j = 0; j < list.size(); j++) {
					if (list.contains(serie) == true) {
						mainLayout.getChildren().add(
								createSerieElement(list.get(j)));
					}

				}
				// mainLayout.getChildren().add(createSerieElement(serie));

			}
			// chiusura primo for

		}// chiusura if
	}

	private static void orderByLastAdded() {

		System.out.println("ciao2");
		List<Serie> list2 = Preferiti.getInstance().getSeries();

		if (countLastAdded == 1) {
			countLastAdded = countLastAdded + 1;
			mainLayout.getChildren().clear();
			lastInsertedSerieIndex = -1;
			if (list2.size() > 0) {

				for (int i = lastInsertedSerieIndex + 1; i < list2.size(); i++) {

					Serie serie = Preferiti.getInstance().getSeries().get(i);
					mainLayout.getChildren().add(createSerieElement(serie));
					System.out.println(list2.get(i).getNome());
					lastInsertedSerieIndex = i;
					System.out.println(lastInsertedSerieIndex);

				}
				// chiusura primo for
			}
			// chiusura if list.size()
			// mainLayout.getChildren().add(createSerieElement(serie));
		}
		// chiusura if countLastAdded == 1

		else {

			mainLayout.getChildren().clear();
			System.out.println("pulisci");
			lastInsertedSerieIndex = -1;

			for (int i = 0; i < list2.size(); i++) {
				mainLayout.getChildren().add(createSerieElement(list2.get(i)));
				lastInsertedSerieIndex = i;
				System.out.println(list2.get(i).getNome());
			}

		}
	}

	@SuppressWarnings("static-access")
	private static GridPane createSerieElement(final Serie serie) {
		final GridPane grid = new GridPane();
		grid.setVgap(20);

		final Label image;
		final Text title;

		
		
		final DropShadow dropShadow = new DropShadow();
		dropShadow.setOffsetX(10);
		dropShadow.setOffsetY(10);
		dropShadow.setColor(Color.rgb(50, 50, 50, 0.7));

		final String style_inner = "-fx-font: Gill Sans;"
				+ "-fx-font-family: Gill Sans;"
				+ "-fx-effect: dropshadow(one-pass-box, black, 8, 0, 4, 4);";

		final String style_inner2 = "-fx-effect: innershadow(one-pass-box, lightgray, 0, 0, 0, 0);";

		image = new Label();
				
		
	
		
		
		
		
		 
		
		image.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				
			
					
			   
				
				
				image.setScaleX(1.115);
				image.setScaleY(1.115);
				image.setStyle(style_inner2);
				image.setCursor(Cursor.HAND);
				

			}
		});
		
	
		image.addEventHandler(MouseEvent.MOUSE_MOVED,
				new EventHandler<MouseEvent>() {
			
	  @Override
      public void handle(MouseEvent e) {
				
		
		  
		  }
				
				
				
			
		});
			

		   
		image.addEventHandler(MouseEvent.MOUSE_RELEASED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
			
						
			}
		});

		image.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				
				image.setScaleX(1);
				image.setScaleY(1);
				image.setStyle(style_inner);
				
				
				
				
				
			}
		});
		
		image.setGraphic(new ImageView(serie.getPoster()));
		// image.setEffect(dropShadow);
		

		image.setStyle(style_inner);
		

		if (serie.getNome().length() > 12) {
			name = serie.getNome().substring(0, 11);
			name = name.concat("...");
		} else {

			name = serie.getNome();
		}

		title = new Text(name);
		title.setFill(Color.WHITE);
		final Tooltip tooltip = new Tooltip(serie.getNome());
	  
		
		
		final Timeline timeline = new Timeline();
		timeline.getKeyFrames().add(
	                new KeyFrame(Duration.seconds(0.5),
	                  new EventHandler() {
	                    // KeyFrame event handler
	                	
						@Override
						public void handle(Event arg0) {
							// TODO Auto-generated method stub
							    
		                        if (timeline.getCurrentTime().greaterThanOrEqualTo(timeline.getTotalDuration())){
		                                   	
			                         timeline.stop();
				                    
			                        	
		                        }       
		                              
						}		
						
	                }));
		
		tooltip.install(title, tooltip);
		
		title.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				
		
			}
		});
		
		
		title.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
			    
				title.setCursor(Cursor.HAND);
				title.setUnderline(true);
				title.setFill(Color.WHITE);
				tooltip.show(title, me.getScreenX(), me.getScreenY() + 11);
			
			}
		});
        
		
	
		
		title.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				
				title.setCursor(null);
				title.setUnderline(false);
				title.setFill(Color.WHITE);
				tooltip.hide();
			}
		});
		title.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 16));

		grid.add(image, 0, 0);
		grid.setHalignment(title, HPos.CENTER);
		grid.add(title, 0, 1);
		
	    
        grid.setOnDragDetected(new EventHandler <MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                /* drag was detected, start drag-and-drop gesture*/
                System.out.println("onDragDetected");
                
                /* allow any transfer mode */
                Dragboard db = grid.startDragAndDrop(TransferMode.MOVE);
               
             
                /* put a string on dragboard */
                ClipboardContent content = new ClipboardContent();
               
                content.putUrl("http://www.thetvdb.com/banners/_cache/posters/95491-1.jpg");
                db.setContent(content);
                System.out.println(db.getUrl());
                event.consume();
            }
        });
        
        grid.setOnDragDone(new EventHandler <DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                System.out.println("onDragDone");
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
                      
                
             
                }
                
                event.consume();
            }
        });

		return grid;
	}

	public static void OrderByName(List<Serie> serie) {
		Collections.sort(serie, new Comparator<Serie>() {
			public int compare(Serie s1, Serie s2) {

				return s1.getNome().compareToIgnoreCase(s2.getNome());
			}
		});

	}
}
