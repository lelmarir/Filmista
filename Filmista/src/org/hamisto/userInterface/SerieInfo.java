package org.hamisto.userInterface;


import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import org.hamisto.filmista.Serie;

public class SerieInfo extends GridPane{
	
	  private final String LABEL_STYLE = "-fx-text-fill: black; -fx-font-size: 14;"
	            + "-fx-effect: dropshadow(one-pass-box, black, 5, 0, 1, 1);";
	  
	 
	
 public SerieInfo(Serie serie) {
	// TODO Auto-generated constructor stub
     
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
     text.setStyle("-fx-text-fill: black;"+
     	    "-fx-font: Verdana;"+
     	    "-fx-font-family: Verdana;"+
     	    "-fx-font-size: 13;" +
     	    "-fx-height:400");
     text.setStyle(LABEL_STYLE);
     
     Label nome = new Label(serie.getNome());
     nome.setTextFill(Color.BLACK);
     nome.setFont(Font.font("Arial", 18));
     
     Button btn = new Button("Aggiungi");
     HBox hbBtn = new HBox(10);
     hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
     hbBtn.getChildren().add(btn);
     VBox v = new VBox(15);
     v.getChildren().add(text);
     v.getChildren().add(hbBtn);
		this.setHgap(25);
		this.setVgap(15);
		this.add(poster, 0, 0, 1, 2);
		this.add(nome, 1, 0, 1, 1);
		this.add(v, 1, 1, 1, 1);
		this.setGridLinesVisible(true);
		this.setHgrow(v, Priority.ALWAYS);
		this.setVgrow(poster, Priority.ALWAYS);

}

}
