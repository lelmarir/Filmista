package org.hamisto.userInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



import org.hamisto.filmista.Serie;
import org.hamisto.tabPaneFX.JFXTabPane.Tab;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class TabPreferiti extends Tab {

	private FlowPane mainLayout;
	private FlowPane paneCb;
	private BorderPane border;
	private int lastInsertedSerieIndex = -1;
	private int countLastAdded = 1;
	@SuppressWarnings("rawtypes")
	private ChoiceBox cb;

	static String name;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public TabPreferiti() {
		super("Preferiti");

		mainLayout = new FlowPane();
		cb = new ChoiceBox();
		
	
		border = new BorderPane();
		cb.getItems().addAll("Last Added", "Series Name");
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
					System.out.println("ciao");
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
							
						for (int j = 0;j < list.size(); j++){
								if(list.contains(serie) == true){
									mainLayout.getChildren().add(createSerieElement(list.get(j)));
								}
						    	
						    }
							//mainLayout.getChildren().add(createSerieElement(serie));
						
						}
						//chiusura primo for
						
					
					}//chiusura if
					}//chiusura if Series Name
				
				


				
				
				if (cb.getItems().get((Integer) new_numb).toString()
						.equals("Last Added")) {
					
					System.out.println("ciao2");
                    List<Serie> list2 = Preferiti.getInstance().getSeries();
                    
                    if(countLastAdded == 1){
                    	countLastAdded = countLastAdded + 1;
                    mainLayout.getChildren().clear();
                    lastInsertedSerieIndex = -1;
					if (list2.size() > 0) {

						for (int i = lastInsertedSerieIndex + 1; i < list2.size(); i++) 
						{
							
							Serie serie = Preferiti.getInstance().getSeries().get(i);
						 	mainLayout.getChildren().add(createSerieElement(serie));
						 	System.out.println(list2.get(i).getNome());
							lastInsertedSerieIndex = i;
							System.out.println(lastInsertedSerieIndex);
							
							}
						    //chiusura primo for
						    }
					        //chiusura if list.size()
							//mainLayout.getChildren().add(createSerieElement(serie));
						}
                         //chiusura if countLastAdded == 1
						
				
				
			
                    else{
					
					
					 mainLayout.getChildren().clear();
					 System.out.println("pulisci");
					 lastInsertedSerieIndex = -1;
					
						
							
							for(int i = 0; i < list2.size(); i++) {
								mainLayout.getChildren().add(createSerieElement(list2.get(i)));
								lastInsertedSerieIndex = i;
								System.out.println(list2.get(i).getNome());
							}
						
				}
					
					
				
	
			
				}
			}
			
		});
		
		
		paneCb = new FlowPane(Orientation.HORIZONTAL);
		paneCb.setAlignment(Pos.CENTER_RIGHT);
		paneCb.getChildren().add(cb);
		paneCb.setPadding(new Insets(0, 20, 20, 20));
		border.setTop(paneCb);

		setImage(new Image("img/preferiti.png", 70, 70, true, true, true));

		mainLayout.setOrientation(Orientation.HORIZONTAL);
		mainLayout.setHgap(30);
		mainLayout.setVgap(30);
		// mainLayout.setPadding(new Insets(15, 30, 15, 30));
		border.setCenter(mainLayout);
		border.setPadding(new Insets(22, 30, 30, 30));

		ScrollPane sPane = new ScrollPane();
		sPane.setContent(border);
		sPane.setFitToWidth(true);
		this.setLayout(sPane);
	}

	@Override
	public void afterActivate() {
		super.afterActivate();
		updateTab();
	}

	public void updateTab() {

		
		

	/*	cb.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>() {

			@Override
			public void changed(
					ObservableValue<? extends Number> value,
					Number numb, Number new_numb) {
				// TODO Auto-generated method stub
				if (cb.getItems().get((Integer) new_numb).toString()
						.equals("Series Name")) {
					
					System.out.println("ciao");
                    List<Serie> list = Preferiti.getInstance().getSeries();
					if (list.size() > 0) {

						for (int i = lastInsertedSerieIndex + 1; i < list.size(); i++) {
							Serie serie = Preferiti.getInstance().getSeries().get(i);
							Preferiti.OrderByName(list);
							mainLayout.getChildren().clear();
							
							
							for (int j = 0;j < list.size(); j++){
								if(list.contains(serie) == true){
									mainLayout.getChildren().add(createSerieElement(list.get(j)));
								}
						    	
						    }
							//mainLayout.getChildren().add(createSerieElement(serie));
							lastInsertedSerieIndex = i;
							System.out.println(lastInsertedSerieIndex);
						}
						//chiusura primo for
						
					
					}//chiusura if
					}//chiusura if Series Name
				
				

				}

			
		});
		
		
		
		cb.getSelectionModel().selectedIndexProperty()
		.addListener(new ChangeListener<Number>() {

			@Override
			public void changed(
					ObservableValue<? extends Number> value,
					Number numb, Number new_numb) {
				
				// TODO Auto-generated method stub
				if (cb.getItems().get((Integer) new_numb).toString()
						.equals("Last Added")) {
					System.out.println("ciao2");
                    List<Serie> list = Preferiti.getInstance().getSeries();
                    mainLayout.getChildren().clear();
					if (list.size() > 0) {

						for (int i = lastInsertedSerieIndex + 1; i < list.size(); i++) {
							Serie serie = Preferiti.getInstance().getSeries().get(i);
							mainLayout.getChildren().add(createSerieElement(serie));
							lastInsertedSerieIndex = i;
							}
						     
						    }
							//mainLayout.getChildren().add(createSerieElement(serie));
						}
						//chiusura primo for
						
					
					
					
				
	}
			

			
		});
	*/
	
		
		
		
	 
	}

	private GridPane createSerieElement(Serie serie) {
		GridPane grid = new GridPane();
		grid.setVgap(20);

		Label image;
		final Text title;

		DropShadow dropShadow = new DropShadow();
		dropShadow.setOffsetX(10);
		dropShadow.setOffsetY(10);
		dropShadow.setColor(Color.rgb(50, 50, 50, 0.7));

		image = new Label();
		image.setTooltip(new Tooltip("More Information..."));
		image.setGraphic(new ImageView(serie.getPoster()));
		image.setEffect(dropShadow);
		image.setCursor(Cursor.HAND);

		if (serie.getNome().length() > 12) {
			name = serie.getNome().substring(0, 11);
			name = name.concat("...");
		} else {

			name = serie.getNome();
		}

		title = new Text(name);
		title.setCursor(Cursor.HAND);
		title.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				// change the z-coordinate of the circle
				title.setUnderline(true);
				title.setFill(Color.GRAY);
			}
		});

		title.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				title.setUnderline(false);
				title.setFill(Color.BLACK);
			}
		});
		title.setFont(javafx.scene.text.Font.font("Arial", FontWeight.BOLD, 16));

		grid.add(image, 0, 0);
		grid.setHalignment(title, HPos.CENTER);
		grid.add(title, 0, 1);

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
