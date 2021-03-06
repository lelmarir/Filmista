package org.hamisto.userInterface;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;

import org.hamisto.database.FilmistaDb;
import org.hamisto.filmista.Serie;

import search.daemon.DaemonManager;
import search.find.Search;
import search.find.SearchListener;

public class SerieInfo extends BorderPane {

	private final String LABEL_STYLE = "-fx-text-fill: black; -fx-font-size: 14; "
			+ "-fx-font: Gill Sans;"
			+ "-fx-font-family: Gill Sans;"
			+ "-fx-effect: innershadow(three-pass-box, gray, 9 , 0.5, 1, 1);";

	private final String borderStyle = "-fx-effect: innershadow(three-pass-box, gray, 12 , 0.5, 1, 1);";

	public SerieInfo(final Serie serie) {
		// TODO Auto-generated constructor stub

		GridPane grid = new GridPane();
		FlowPane flow = new FlowPane(Orientation.HORIZONTAL);
		flow.setAlignment(Pos.TOP_LEFT);
		flow.setHgap(40);

		DropShadow dropShadow = new DropShadow();
		dropShadow.setOffsetX(10);
		dropShadow.setOffsetY(10);
		dropShadow.setColor(Color.rgb(50, 50, 50, 0.7));

		Label poster = new Label();
		String style_inner = "-fx-font: Gill Sans;"
				+ "-fx-font-family: Gill Sans;"
				+ "-fx-effect: dropshadow(one-pass-box, black, 8, 0, 4, 4);";
		poster.setStyle(style_inner);

		final Label star = new Label();
		Image stella = new Image("img/greentick.png", 35, 35, true, true, true);
		star.setGraphic(new ImageView(stella));
		star.setVisible(false);

		ImageView image = new ImageView(serie.getPoster());
		poster.setGraphic(image);

		TextArea text = new TextArea();

		text.setPrefSize(600, 160);
		text.setText(serie.getOverview());
		text.setWrapText(true);
		text.setEditable(false);
		/*
		 * text.setStyle("-fx-text-fill: black;"+ "-fx-font: Gill Sans;"+
		 * "-fx-font-size: 13;" + "-fx-height:400");
		 */

		text.setStyle(LABEL_STYLE);
		String name = null;
		if (serie.getNome().length() > 16) {
			name = serie.getNome().substring(0, 15);
			name = name.concat("...");
		} else {

			name = serie.getNome();
		}

		Label nome = new Label(name);
		nome.setTextFill(Color.BLACK);
		nome.setFont(Font.font("Helvetica", 28));

		final Button btn = new Button("  Add   ");
		ImageView imageview = new ImageView(new Image("img/add.png", 12, 12,
				true, true, true));
		btn.setGraphic(imageview);
		btn.setContentDisplay(ContentDisplay.LEFT);
		/*
		 * String buttonCss = SerieInfo.class.getResource("CustomButton.css")
		 * .toExternalForm(); btn.getStylesheets().add(buttonCss);
		 */
		btn.getStyleClass().add("custom-browse");
		btn.setCursor(Cursor.HAND);
		btn.setTextFill(Color.WHITE);

		btn.addEventHandler(MouseEvent.MOUSE_CLICKED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {

						star.setVisible(true);

						if (Preferiti.getInstance().addToPreferiti(serie) == false) {

							/*
							 * new MyDialog(Guiseries2.stage,
							 * Modality.APPLICATION_MODAL, "Warning!", serie);
							 */

						} else {

							btn.setDisable(true);
							btn.setText("  Added  ");
							btn.setGraphic(null);
							// torrent...
							DaemonManager manager = new DaemonManager();
							Search search = new Search(serie, manager, "ENG",
									new SearchListener() {

										@Override
										public void SearchListener() {
											Platform.runLater(new Runnable() {

												@Override
												public void run() {

													boolean compare = false;
													for (int i = 0; i < serie
															.getStagioni()
															.size(); i++) {

														for (int j = 0; j < serie
																.getStagioni()
																.get(i)
																.getEpisodiStagione()
																.size(); j++) {

															compare = false;

															if ((serie
																	.getStagioni()
																	.get(i)
																	.getEpisodiStagione()
																	.get(j)
																	.getTorrent() != null)) {
																for (int k = (1 + j); k < serie
																		.getStagioni()
																		.get(i)
																		.getEpisodiStagione()
																		.size(); k++) {

																	if (serie
																			.getStagioni()
																			.get(i)
																			.getEpisodiStagione()
																			.get(k)
																			.getTorrent() != null) {
																		if (serie
																				.getStagioni()
																				.get(i)
																				.getEpisodiStagione()
																				.get(j)
																				.getTorrent()
																				.getName()
																				.equals(serie
																						.getStagioni()
																						.get(i)
																						.getEpisodiStagione()
																						.get(k)
																						.getTorrent()
																						.getName())) {

																			compare = true;
																		}
																	}
																}

																if (compare == false) {

																	TorrentSeriesElement
																			.getInstance()
																			.addToTorrents(
																					serie.getStagioni()
																							.get(i)
																							.getEpisodiStagione()
																							.get(j)
																							.getTorrent());
																	if ((TorrentSeriesElement
																			.getInstance().torrents
																			.indexOf(serie
																					.getStagioni()
																					.get(i)
																					.getEpisodiStagione()
																					.get(j)
																					.getTorrent()) % 2) == 0) {

																		TabDownload.mainDownload
																				.getChildren()
																				.add(TabDownload
																						.addTorrentEvenToDownloadTab(serie
																								.getStagioni()
																								.get(i)
																								.getEpisodiStagione()
																								.get(j)
																								.getTorrent()));

																	}

																	else {

																		TabDownload.mainDownload
																				.getChildren()
																				.add(TabDownload
																						.addTorrentOddToDownloadTab(serie
																								.getStagioni()
																								.get(i)
																								.getEpisodiStagione()
																								.get(j)
																								.getTorrent()));
																	}

																	System.out
																			.println(serie
																					.getStagioni()
																					.get(i)
																					.getEpisodiStagione()
																					.get(j)
																					.getTorrent()
																					.getName());

																}
															}

														}

													}

													try {

														FilmistaDb
																.getInstance()
																.addSeriesToFilmistaDb(
																		serie);
													} catch (ClassNotFoundException e1) {
														// TODO Auto-generated
														// catch
														// block
														e1.printStackTrace();
													} catch (IOException e1) {
														// TODO Auto-generated
														// catch
														// block
														e1.printStackTrace();
													} catch (SQLException e1) {
														// TODO Auto-generated
														// catch
														// block
														e1.printStackTrace();
													}

													TabPreferiti.updateTab();
												}

											});
										}
									});
						}
					}
				});

		flow.getChildren().add(btn);
		flow.getChildren().add(nome);

		if (Preferiti.getInstance().series.contains(serie) == true) {

			btn.setText("  Added  ");
			btn.setDisable(true);
			star.setVisible(true);
			btn.setGraphic(null);
		}

		grid.setHgap(25);
		grid.setVgap(15);
		grid.add(poster, 0, 0, 1, 2);
		grid.add(flow, 1, 0);
		FlowPane paneStar = new FlowPane(Orientation.HORIZONTAL);
		paneStar.setAlignment(Pos.TOP_RIGHT);
		paneStar.getChildren().add(star);
		grid.add(paneStar, 2, 0, 1, 1);
		grid.add(text, 1, 1, 2, 1);

		grid.getColumnConstraints().add(0, new ColumnConstraints());
		grid.getColumnConstraints().add(1, new ColumnConstraints());
		grid.getColumnConstraints().add(2, new ColumnConstraints(150));

		// grid.setGridLinesVisible(true);
		grid.setHgrow(text, Priority.ALWAYS);
		grid.setVgrow(poster, Priority.ALWAYS);

		grid.setPadding(new Insets(25, 25, 25, 25));
		this.setCenter(grid);

		String customCss = SerieInfo.class.getResource("CustomBorder.css")
				.toExternalForm();
		this.getStylesheets().add(customCss);
		this.getStyleClass().add("custom-border");

	}

}
