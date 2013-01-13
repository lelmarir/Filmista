package org.hamisto.userInterface;





import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import org.hamisto.database.FilmistaDb;
import org.hamisto.filmista.Serie;
import org.hamisto.filmista.TopElement;
import org.hamisto.filmista.TopParsingTvDb;
import org.hamisto.filmista.TopSeriesWorkerListener;



public class TopSeries extends ScrollPane{

	
	private VBox principal;
	private Button update;
	private GridPane  updategrid;
	private FlowPane flow;
	private StackPane stack;
	
	private HBox hbox1;
	private HBox hbox2;
	private HBox hbox3;
	private HBox hbox4;
	private HBox hbox5;
	
	
	private static final Integer STARTTIME = 0;
	private Integer timeSeconds ;
	
	public TopSeries() {
		// TODO Auto-generated constructor stub
	    principal = new VBox();
		principal.setPadding(new Insets(20));
		
		
		
		Light.Point light = new Light.Point();
	
		light.setX(120);
		light.setY(100);
		light.setZ(80);
		
		Distant distance = new Distant();
		distance.setAzimuth(90);
		distance.setElevation(50);
		 
		 
		final Lighting lighting = new Lighting();
		lighting.setLight(distance);
		lighting.setSurfaceScale(40.0);
		
		
		update  = new Button("Update");
		update.setContentDisplay(ContentDisplay.LEFT);
		update.getStyleClass().add("custom-browse");
		update.setCursor(Cursor.HAND);
		Tooltip tooltip = new Tooltip("Update Series Top");
		update.setTooltip(tooltip);
		
		
		update.addEventHandler(MouseEvent.MOUSE_PRESSED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				
				
				
			}
		});
		update.setGraphic(new ImageView(new Image("img/Repeat.png", 20, 20, true, true, true)));
		
		update.addEventHandler(MouseEvent.MOUSE_RELEASED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
			
				
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
									
									try {
										FilmistaDb.getInstance().addTopElementDb(topseries.get(i));
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								
									System.out.println("NOME: " + topseries.get(i).getNome());
									System.out.println("SHORT OVERVIEW: " + topseries.get(i).getShortOverview());
									System.out.println("GENRE:" + topseries.get(i).getGenre());
									System.out.println("\n\n\n\n");
									
									
								if( i == 0){
									
									CreateTopElementLayout(topseries.get(i));
									
									
								}
								
								else{
									
									
									
									CreateOtherElement(topseries.get(i), i);
								}
									
								}
								
								 hbox1.setVisible(true);
								 hbox2.setVisible(true);
								 hbox3.setVisible(true);
								 hbox4.setVisible(true);
								 hbox5.setVisible(true);
								
								
								
								
							}

							

						
						});

					}
				});
		
			
				
			}
		});
		
		updategrid = new GridPane();
		flow = new FlowPane(Orientation.HORIZONTAL);
		flow.setAlignment(Pos.TOP_RIGHT);
		flow.getChildren().add(update);
	    principal.getChildren().add(updategrid);
		updategrid.setAlignment(Pos.TOP_RIGHT);
		//updategrid.setGridLinesVisible(true);
		updategrid.getColumnConstraints().add(new ColumnConstraints()); 
		updategrid.getColumnConstraints().add(new ColumnConstraints(200)); 
		updategrid.add(flow, 1, 0);
		stack = new StackPane();
		updategrid.add(stack, 0, 0);
		
		updategrid.setVgrow(stack, Priority.ALWAYS);
	
        
		 hbox1 = new HBox();
		 hbox1.setVisible(false);
	     hbox1.getStyleClass().add("topeffect-odd");
	     hbox1.setPadding(new Insets(30, 30, 30, 10));
	     
	     hbox2 = new HBox();
	     hbox2.setVisible(false);
	     hbox2.getStyleClass().add("topeffect-even");
	     hbox2.setPadding(new Insets(30, 30, 30, 10));
	     
	     hbox3 = new HBox();
	     hbox3.setVisible(false);
	     hbox3.getStyleClass().add("topeffect-odd");
	     hbox3.setPadding(new Insets(30, 30, 30, 10));
	     
	     hbox4 = new HBox();
	     hbox4.setVisible(false);
	     hbox4.getStyleClass().add("topeffect-even");
	     hbox4.setPadding(new Insets(30, 30, 30, 10));
	     
	     
	     
		 hbox5 = new HBox();
		 hbox5.setVisible(false);
	     hbox5.getStyleClass().add("topeffect-odd");
	     hbox5.setPadding(new Insets(30, 30, 30, 10));
		
	    
	    principal.getChildren().add(hbox1);
		principal.setMargin(hbox1, new Insets(30, 0, 0, 0));
		principal.getChildren().add(hbox2);
		principal.getChildren().add(hbox3);
		principal.getChildren().add(hbox4);
		principal.getChildren().add(hbox5);
		
		this.setFitToWidth(true);
		this.setContent(principal);
		this.getStyleClass().add("tab-background");
		
		
	}
	
	
	
	private void CreateTopElementLayout(
			TopElement topElement) {
		// TODO Auto-generated method stub
		
		final Label poster = new Label();
		final DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(3.0);
		dropShadow.setOffsetX(2.5);
		dropShadow.setOffsetY(2.5);
		dropShadow.setColor(Color.BLACK); 
		poster.setEffect(dropShadow);
	    ImageView view = new ImageView(topElement.getPoster());
	    view.resize(220, 220);
		poster.setGraphic(view);
		Light.Point light = new Light.Point();
		light.setX(120);
		light.setY(100);
		light.setZ(80);
		

		final Lighting lighting = new Lighting();
		lighting.setLight(light);
		 
		 
		final Popup popup = CreateTopElementPopUp(topElement, poster);
		
		final Timeline timeline = new Timeline();
		final FadeTransition fadeTransition
	    = new FadeTransition(Duration.millis(600));
	    fadeTransition.setNode(popup.getContent().get(0));
		fadeTransition.setNode(popup.getContent().get(1));
	       
			 
			 
			   popup.getContent().get(0).setOnMouseEntered(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent me) {
						
						timeline.stop();
						popup.getContent().get(0).setCursor(Cursor.HAND);
						
					}
				});
			   
			   
			   popup.getContent().get(0).setOnMouseExited(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent me) {
						
			
						timeline.playFromStart();
		
					}
				});
			    
		   
		   popup.getContent().get(1).setOnMouseEntered(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent me) {
					
					timeline.stop();
					popup.getContent().get(1).setCursor(Cursor.HAND);
					
				}
			});
		    
		   
		   popup.getContent().get(1).setOnMouseExited(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent me) {	
				
					timeline.playFromStart();		
					
				}
			});
		    
		   

		   popup.setOnShowing(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {

					 
	                  
						 fadeTransition.setFromValue(0.0);
						 fadeTransition.setToValue(1.0);
						 fadeTransition.play();		

				}
		   });
		  
		  
			
		    poster.setOnMouseEntered(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent me) {
					// change the z-coordinate of the circle
					
					timeline.stop();
					popup.show(ToolsApp.GetStage(), ToolsApp.GetStage().getX() -  50,  ToolsApp.GetStage().getY() );
					System.out.println( (ToolsApp.GetStage().getX() -  30) + "," + (ToolsApp.GetStage().getY() - 20));
					System.out.println(me.getScreenX() + "," + me.getScreenY());
					poster.setCursor(Cursor.HAND);
					poster.setEffect(null);
					poster.setEffect(lighting);
					
				}
			});
		    
		    
		    poster.setOnMouseExited(new EventHandler<MouseEvent>() {

				public void handle(MouseEvent me) {
					
					
					timeline.playFromStart();   
				    poster.setCursor(null);
				    poster.setEffect(null);
				    poster.setEffect(dropShadow);
				    
				    
				    
				}
			});
	        
		    timeline.getKeyFrames().add(
	                new KeyFrame(Duration.seconds(1.2),
	                  new EventHandler() {
	                    // KeyFrame event handler
	                	
						@Override
						public void handle(Event arg0) {
							// TODO Auto-generated method stub
							    
		                        if (timeline.getCurrentTime().greaterThanOrEqualTo(timeline.getTotalDuration())){
		                                   	
			                         timeline.stop();
				                     popup.hide();
			                        	
		                        }       
		                              
						}		
						
	                }));
	        
		    
		    
		    
		Text posizione = new Text("1.");
		posizione.setStyle("-fx-font: 25px Verdana;" +
                      "-fx-fill: #F2EF4B;");
		
		Text nome = new Text(topElement.getNome());
		nome.setFont(Font.font("verdana", FontWeight.BOLD, 30));
		
		final Hyperlink addLink = new Hyperlink("Add To Favorite");
		addLink.setStyle("-fx-font: 16px Verdana;" +
		         "-fx-text-fill: #3B64C4;");
		addLink.setFocusTraversable(false);
		addLink.setCursor(Cursor.HAND);
		addLink.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				
			
					
				 System.out.println("Sto entrando con il mouse");
				 addLink.setStyle(null);
				 addLink.setStyle("-fx-font: 16px Verdana;" +
	    		         "-fx-text-fill: #00FFFF;");
				
			
				

			}
		});
		addLink.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				
			
					
				 System.out.println("Sto uscendo con il mouse");
				 addLink.setStyle(null);
				 addLink.setStyle("-fx-font: 16px Verdana;" +
	    		         "-fx-text-fill: #3B64C4;");
			
				

			}
		});
	    
		
		addLink.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	
		    	Serie s = new Serie();
		        System.out.println("This link is clicked");
		      
		    }
		});
		
		
		
		VBox nameAndLink = new VBox();
		nameAndLink.setAlignment(Pos.TOP_LEFT);
		FlowPane titolo = new FlowPane(Orientation.HORIZONTAL);
		titolo.setAlignment(Pos.TOP_LEFT);
		titolo.getChildren().add(posizione);
		titolo.getChildren().add(nome);
		nameAndLink.getChildren().add(titolo);
		nameAndLink.getChildren().add(addLink);
	
		
		
		
		GridPane gridElement = new GridPane();
		//gridElement.setGridLinesVisible(true);
		gridElement.setHgap(25);
		gridElement.setVgap(25);
		gridElement.setPadding(new Insets(15, 15, 15, 15));
		gridElement.add(poster, 0, 0, 1, 2);
		gridElement.setVgrow(poster, Priority.ALWAYS);
		gridElement.setMargin(poster, new Insets(15, 15, 15, 15));
		gridElement.add(nameAndLink, 1, 0);
		gridElement.setHgrow(nameAndLink, Priority.ALWAYS);
		gridElement.setMargin(nameAndLink, new Insets(15, 15, 15, 15));
	
		
	   
	    TextArea area = new TextArea();
	   
       
	    area.getStyleClass().add("top1-effect");

	
		
		
	    area.setEditable(false);
	    area.setFocusTraversable(false);
	    
	    stack.getChildren().add(area);
	    stack.resize(100, 300);
	    
	    stack.getChildren().add(gridElement);
		
	    
		
		
		
		
		
		
		
		
		
       
		
		
		
		
		
		
		
		
	}
	
	
	private Popup CreateTopElementPopUp (
			TopElement topElement,Label top) {
		
		
		
		
		final Popup pop = new Popup();
	
	    
		
		
		
	
		
		 DropShadow ds = new DropShadow();
		 ds.setOffsetY(3.0f);
		 ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
		
		
		
		Rectangle rect = new Rectangle(280, 350, Color.LIGHTGREY);
		rect.setEffect(ds);
		rect.setArcHeight(20.0);                 
		rect.setArcWidth(20.0);
		
		
		Polygon polygon = new Polygon();
	    polygon.getPoints().addAll(new Double[]{
	        (rect.getWidth() + rect.getX() + 40.0) , (rect.getY() + (rect.getHeight()/2)),
	        (rect.getWidth() + rect.getX()), (rect.getY() + (rect.getHeight()/2) - 20.0),
	        (rect.getWidth() + rect.getX()), (rect.getY() + (rect.getHeight()/2) + 20.0) });
	    polygon.setFill(Color.GREY);
	    
		
	    Shape shape = Shape.union(rect, polygon);
	    shape.setFill(Color.LIGHTGREY);
	    shape.setEffect(ds);
	    
	    
	    FlowPane pane = new FlowPane();
	    pane.setPadding(new Insets(20));
	    Text  nome= new Text(topElement.getNome());
	    nome.setFill(Color.WHITE);
	    nome.setFont(Font.font("Helvetica", 18));
	    pane.getChildren().add(nome);
	    
	    
	 
	    
	    BorderPane border = new BorderPane();
	    border.setTop(pane);
	    border.setPrefWidth(280);
	    String buttonCss = TopSeries.class.getResource("popup.css").toExternalForm();
	 	border.getStylesheets().add(buttonCss);
	 	border.getTop().getStyleClass().add("custom-border");
	  
	  
	    
	    VBox vbox = new VBox();
	    TextArea overview = new TextArea(topElement.getShortOverview());
	    
	    
	    overview.setStyle("-fx-background-color: lightgray;");
	    overview.setPrefHeight(75);
	    overview.setWrapText(true);
	    overview.setEditable(false);
	  
	    vbox.getChildren().add(overview);
	    
	    Label genre = new Label("Genre: ");
	    Label  runtime= new Label("Runtime: ");
	    Label status = new Label(" Status: ");
	    genre.setStyle("-fx-font-weight: bold;");
	    runtime.setStyle("-fx-font-weight: bold;");
	    status.setStyle("-fx-font-weight: bold;");
	    
	    int i = 1;
	    while(topElement.getGenre().charAt(i) != '|'){
	    	
	    	i ++;
	    	
	    }
	    
	    String results = topElement.getGenre().substring(1, i);
	    Label genreval = new Label(results);
	    Label  runtimeval = new Label(topElement.getRuntime());
	    Label statusval = new Label(topElement.getStatus());
	   
	    GridPane grid = new GridPane();
	    grid.add(genre, 0, 0);
	    grid.setMargin(genre, new Insets(10, 0, 0, 32));
	    grid.add(runtime, 0, 1);
	    grid.setMargin(runtime, new Insets(2, 0, 0, 15));
	    grid.add(status, 0, 2);
	    grid.setMargin(status, new Insets(2, 0, 0, 14));
	    
	    
	    grid.add(genreval, 1, 0);
	    grid.setMargin(genreval, new Insets(10, 0, 0, 0));
	    grid.add(runtimeval, 1, 1);
	    grid.setMargin(runtimeval, new Insets(2, 0, 0, 0));
	    grid.add(statusval, 1, 2);
	    grid.setMargin(statusval, new Insets(2, 0, 0, 0));
	    
	    vbox.getChildren().add(grid);
	    border.setCenter(vbox);
	    border.setMargin(vbox, new Insets(20, 0, 0, 0));
	    
	    
	    Line line = new Line(210, 0, 0, 0);
		line.setFill(null);
		line.setStroke(Color.GREY);
		line.setStrokeWidth(1);
		line.setStrokeLineCap(StrokeLineCap.BUTT);
		vbox.getChildren().add(line);
		vbox.setMargin(line, new Insets(35, 0, 0, 20));
		
		HBox hbox = new HBox(7);
	
		
		 
		 
		int valRate = RateToStars(topElement.getRating());
		
		int redStar = valRate/4;
	     for(int j = 0;j < redStar; j++){
			
			Label star = new Label();
			star.setGraphic(new ImageView(new Image("img/icon-star-100.png", 25, 25, true, true, true)));
			
			hbox.getChildren().add(star);
			
			
		}
	
		if( (valRate%4) == 0){
			
			for(int j = 0; j < (valRate-(valRate%4)); j++){
				
				Label star = new Label();
				star.setGraphic(new ImageView(new Image("img/icon-grey-star.png", 25, 25, true, true, true)));
				
				hbox.getChildren().add(star);
			}
		}
		
	 if( ((valRate%4) > 0 ) && ((valRate%4) < 2 ))
	 {
		 
		 Label star = new Label();
		 star.setGraphic(new ImageView(new Image("img/icon-grey-33.png", 25, 25, true, true, true)));
			
		 hbox.getChildren().add(star);
	 }
	 
	 if( ((valRate%4) >= 2 ) && ((valRate%4) <= 2.5 )) {
		 
		 Label star = new Label();
		 star.setGraphic(new ImageView(new Image("img/icon-grey-50.png", 25, 25, true, true, true)));
			
		 hbox.getChildren().add(star);
		 
	 }
	 
 if( ((valRate%4) > 2.5 ) && ((valRate%4) <= 4.9 )) {
		 
		 Label star = new Label();
		 star.setGraphic(new ImageView(new Image("img/icon-star-66.png", 25, 25, true, true, true)));
			
		 hbox.getChildren().add(star);
		 
	 }
	
	
	
	
		
		
		
		System.out.println(valRate);
		
		float ratingNumb = Float.parseFloat(topElement.getRating());
		ratingNumb=ratingNumb/2;
		Text rate = new Text(Float.toString(ratingNumb));
		
		rate.setFont(Font.font("Helvetica", 22));
		rate.setStyle("-fx-font-weight: bold;");
		hbox.getChildren().add(rate);
		
		hbox.setMargin(rate, new Insets(5, 0, 0, 15));
		
		vbox.getChildren().add(hbox);
		vbox.setMargin(hbox, new Insets(15, 0, 0, 20));
	    StackPane stack1 = new StackPane();
	    stack1.getChildren().add(border);
	    
	  
	    
	  
	    
	   pop.getContent().addAll(shape,stack1);
	   
	  
		
		
		
				return pop;
		// TODO Auto-generated method stub
		
		
		
		
		
		
		
	}
	
	     private  int RateToStars(String rating){
		 int starNum;
		 float ratingNumb = Float.parseFloat(rating); 
		 System.out.println(ratingNumb);
		 ratingNumb=ratingNumb/2;
		 System.out.println(ratingNumb);
		 starNum=((int)ratingNumb)*4;
		 System.out.println(starNum);
		 
		 String point = Float.toString(ratingNumb).substring(2);
		 
		 if(point.length() < 2) point=point.concat("0");
		 System.out.println(point);
		 int afterPoint = Integer.parseInt(point); 
		 if (afterPoint>=25) starNum++;
		 if (afterPoint>=50) starNum++;
		 if (afterPoint>=75) starNum++;
		 if (afterPoint>=90) starNum++;
		 
		 
		 System.out.println(starNum);
		 return starNum;
		 
		 }
	     
	     

			private void CreateOtherElement(
					TopElement topElement, int pos) {
				// TODO Auto-generated method stub
				
				
				final Label poster = new Label();
			
				poster.setGraphic(new ImageView(topElement.getPoster()));
			
				final DropShadow dropShadow = new DropShadow();
				dropShadow.setRadius(3.0);
				dropShadow.setOffsetX(2.0);
				dropShadow.setOffsetY(2.0);
				dropShadow.setColor(Color.LIGHTGRAY);
				Light.Point light = new Light.Point();
				light.setX(120);
				light.setY(100);
				light.setZ(80);
				final Lighting lighting = new Lighting();
				lighting.setLight(light);

				poster.setEffect(dropShadow);
				poster.setOnMouseEntered(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent me) {
						// change the z-coordinate of the circle

						poster.setEffect(null);
						poster.setCursor(Cursor.HAND);
						poster.setEffect(lighting);
					}
				});
				poster.setOnMouseExited(new EventHandler<MouseEvent>() {

					public void handle(MouseEvent me) {

						poster.setEffect(null);
						poster.setCursor(null);
						poster.setEffect(dropShadow);

					}
				});

				Text nome = new Text(topElement.getNome());
				nome.setFill(Color.WHITE);
				//nome.setStyle("-fx-font: 14px Verdana;" + "-fx-text-fill: #E3DADE;");

				Text numero = new Text((pos + 1) + ".");
				numero.setFill(Color.WHITE);
				//numero.setStyle("-fx-font: 14px Verdana;" + "-fx-text-fill: #E3DADE;");

				final Hyperlink addLink = new Hyperlink("Add To Favorite");
				addLink.setStyle("-fx-font: 12px Verdana;" + "-fx-text-fill: #528DD9;");
				addLink.setFocusTraversable(false);
				addLink.setCursor(Cursor.HAND);
				addLink.addEventHandler(MouseEvent.MOUSE_ENTERED,
						new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent e) {

								addLink.setStyle(null);
								addLink.setStyle("-fx-font: 12px Verdana;"
										+ "-fx-text-fill: #5D8BF5;");

							}
						});
				addLink.addEventHandler(MouseEvent.MOUSE_EXITED,
						new EventHandler<MouseEvent>() {
							@Override
							public void handle(MouseEvent e) {

								addLink.setStyle(null);
								addLink.setStyle("-fx-font: 12px Verdana;"
										+ "-fx-text-fill: #528DD9;");

							}
						});

				addLink.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent e) {

						System.out.println("This link is clicked");

					}
				});

				// insert element into layouts

				GridPane element = new GridPane();
				FlowPane title = new FlowPane(Orientation.HORIZONTAL);
				VBox vbox = new VBox();

				title.getChildren().add(numero);
				title.getChildren().add(nome);
				vbox.setAlignment(Pos.TOP_LEFT);
				vbox.getChildren().add(title);
				vbox.getChildren().add(addLink);
				element.setHgap(20);
				
				//element.setGridLinesVisible(true);
				element.add(poster, 0, 0, 1, 2);
				element.setVgrow(element, Priority.ALWAYS);
				element.add(vbox, 1, 0);
				
			 

				if(pos < 3 ){
					
					
					hbox1.getChildren().add(element);
					
				}
				else if(pos < 5){
					
					
					hbox2.getChildren().add(element);
					
				}
				
				else if(pos < 7){
					
					
					
					hbox3.getChildren().add(element);
					
				}
				
				else if(pos < 9){
					
					
					hbox4.getChildren().add(element);
					
				}
				 
				else if (pos == 9){
					
					hbox5.getChildren().add(element);
				}
				
			}
			
			
			

	
	
}
