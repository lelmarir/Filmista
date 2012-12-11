package org.hamisto.tabPaneFX;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import com.sun.javafx.scene.layout.region.Border;

public class CustomTabPaneFX extends Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("TabPane");
		Border bord;

		FlowPane flow = new FlowPane(Orientation.HORIZONTAL);
		BorderPane border = new BorderPane();
		flow.setPadding(new Insets(15, 15, 15, 15));
		flow.setHgap(20);
		flow.setAlignment(Pos.CENTER);
		final Button button = new Button();
		// button.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		Image image = new Image(
				"http://findicons.com/files/icons/2198/dark_glass/128/bookmark_toolbar.png",
				70, 70, true, true, true);

		// button.setFocusTraversable(false);
		button.getStyleClass().add("tab-button");
		button.setPrefSize(120, 100);
		button.setGraphic(new ImageView(image));
		button.setText("Preferiti");
		button.setFont(new Font("Tahoma", 15));
		button.setContentDisplay(ContentDisplay.TOP);
		final DropShadow shadow = new DropShadow();
		// Adding the shadow when the mouse cursor is on
		button.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						button.setEffect(shadow);
						button.setCursor(Cursor.HAND);
					}
				});
		// Removing the shadow when the mouse cursor is off
		button.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						button.setEffect(null);
					}
				});
		final Button button1 = new Button();
		// button.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		Image image1 = new Image(
				"http://findicons.com/files/icons/575/pleasant/128/search.png",
				70, 70, true, true, true);

		button1.setPrefSize(120, 100);
		ImageView ima = new ImageView(image1);
		// ima.setOpacity(0.11111);
		button1.setGraphic(ima);
		button1.setText("Search");
		button1.setFont(new Font("Tahoma", 15));
		button1.setContentDisplay(ContentDisplay.TOP);

		// button1.setOpacity(0.2222);
		// Adding the shadow when the mouse cursor is on
		button1.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						button1.setEffect(shadow);
						button1.setCursor(Cursor.HAND);
					}
				});
		// Removing the shadow when the mouse cursor is off
		button1.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						button1.setEffect(null);
					}
				});
		final Button button2 = new Button();
		// button.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
		Image image3 = new Image(
				"http://findicons.com/files/icons/2338/reflection/128/gears.png",
				70, 70, true, true, true);
		button2.getStyleClass().add("tab-button");
		button2.setPrefSize(120, 100);
		button2.setGraphic(new ImageView(image3));
		button2.setText("Preference");
		button2.setFont(new Font("Tahoma", 15));
		button2.setContentDisplay(ContentDisplay.TOP);

		// Adding the shadow when the mouse cursor is on
		button2.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						button2.setEffect(shadow);
						button2.setCursor(Cursor.HAND);
					}
				});
		// Removing the shadow when the mouse cursor is off
		button2.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						button2.setEffect(null);
					}
				});

		// Scene scene = new Scene(border,800,400);
		// flow.setStyle("-fx-background-color: #D0CFCF;");
		// flow.getChildren().addAll(button,button1,button2);
		//
		// String searchBoxCss =
		// CustomTabPaneFX.class.getResource("style.css").toExternalForm();
		// border.getStylesheets().add(searchBoxCss);
		// border.setTop(flow);
		// FlowPane panel = new FlowPane();
		// panel.setStyle("-fx-background-color:#9A9A9A;");
		// border.setCenter(panel);
		// scene.setFill(null);
		// primaryStage.setScene(scene);
		// primaryStage.show();

		JFXTabPane tabPane = new JFXTabPane();
		{

			JFXTabPane.Tab t1 = new JFXTabPane.Tab("Preferiti");
			t1.setImage(new Image("img/preferiti.png", 70, 70, true, true, true));
			t1.getLayout().getChildren().add(new Button("aa"));
			tabPane.addTab(t1);

			JFXTabPane.Tab t2 = new JFXTabPane.Tab("Cerca");
			t2.setImage(new Image("img/cerca.png", 70, 70, true, true, true));
			t2.getChildren().add(new Button("bb"));
			tabPane.addTab(t2);

			JFXTabPane.Tab t3 = new JFXTabPane.Tab("Impostazioni");
			t3.setImage(new Image("img/impostazioni.png", 70, 70, true, true, true));
			t3.getChildren().add(new Button("cc"));
			tabPane.addTab(t3);
			
			tabPane.select(t2);
		}
		Scene scene = new Scene(tabPane, 800, 400);
		flow.setStyle("-fx-background-color: #D0CFCF;");
		flow.getChildren().addAll(button, button1, button2);

		String searchBoxCss = CustomTabPaneFX.class.getResource("style.css")
				.toExternalForm();
		tabPane.getStylesheets().add(searchBoxCss);
		// border.setTop(flow);
		// FlowPane panel = new FlowPane();
		// panel.setStyle("-fx-background-color:#9A9A9A;");
		// border.setCenter(panel);
		scene.setFill(null);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
