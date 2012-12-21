package org.hamisto.userInterface;



import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import org.hamisto.filmista.Serie;

public class SerieInfo extends GridPane{
	
	  private final String LABEL_STYLE = "-fx-text-fill: black; -fx-font-size: 14; " +
			      "-fx-font: Gill Sans;"+
			      "-fx-font-family: Gill Sans;"+
	              "-fx-effect: dropshadow(one-pass-box, black, 5, 0, 1, 1);";
	  
 public SerieInfo(final Serie serie) {
	// TODO Auto-generated constructor stub
	 
	
	 FlowPane flow = new FlowPane(Orientation.HORIZONTAL);
	 flow.setAlignment(Pos.TOP_LEFT);
	 flow.setHgap(40);
     
     DropShadow dropShadow = new DropShadow();
     dropShadow.setOffsetX(10);
     dropShadow.setOffsetY(10);
     dropShadow.setColor(Color.rgb(50, 50, 50, 0.7));
     
     Label poster = new Label();
     poster.setGraphic(new ImageView(serie.getPoster()));
     poster.setEffect(dropShadow);
     
     TextArea text = new TextArea();

     
     text.setPrefSize(600, 160);
     text.setText(serie.getOverview());
     text.setWrapText(true);
     text.setEditable(false);
    /* text.setStyle("-fx-text-fill: black;"+
     	    "-fx-font: Gill Sans;"+
     	    "-fx-font-size: 13;" +
     	    "-fx-height:400");*/
   
     text.setStyle(LABEL_STYLE);
     Label nome = new Label(serie.getNome());
     nome.setTextFill(Color.BLACK);
     nome.setFont(Font.font("Helvetica", 28));
    
     
     Button btn = new Button("Aggiungi");
     String buttonCss = SerieInfo.class.getResource("CustomButton.css").toExternalForm();
 	 btn.getStylesheets().add(buttonCss);
 	 btn.getStyleClass().add("record-sales");
 	 btn.setCursor(Cursor.HAND);
 	 btn.setTextFill(Color.WHITE);
 	 
 	 
 	 
 	 btn.addEventHandler(MouseEvent.MOUSE_CLICKED, 
 		    new EventHandler<MouseEvent>() {
 		        @Override public void handle(MouseEvent e) {
 		        	Guiseries2.count = Guiseries2.count + 1;
 		        	Preferiti.getInstance().addToPreferiti(serie);
 		        }
 		});
 	 
 	 flow.getChildren().add(btn);
 	 flow.getChildren().add(nome);
 	 
		this.setHgap(25);
		this.setVgap(15);
		this.add(poster, 0, 0, 1, 2);
		//this.add(nome, 1, 0, 1, 1);
		this.add(flow, 1, 0);
		this.add(text, 1, 1, 1, 1);
		//this.setGridLinesVisible(true);
		this.setHgrow(text, Priority.ALWAYS);
		this.setVgrow(poster, Priority.ALWAYS);

}

}
