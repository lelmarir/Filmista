package org.hamisto.userInterface;

import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import org.transdroid.daemon.Torrent;

import search.find.RefreshDataTorrent;

public class TabDownload extends ScrollPane {

	private Label label;
	public static VBox mainDownload;
	public Button start;
	public Button stop;
	public FlowPane menu;

	public TabDownload(DownloadUpdateListener listener) {

		RefreshDataTorrent updateTorrents = new RefreshDataTorrent(listener);

		mainDownload = new VBox();
		menu = new FlowPane(Orientation.HORIZONTAL);
		menu.setPadding(new Insets(10, 0, 15, 0));
		start = new Button();
		start.getStyleClass().add("custom-browse");
		start.setGraphic(new ImageView(new Image(TabDownload.class
				.getResourceAsStream("images/Play.png"), 20, 20, true, true)));
		stop = new Button();
		stop.getStyleClass().add("custom-browse");
		stop.setGraphic(new ImageView(new Image(TabDownload.class
				.getResourceAsStream("images/toolbar-pause.png"), 30, 30, true,
				true)));
		menu.getChildren().add(start);
		start.addEventHandler(MouseEvent.MOUSE_CLICKED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {

						start.setGraphic(new ImageView(
								new Image(
										TabDownload.class
												.getResourceAsStream("images/toolbar-pause.png"),
										30, 30, true, true)));

					}
				});
		// menu.getChildren().add(stop);
		menu.setHgap(20);
		mainDownload.setPadding(new Insets(20));
		mainDownload.getChildren().add(menu);
		// updateTabDownload(TorrentSeriesElement.getInstance().getTorrents());

		this.setContent(mainDownload);
		this.setFitToWidth(true);
		this.getStyleClass().add("tab-background");

	}

	public static void updateTabDownload(List<Torrent> torrents) {
		// TODO Auto-generated method stub
		mainDownload.getChildren().clear();

		for (int i = 0; i < torrents.size(); i++) {

			if ((i % 2) == 0) {

				mainDownload.getChildren().add(
						addTorrentEvenToDownloadTab(torrents.get(i)));

			} else {

				mainDownload.getChildren().add(
						addTorrentOddToDownloadTab(torrents.get(i)));

			}

		}

	}

	public static VBox addTorrentEvenToDownloadTab(Torrent torrent) {

		Label label = new Label(torrent.getName());
		label.setFont(Font.font("verdana", FontWeight.BOLD, 14));
		label.setTextFill(Color.GRAY);
		String percent;
		if ( torrent.getPercentuale() == 0 || Double.toString(torrent.getPercentuale()).contains("E"))
		percent = "000";
		else
	    percent = Double.toString(torrent.getPercentuale()).replace(".", "")
					.substring(0, 3);
		Label info = new Label("0 B of  "
					+ torrent.getDimensione()
					+ "     ("
					+ percent + "%)" + "   Velocità:"
					+ torrent.getVelDown() + "KB");
		label.setTextFill(Color.WHITE);
		FlowPane pane = new FlowPane(Orientation.HORIZONTAL);
		ProgressBar pb = new ProgressBar();
		pb.setProgress(torrent.getPercentuale());
		pb.setPrefHeight(15);
		pb.setPrefWidth(600);
		pane.getChildren().add(pb);
		Label startStop = new Label();
		pane.setHgap(15);
		startStop.setGraphic(new ImageView(new Image(TabDownload.class
				.getResourceAsStream("images/play.png"), 15, 15, true, true)));
		pane.getChildren().add(startStop);

		VBox download = new VBox(15);
		download.setPadding(new Insets(15));
		download.getStyleClass().add("topeffect-even");

		download.getChildren().add(label);
		download.getChildren().add(pane);
		download.getChildren().add(info);

		return download;

	}

	public static VBox addTorrentOddToDownloadTab(Torrent torrent) {

		Label label = new Label(torrent.getName());
		label.setFont(Font.font("verdana", FontWeight.BOLD, 14));
		label.setTextFill(Color.GRAY);
		String percent;
		if ( torrent.getPercentuale() == 0 || Double.toString(torrent.getPercentuale()).contains("E"))
		percent = "000";
	    else
		percent = Double.toString(torrent.getPercentuale()).replace(".", "")
				.substring(0, 3);
		Label info = new Label("0 B of  "
				+ torrent.getDimensione()
				+ "     ("
				+ percent + "%)" + "   Velocità:"
				+ torrent.getVelDown() + "KB");
		label.setTextFill(Color.WHITE);
		FlowPane pane = new FlowPane(Orientation.HORIZONTAL);
		ProgressBar pb = new ProgressBar(torrent.getPercentuale());
		pb.setPrefHeight(15);
		pb.setPrefWidth(600);
		pane.getChildren().add(pb);
		Label startStop = new Label();
		pane.setHgap(15);
		startStop.setGraphic(new ImageView(new Image(TabDownload.class
				.getResourceAsStream("images/play.png"), 15, 15, true, true)));
		pane.getChildren().add(startStop);
		VBox download = new VBox(15);
		download.setPadding(new Insets(15));

		download.getStyleClass().add("topeffect-odd");

		download.getChildren().add(label);
		download.getChildren().add(pane);
		download.getChildren().add(info);

		return download;

	}

}
