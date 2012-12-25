package org.hamisto.userInterface;




import java.io.IOException;
import java.sql.SQLException;

import javax.print.attribute.standard.DialogTypeSelection;

import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Dialogs;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.hamisto.database.DbPreferiti;
import org.hamisto.filmista.Serie;
import org.jfxtras.scene.border.PipeBorder;
import org.jfxtras.stage.DialogStage;
import org.jfxtras.stage.DialogStageDelegate;

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
    
     ImageView image = new ImageView(serie.getPoster());
     Image im = serie.getPoster();
     poster.setGraphic(image);
     
     String style_inner = 
		      "-fx-font: Gill Sans;"+
		      "-fx-font-family: Gill Sans;"+
             "-fx-effect: dropshadow(one-pass-box, black, 8, 0, 4, 4);";
     poster.setStyle(style_inner);
    
    // poster.setEffect(dropShadow);
    
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
     String name = null;
     if (serie.getNome().length() > 26) {
			name = serie.getNome().substring(0, 25);
			name = name.concat("...");
		} else {

			name = serie.getNome();
		}
    
     Label nome = new Label(name);
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
 		        	
 		        	
 		        if(Preferiti.getInstance().addToPreferiti(serie) == false){
 		        	
 		        	new MyDialog(Guiseries2.stage, Modality.APPLICATION_MODAL, "Warning!",serie);
 		       
					//Dialogs.showWarningDialog(Guiseries2.stage, serie.getNome() + " e' già presente tra i preferiti" +
						//	"", "Warning Dialog", "");
 		        }
 		        else{	
 		           
 		        	
 		        	try {
						DbPreferiti.getInstance().addToDbPreferiti(serie);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
 		        }
 		        }
 		});
 	    
 	  flow.getChildren().add(btn);
	  flow.getChildren().add(nome);
	    
 	    if( Preferiti.getInstance().series.contains(serie) == true ){
 	    	
 	    	Label star = new Label();
 	    	Image stella = new Image("img/star_1.png", 30, 30, true, true, true);
 	    	star.setGraphic(new ImageView(stella));
 	    	flow.getChildren().add(star);
 	    	
 	    	
 	    }
 	   
 	    
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
