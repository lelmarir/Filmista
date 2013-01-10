package org.hamisto.userInterface;




import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
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
import javafx.scene.text.Text;
import javafx.stage.Popup;


import org.hamisto.filmista.TopElement;
import org.hamisto.filmista.TopParsingTvDb;
import org.hamisto.filmista.TopSeriesWorkerListener;



public class TopSeries extends VBox{

	private Label update;
	private HBox  updateHbox;
	private VBox  leftVbox;
	private VBox  rightVbox;
	
	
	public TopSeries() {
		// TODO Auto-generated constructor stub
		
		
		leftVbox = new VBox(30);
		
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
		update.setGraphic(new ImageView(new Image("img/iSync2.png", 100, 100, true, true, true)));
		
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
								CreateTopElementLayout(topseries.get(0));
								
							}

							

						
						});

					}
				});
		
			
				
			}
		});

		updateHbox = new HBox();
		updateHbox.setAlignment(Pos.TOP_CENTER);
		updateHbox.setPadding(new Insets(30));
		updateHbox.getChildren().add(update);
		//updateHbox.getStyleClass().add("top-effect");

		
		
	    
		
		
		this.getStyleClass().add("tab-background");
		this.getChildren().add(updateHbox);
	}
	
	
	
	private void CreateTopElementLayout(
			TopElement topElement) {
		// TODO Auto-generated method stub
		
		final Label poster = new Label();
	    ImageView view = new ImageView(topElement.getPoster());
	    view.resize(220, 220);
		poster.setGraphic(view);
		//poster.setStyle("-fx-effect: innershadow(three-pass-box, gray, 9 , 0.5, 1, 1);");
		Light.Point light = new Light.Point();
		light.setX(120);
		light.setY(100);
		light.setZ(80);
		

		 final Lighting lighting = new Lighting();
		 lighting.setLight(light);
		 
		 
		final Popup popup = CreateTopElementPopUp(topElement);
		
		  poster.setOnMouseEntered(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent me) {
				
					popup.show(ToolsApp.GetStage());
					poster.setCursor(Cursor.HAND);
					poster.setEffect(lighting);
					
				}
			});
		    
		    
		    poster.setOnMouseExited(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent me) {
					
				    popup.hide();
				    poster.setCursor(null);
				    poster.setEffect(null);
				    
				    
				}
			});
		
		Text nome = new Text("1. " + topElement.getNome());
		
		nome.setStyle("-fx-font: 30px Verdana;" +
                      "-fx-fill: white;" +
                      " -fx-stroke: black;" +
                      "-fx-stroke-width: 1;");
		
		final Hyperlink addLink = new Hyperlink("Add To Favorite");
		addLink.setStyle("-fx-font: 16px Verdana;" +
		         "-fx-text-fill: #00E0FF;");
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
	    		         "-fx-text-fill: #00E0FF;");
			
				

			}
		});
	    
		
		addLink.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	
		        System.out.println("This link is clicked");
		      
		    }
		});
		
		
		
		VBox nameAndLink = new VBox();
		nameAndLink.setAlignment(Pos.TOP_LEFT);
		nameAndLink.getChildren().add(nome);
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
		//gridElement.getStyleClass().add("top1-effect");
		
		updateHbox.getChildren().removeAll(update);
		updateHbox.getChildren().add(gridElement);
		updateHbox.getChildren().add(update);
		updateHbox.setMargin(update, new Insets(10, 0, 0, 50));
		
		

		
		
		
		
		
		
		
		
	}
	
	
	private Popup CreateTopElementPopUp (
			TopElement topElement) {
		
		
		
		
		final Popup pop = new Popup();
		pop.setX(200); pop.setY(200);
	    
		//pop.setX(200); pop.setY(200);
		
		
		
	
		
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
	    Text  nome= new Text("Sons of Anarchy (2008)");
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
	    TextArea overview = new TextArea("Sons of Anarchy, aka SAMCRO, is a motorcycle gang that operates both " +
                "illegal and legal businesses in the small town of " +
                "Charming...");
	    
	    
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
	    
	    Label genreval = new Label("Action and Adventure");
	    Label  runtimeval = new Label("60 minutes");
	    Label statusval = new Label("FX");
	   
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
		for(int i = 0;i < 4; i++){
			
			Label star = new Label();
			star.setGraphic(new ImageView(new Image("img/icon-star-100.png", 25, 25, true, true, true)));
			
			hbox.getChildren().add(star);
			
			
		}
		Label star1 = new Label();
		Image stargrey = new Image("img/icon-grey-star.png", 25, 25, true, true, true);
		
		 
		 
		 
		star1.setGraphic(new ImageView(stargrey));
		hbox.getChildren().add(star1);
		Text rate = new Text("4.0");
		
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
	
	
}
