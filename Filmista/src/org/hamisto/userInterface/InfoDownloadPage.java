package org.hamisto.userInterface;





import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.hamisto.filmista.Serie;

public class InfoDownloadPage extends Stage{

	public InfoDownloadPage(Stage stage, Modality applicationModal, Serie serie) {
		
		// TODO Auto-generated constructor stub
		
		initModality(applicationModal);
		initOwner(stage);
		
		
		
	   
		BorderPane border = new BorderPane();
		
		FlowPane flow = new FlowPane(Orientation.HORIZONTAL);
	    flow.setAlignment(Pos.CENTER_LEFT);
	    flow.setStyle("-fx-background-color: #D0CFCF;");
	    final Label info = new Label();
	    info.setStyle("-fx-effect: dropshadow(one-pass-box, gray, 4, 0, 2, 2)");
	    
	    info.setGraphic(new ImageView(new Image("img/Info_2.png", 80, 80, true, true, true)));
	    final Label download = new Label();
	    info.setCursor(Cursor.HAND);
	    download.setStyle("-fx-effect: dropshadow(one-pass-box, gray, 4, 0, 2, 2)");
	    download.setCursor(Cursor.HAND);
	    
	    download.setGraphic(new ImageView(new Image("img/Download.png", 80, 80, true, true, true)));
	    flow.getChildren().add(info);
	    flow.getChildren().add(download);
	    flow.setHgap(0);
	    flow.setPadding(new Insets(15));
		border.setTop(flow);
		
		info.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
			
				final String borderStyle = "-fx-effect: innershadow(three-pass-box, gray, 14 , 0.5, 1, 1);";
				info.setStyle(borderStyle);
			}
		});

		info.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
             
				info.setStyle("-fx-effect: dropshadow(one-pass-box, gray, 4, 0, 2, 2)");
			}
		});
		
		
		download.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
			
				final String borderStyle = "-fx-effect: innershadow(three-pass-box, gray, 14 , 0.5, 1, 1);";
				download.setStyle(borderStyle);
			}
		});

		download.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
             
				download.setStyle("-fx-effect: dropshadow(one-pass-box, gray, 4, 0, 2, 2)");
			}
		});
		
		
		Scene scene = new Scene(border, 850, 650);
        setScene(scene);
        
        show();
        
		
	
	}

}
