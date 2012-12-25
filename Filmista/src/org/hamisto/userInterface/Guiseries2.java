package org.hamisto.userInterface;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.hamisto.database.DbPreferiti;
import org.hamisto.tabPaneFX.JFXTabPane;

public class Guiseries2 extends Application {

	
	static JFXTabPane tabPane;
	public static int count = 0;
    static Stage stage;
	
	
	public Guiseries2() {

	}

	@Override
	public void start(final Stage primaryStage) throws Exception {

		
		stage = primaryStage;
		
		tabPane = new JFXTabPane();
		// aggiungo search al Flow

		// setto tabPane
		final JFXTabPane.Tab tab1 = new TabPreferiti();
		tabPane.addTab(tab1);

		JFXTabPane.Tab tab2 = new TabCerca();
		tabPane.addTab(tab2);
		tabPane.select(tab2);

		JFXTabPane.Tab tab3 = new JFXTabPane.Tab("Impostazioni");
		tab3.setImage(new Image("img/settings.png", 70, 70, true, true, true));
		tabPane.addTab(tab3);

		Scene scene = new Scene(tabPane, 950, 700);
		tabPane.getStylesheets().add(
				Guiseries2.class.getResource("style.css").toExternalForm());
		primaryStage.setScene(scene);

		// close Window of primaryStage
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {
						primaryStage.close();
				}
			
		});
		
		primaryStage.show();
		
	}

	public Parent SerieGetTab() {

		return null;

	}
}
