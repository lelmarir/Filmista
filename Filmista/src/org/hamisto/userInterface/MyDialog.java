package org.hamisto.userInterface;



import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.hamisto.filmista.Serie;

public class MyDialog extends Stage{

	public MyDialog(Stage stage, Modality applicationModal, String title, Serie serie) {
		// TODO Auto-generated constructor stub
		//super();
		initModality(applicationModal);
		initOwner(stage);
       // setOpacity(.90);
        setTitle(title);
        VBox grid = new VBox(14);
        grid.setAlignment(Pos.CENTER);
        FlowPane flow = new FlowPane(Orientation.HORIZONTAL);
        flow.setAlignment(Pos.TOP_RIGHT);
        final String style = " -fx-background-color:" +
        		"linear-gradient(#f2ffff, #d6d6d6)," +
        		"linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%)," +
        		"linear-gradient(#dddddd 0%, #f6f6f6 50%);" +
        		"-fx-background-radius: 8,7,6;" +
        		" -fx-background-insets: 0,1,2;" +
        		" -fx-text-fill: black;" +
        		" -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );";
        
        
        final String style2 = "-fx-background-color:" + 
                        "rgba(0,0,0,0.08)," +
                        "linear-gradient(#5a61af, #51536d)," +
                        "linear-gradient(#e4fbff 0%,#cee6fb 10%, #a5d3fb 50%, #88c6fb 51%, #d5faff 100%);";
        Label label = new Label();
        Image image = new Image("img/warning.png", 60, 60, true, true, true);
        label.setGraphic(new ImageView(image));
        Label text = new Label();
        grid.setPadding(new Insets(10,20,10,10));
        final Button button = new Button("    Ok    ");
       
        
        button.setMinWidth(20.0);
        button.setStyle(style);
        button.addEventHandler(MouseEvent.MOUSE_PRESSED, 
     		    new EventHandler<MouseEvent>() {
     		        @Override public void handle(MouseEvent e) {
     		        	
     		        	button.setStyle(style2);
     		        	
     		        	
     		        	
     		        }
        });
        button.addEventHandler(MouseEvent.MOUSE_RELEASED, 
     		    new EventHandler<MouseEvent>() {
     		        @Override public void handle(MouseEvent e) {
     		        	
     		        	button.setStyle(style);
     		        	close();
     		        	
     		        	
     		        }
        });
     		        	
        flow.getChildren().add(button);
        grid.getChildren().add(label);
        grid.getChildren().add(text);
        grid.getChildren().add(flow);
        String styleText = "-fx-font: bold italic 15pt Arial";
        text.setStyle(styleText);
        text.setText( serie.getNome() + " e' già tra i Preferiti");
        grid.setStyle("-fx-background-color: #D0CFCF;");
        Scene scene = new Scene(grid, 300, 160);
       
        setScene(scene);
        show();
		
	}

}
