package org.hamisto.userInterface;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import javafx.scene.layout.ColumnConstraints;
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
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

import org.hamisto.database.FilmistaDb;
import org.hamisto.filmista.AddFavoriteTop;
import org.hamisto.filmista.Serie;
import org.hamisto.filmista.TopElement;
import org.hamisto.filmista.TopParsingTvDb;
import org.hamisto.filmista.TopSeriesWorkerListener;

import search.daemon.DaemonManager;
import search.find.Search;
import search.find.SearchListener;

public class TopSeries extends ScrollPane {

	private static VBox principal;
	private Button update;
	private static GridPane updategrid;
	private FlowPane flow;
	public static StackPane stack;

	public static HBox hbox1;
	public static HBox hbox2;
	public static HBox hbox3;
	public static HBox hbox4;
	public static HBox hbox5;

	static boolean contain;
	static boolean containTop;

	private static final Integer STARTTIME = 0;
	private Integer timeSeconds;

	public TopSeries() {

		// TODO Auto-generated constructor stub

		principal = new VBox();
		stack = new StackPane();
		hbox1 = new HBox();
		hbox2 = new HBox();
		hbox3 = new HBox();

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

		update = new Button("Update");
		update.setContentDisplay(ContentDisplay.LEFT);
		update.getStyleClass().add("custom-browse");
		update.setCursor(Cursor.HAND);
		Tooltip tooltip = new Tooltip("Update Series Top");
		update.setTooltip(tooltip);

		update.addEventHandler(MouseEvent.MOUSE_PRESSED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {

					}
				});
		update.setGraphic(new ImageView(new Image("img/Repeat.png", 20, 20,
				true, true, true)));

		update.addEventHandler(MouseEvent.MOUSE_RELEASED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {

						stack.setVisible(false);
						hbox1.setVisible(false);
						hbox2.setVisible(false);
						hbox3.setVisible(false);
						hbox1.getChildren().clear();
						hbox2.getChildren().clear();
						hbox3.getChildren().clear();
						update.setDisable(true);

						TopParsingTvDb topsSeries = new TopParsingTvDb(
								new TopSeriesWorkerListener() {

									@Override
									public void WorkerDone(
											final List<TopElement> topseries) {
										Platform.runLater(new Runnable() {

											@Override
											public void run() {

												if (TopParsingTvDb.alreadyUpdate == false) {

													FilmistaDb.getInstance()
															.resetTopSeriesDb();

													for (int i = 0; i < topseries
															.size(); i++) {

														try {
															FilmistaDb
																	.getInstance()
																	.addTopElementDb(
																			topseries
																					.get(i));
														} catch (IOException e) {
															// TODO
															// Auto-generated
															// catch block
															e.printStackTrace();
														} catch (SQLException e) {
															// TODO
															// Auto-generated
															// catch block
															e.printStackTrace();
														}

													}

												}
												update.setDisable(false);
												System.out.println(topseries
														.size());

												updateSeriesTop(topseries);

											}

										});

									}
								});

					}

				});

		hbox1.setVisible(false);
		hbox1.getStyleClass().add("topeffect-odd");
		hbox1.setPadding(new Insets(20, 30, 15, 10));

		hbox2.setVisible(false);
		hbox2.getStyleClass().add("topeffect-even");
		hbox2.setPadding(new Insets(20, 30, 15, 10));

		hbox3.setVisible(false);
		hbox3.getStyleClass().add("topeffect-odd");
		hbox3.setPadding(new Insets(20, 30, 15, 10));

		updategrid = new GridPane();
		flow = new FlowPane(Orientation.HORIZONTAL);
		flow.setAlignment(Pos.TOP_RIGHT);
		flow.getChildren().add(update);
		principal.setPadding(new Insets(30, 15, 15, 30));
		principal.getChildren().add(updategrid);

		// aupdategrid.setGridLinesVisible(true);
		updategrid.getColumnConstraints().add(new ColumnConstraints());
		updategrid.getColumnConstraints().add(new ColumnConstraints(200));
		updategrid.add(flow, 1, 0);
		stack.setPrefWidth(700);
		updategrid.add(stack, 0, 0);
		updategrid.setVgrow(stack, Priority.ALWAYS);
		principal.getChildren().add(hbox1);
		principal.setMargin(hbox1, new Insets(30, 0, 0, 0));
		principal.getChildren().add(hbox2);
		principal.getChildren().add(hbox3);
		principal.setMargin(hbox3, new Insets(0, 0, 10, 0));

		this.setFitToWidth(true);
		this.setContent(principal);
		this.getStyleClass().add("tab-background");

	}

	public static void updateSeriesTop(List<TopElement> topseries) {

		for (int i = 0; i < topseries.size(); i++) {

			System.out.println("NOME: " + topseries.get(i).getNome());
			topseries.get(i).setShortOverview(topseries.get(i).getOverview());
			System.out.println("SHORT OVERVIEW: "
					+ topseries.get(i).getShortOverview());
			System.out.println("GENRE:" + topseries.get(i).getGenre());
			System.out.println("\n\n\n\n");

			if (i == 0) {

				CreateTopElementLayout(topseries.get(i));

			}

			else {

				CreateOtherElement(topseries.get(i), i);
			}

		}

		stack.setVisible(true);
		hbox1.setVisible(true);
		hbox2.setVisible(true);
		hbox3.setVisible(true);
	}

	private static void CreateTopElementLayout(final TopElement topElement) {
		// TODO Auto-generated method stub

		Image tick = null;
		containTop = false;

		for (int i = 0; i < Preferiti.getInstance().getSeries().size(); i++) {

			if (Preferiti.getInstance().getSeries().get(i).getId()
					.contains(topElement.getId())) {

				containTop = true;
				tick = new Image("img/greentick.png", 35, 35, true, true, true);

			}

		}
		final Label poster = new Label();
		final DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(3.0);
		dropShadow.setOffsetX(2.5);
		dropShadow.setOffsetY(2.5);
		dropShadow.setColor(Color.BLACK);
		poster.setEffect(dropShadow);
		ImageView view = new ImageView(topElement.getPoster());
		view.resize(220, 220);
		poster.setGraphic(view);
		Light.Point light = new Light.Point();
		light.setX(120);
		light.setY(100);
		light.setZ(80);

		final Lighting lighting = new Lighting();
		lighting.setLight(light);

		final Popup popup = CreateTopElementPopUp(topElement, poster, 0);

		final Timeline timeline = new Timeline();
		final FadeTransition fadeTransition = new FadeTransition(
				Duration.millis(600));
		fadeTransition.setNode(popup.getContent().get(0));
		fadeTransition.setNode(popup.getContent().get(1));

		popup.getContent().get(0)
				.setOnMouseEntered(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent me) {

						timeline.stop();
						popup.getContent().get(0).setCursor(Cursor.HAND);

					}
				});

		popup.getContent().get(0)
				.setOnMouseExited(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent me) {

						timeline.playFromStart();

					}
				});

		popup.getContent().get(1)
				.setOnMouseEntered(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent me) {

						timeline.stop();
						popup.getContent().get(1).setCursor(Cursor.HAND);

					}
				});

		popup.getContent().get(1)
				.setOnMouseExited(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent me) {

						timeline.playFromStart();

					}
				});

		popup.setOnShowing(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {

				fadeTransition.setFromValue(0.0);
				fadeTransition.setToValue(1.0);
				fadeTransition.play();

			}
		});

		poster.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				// change the z-coordinate of the circle

				timeline.stop();

				popup.show(ToolsApp.GetStage(),
						ToolsApp.GetStage().getX() + 350, ToolsApp.GetStage()
								.getY());
				System.out.println((ToolsApp.GetStage().getX() - 30) + ","
						+ (ToolsApp.GetStage().getY() - 20));
				System.out.println(me.getScreenX() + "," + me.getScreenY());
				poster.setCursor(Cursor.HAND);
				poster.setEffect(null);
				poster.setEffect(lighting);

			}
		});

		poster.setOnMouseExited(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent me) {

				timeline.playFromStart();
				poster.setCursor(null);
				poster.setEffect(null);
				poster.setEffect(dropShadow);

			}
		});

		timeline.getKeyFrames().add(
				new KeyFrame(Duration.seconds(0.3), new EventHandler() {
					// KeyFrame event handler

					@Override
					public void handle(Event arg0) {
						// TODO Auto-generated method stub

						if (timeline.getCurrentTime().greaterThanOrEqualTo(
								timeline.getTotalDuration())) {

							timeline.stop();
							popup.hide();

						}

					}

				}));

		Text posizione = new Text("1.");
		posizione.setStyle("-fx-font: 25px Verdana;" + "-fx-fill: #F2EF4B;");

		Text nome = new Text(topElement.getNome());
		nome.setFont(Font.font("verdana", FontWeight.BOLD, 30));

		final Hyperlink addLink = new Hyperlink("Add To Favorite");
		addLink.setStyle("-fx-font: 16px Verdana;" + "-fx-text-fill: #3B64C4;");
		addLink.setFocusTraversable(false);
		addLink.setCursor(Cursor.HAND);
		addLink.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {

						System.out.println("Sto entrando con il mouse");
						addLink.setStyle(null);
						addLink.setStyle("-fx-font: 16px Verdana;"
								+ "-fx-text-fill: #3491E3;");

					}
				});
		addLink.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {

						System.out.println("Sto uscendo con il mouse");
						addLink.setStyle(null);
						addLink.setStyle("-fx-font: 16px Verdana;"
								+ "-fx-text-fill: #3B64C4;");

					}
				});

		VBox nameAndLink = new VBox();
		nameAndLink.setAlignment(Pos.TOP_LEFT);
		FlowPane titolo = new FlowPane(Orientation.HORIZONTAL);
		titolo.setAlignment(Pos.TOP_LEFT);
		titolo.getChildren().add(posizione);
		titolo.getChildren().add(nome);
		nameAndLink.getChildren().add(titolo);
		nameAndLink.getChildren().add(addLink);

		final GridPane gridElement = new GridPane();
		gridElement.setPadding(new Insets(15, 15, 15, 15));
		gridElement.add(poster, 0, 0, 1, 2);
		gridElement.setVgrow(poster, Priority.ALWAYS);
		gridElement.setMargin(poster, new Insets(15, 15, 15, 15));
		gridElement.add(nameAndLink, 1, 0);
		gridElement.setHgrow(nameAndLink, Priority.ALWAYS);
		gridElement.setMargin(nameAndLink, new Insets(15, 15, 15, 15));

		if (tick != null) {

			Label tickLabel = new Label();
			tickLabel.setGraphic(new ImageView(tick));
			FlowPane flow = new FlowPane(Orientation.HORIZONTAL);
			flow.setAlignment(Pos.TOP_RIGHT);
			flow.getChildren().add(tickLabel);
			gridElement.add(flow, 1, 1);
			gridElement.setMargin(flow, new Insets(0, 0, 0, 0));

		}

		addLink.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				System.out.println("This link is clicked");

				if (contain == false) {

					Image tick1 = new Image("img/greentick.png", 35, 35, true,
							true, true);
					Label tickLabel = new Label();
					tickLabel.setGraphic(new ImageView(tick1));
					FlowPane flow = new FlowPane(Orientation.HORIZONTAL);
					flow.setAlignment(Pos.TOP_RIGHT);
					flow.getChildren().add(tickLabel);
					gridElement.add(flow, 1, 1);
					gridElement.setMargin(flow, new Insets(0, 0, 0, 0));

					new AddFavoriteTop(topElement, new TopListener() {

						@Override
						public void TopAdded(final Serie serie) {
							// TODO Auto-generated method stub
							Platform.runLater(new Runnable() {
								@Override
								public void run() {
									Preferiti.getInstance().addToPreferiti(
											serie);
									TabPreferiti.updateTab();
								}
							});
							System.out.print("SERIE NOME:" + serie.getNome());

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
													.getStagioni().size(); i++) {

												for (int j = 0; j < serie
														.getStagioni().get(i)
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
															
															else{
																
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

										}
									});

							try {
								FilmistaDb.getInstance().addSeriesToFilmistaDb(
										serie);

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
							});
						}
					});

				}

			}

		});

		TextArea area = new TextArea();
		area.getStyleClass().add("top1-effect");
		area.setEditable(false);
		area.setFocusTraversable(false);

		stack.getChildren().add(area);
		stack.resize(100, 300);

		stack.getChildren().add(gridElement);

	}

	private static Popup CreateTopElementPopUp(TopElement topElement,
			Label top, int pos) {

		final Popup pop = new Popup();

		DropShadow ds = new DropShadow();
		ds.setOffsetY(3.0f);
		ds.setColor(Color.color(0.4f, 0.4f, 0.4f));

		Rectangle rect = new Rectangle(280, 350, Color.LIGHTGREY);
		rect.setEffect(ds);
		rect.setArcHeight(20.0);
		rect.setArcWidth(20.0);

		Polygon polygon = new Polygon();

		if ((pos == 5) || (pos == 6)) {
			polygon.getPoints()
					.addAll(new Double[] { 320.0 - rect.getWidth() - 80,
							(rect.getY() + (rect.getHeight() / 2)) + 60,

							280 - rect.getWidth(),
							(rect.getY() + (rect.getHeight() / 2) - 20.0) + 10,
							280 - rect.getWidth(),
							(rect.getY() + (rect.getHeight() / 2) + 20.0) + 20 });

		}

		else if (pos == 0) {

			polygon.getPoints()
					.addAll(new Double[] { 320.0 - rect.getWidth() - 80,
							(rect.getY() + (rect.getHeight() / 2)) - 60,

							280 - rect.getWidth(),
							(rect.getY() + (rect.getHeight() / 2) - 20.0) + 10,
							280 - rect.getWidth(),
							(rect.getY() + (rect.getHeight() / 2) + 20.0) + 10 });
		}

		else {

			polygon.getPoints().addAll(
					new Double[] { 320.0 - rect.getWidth() - 80,
							(rect.getY() + (rect.getHeight() / 2)),

							280 - rect.getWidth(),
							(rect.getY() + (rect.getHeight() / 2) - 20.0),
							280 - rect.getWidth(),
							(rect.getY() + (rect.getHeight() / 2) + 20.0) });

		}

		Shape shape = Shape.union(rect, polygon);
		shape.setFill(Color.LIGHTGREY);
		shape.setEffect(ds);

		FlowPane pane = new FlowPane();
		pane.setPadding(new Insets(20));
		Text nome = new Text(topElement.getNome());
		nome.setFill(Color.WHITE);
		nome.setFont(Font.font("Helvetica", 18));
		pane.getChildren().add(nome);
		int k = 0;
		while ((topElement.getYear().charAt(k)) != '-'){
			
			k++;
		}
		String correctYear = topElement.getYear().substring(0, k);
		Text year = new Text(correctYear);
		year.setFill(Color.WHITE);
		year.setFont(Font.font("Helvetica", 10));
		pane.getChildren().add(year);

		BorderPane border = new BorderPane();
		border.setTop(pane);
		border.setPrefWidth(280);
		String buttonCss = TopSeries.class.getResource("popup.css")
				.toExternalForm();
		border.getStylesheets().add(buttonCss);
		border.getTop().getStyleClass().add("custom-border");

		VBox vbox = new VBox();
		TextArea overview = new TextArea(topElement.getShortOverview()
				.substring(0, 125).concat("..."));

		System.out.println(topElement.getShortOverview());

		overview.setStyle("-fx-background-color: lightgray;");
		overview.setPrefHeight(75);
		overview.setWrapText(true);
		overview.setEditable(false);

		vbox.getChildren().add(overview);

		Label genre = new Label("Genre: ");
		Label runtime = new Label("Runtime: ");
		Label status = new Label(" Status: ");
		genre.setStyle("-fx-font-weight: bold;");
		runtime.setStyle("-fx-font-weight: bold;");
		status.setStyle("-fx-font-weight: bold;");

		int i = 1;
		while (topElement.getGenre().charAt(i) != '|') {
			i++;
		}

		String results = topElement.getGenre().substring(1, i);
		Label genreval = new Label(results);
		Label runtimeval = new Label(topElement.getRuntime());
		Label statusval = new Label(topElement.getStatus());

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

		int valRate = RateToStars(topElement.getRating());

		int redStar = valRate / 4;
		for (int j = 0; j < redStar; j++) {

			Label star = new Label();
			star.setGraphic(new ImageView(new Image("img/icon-star-100.png",
					25, 25, true, true, true)));

			hbox.getChildren().add(star);

		}

		if ((valRate % 4) == 0) {

			int greyStar = ((20 - valRate) / 4);
			for (int j = 0; j < greyStar; j++) {

				Label star = new Label();
				star.setGraphic(new ImageView(new Image(
						"img/icon-grey-star.png", 25, 25, true, true, true)));

				hbox.getChildren().add(star);
			}
		}

		if (((valRate % 4) > 0) && ((valRate % 4) < 2)) {

			Label star = new Label();
			star.setGraphic(new ImageView(new Image("img/icon-grey-33.png", 25,
					25, true, true, true)));

			hbox.getChildren().add(star);
		}

		if (((valRate % 4) >= 2) && ((valRate % 4) <= 2.5)) {

			Label star = new Label();
			star.setGraphic(new ImageView(new Image("img/icon-grey-50.png", 25,
					25, true, true, true)));

			hbox.getChildren().add(star);

		}

		if (((valRate % 4) > 2.5) && ((valRate % 4) <= 4.9)) {

			Label star = new Label();
			star.setGraphic(new ImageView(new Image("img/icon-star-66.png", 25,
					25, true, true, true)));

			hbox.getChildren().add(star);

		}

		System.out.println(valRate);

		float ratingNumb = Float.parseFloat(topElement.getRating());
		ratingNumb = ratingNumb / 2;
		Text rate = new Text(Float.toString(ratingNumb));

		rate.setFont(Font.font("Helvetica", 22));
		rate.setStyle("-fx-font-weight: bold;");
		hbox.getChildren().add(rate);

		hbox.setMargin(rate, new Insets(5, 0, 0, 15));

		vbox.getChildren().add(hbox);
		vbox.setMargin(hbox, new Insets(15, 0, 0, 20));
		StackPane stack1 = new StackPane();
		stack1.getChildren().add(border);

		pop.getContent().addAll(shape, stack1);

		return pop;
		// TODO Auto-generated method stub

	}

	private static int RateToStars(String rating) {
		int starNum;
		float ratingNumb = Float.parseFloat(rating);
		System.out.println(ratingNumb);
		ratingNumb = ratingNumb / 2;
		System.out.println(ratingNumb);
		starNum = ((int) ratingNumb) * 4;
		System.out.println(starNum);

		String point = Float.toString(ratingNumb).substring(2);

		if (point.length() < 2)
			point = point.concat("0");
		System.out.println(point);
		int afterPoint = Integer.parseInt(point);
		if (afterPoint >= 25)
			starNum++;
		if (afterPoint >= 50)
			starNum++;
		if (afterPoint >= 75)
			starNum++;
		if (afterPoint >= 90)
			starNum++;

		System.out.println(starNum);
		return starNum;

	}

	private static void CreateOtherElement(final TopElement topElement,
			final int pos) {
		// TODO Auto-generated method stub

		Image tick = null;
		contain = false;

		for (int i = 0; i < Preferiti.getInstance().getSeries().size(); i++) {

			if (Preferiti.getInstance().getSeries().get(i).getId()
					.contains(topElement.getId())) {

				contain = true;
				tick = new Image("img/greentick.png", 35, 35, true, true, true);

			}

		}

		final Label poster = new Label();

		poster.setGraphic(new ImageView(topElement.getPoster()));

		final DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(3.0);
		dropShadow.setOffsetX(2.0);
		dropShadow.setOffsetY(2.0);
		dropShadow.setColor(Color.BLACK);
		Light.Point light = new Light.Point();
		light.setX(120);
		light.setY(100);
		light.setZ(80);
		final Lighting lighting = new Lighting();
		lighting.setLight(light);

		poster.setEffect(dropShadow);

		final Popup popup = CreateTopElementPopUp(topElement, poster, pos);

		final Timeline timeline = new Timeline();
		final FadeTransition fadeTransition = new FadeTransition(
				Duration.millis(600));
		fadeTransition.setNode(popup.getContent().get(0));
		fadeTransition.setNode(popup.getContent().get(1));

		popup.getContent().get(0)
				.setOnMouseEntered(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent me) {

						timeline.stop();
						popup.getContent().get(0).setCursor(Cursor.HAND);

					}
				});

		popup.getContent().get(0)
				.setOnMouseExited(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent me) {

						timeline.playFromStart();

					}
				});

		popup.getContent().get(1)
				.setOnMouseEntered(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent me) {

						timeline.stop();
						popup.getContent().get(1).setCursor(Cursor.HAND);

					}
				});

		popup.getContent().get(1)
				.setOnMouseExited(new EventHandler<MouseEvent>() {
					public void handle(MouseEvent me) {

						timeline.playFromStart();

					}
				});

		popup.setOnShowing(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent event) {

				fadeTransition.setFromValue(0.0);
				fadeTransition.setToValue(1.0);
				fadeTransition.play();

			}
		});

		poster.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				// change the z-coordinate of the circle

				timeline.stop();
				if (pos == 1) {
					popup.show(ToolsApp.GetStage(),
							ToolsApp.GetStage().getX() + 350, ToolsApp
									.GetStage().getY() + 150);

				}

				if (pos == 2) {
					popup.show(ToolsApp.GetStage(),
							ToolsApp.GetStage().getX() + 770, ToolsApp
									.GetStage().getY() + 150);

				}
				if (pos == 3) {
					popup.show(ToolsApp.GetStage(),
							ToolsApp.GetStage().getX() + 350, ToolsApp
									.GetStage().getY() + 300);

				}

				if (pos == 4) {
					popup.show(ToolsApp.GetStage(),
							ToolsApp.GetStage().getX() + 770, ToolsApp
									.GetStage().getY() + 300);

				}

				if (pos == 5) {

					popup.show(ToolsApp.GetStage(),
							ToolsApp.GetStage().getX() + 350, ToolsApp
									.GetStage().getY() + 460);
				}

				if (pos == 6) {
					popup.show(ToolsApp.GetStage(),
							ToolsApp.GetStage().getX() + 770, ToolsApp
									.GetStage().getY() + 460);

				}

				poster.setCursor(Cursor.HAND);
				poster.setEffect(null);
				poster.setEffect(lighting);

			}
		});

		poster.setOnMouseExited(new EventHandler<MouseEvent>() {

			public void handle(MouseEvent me) {

				timeline.playFromStart();
				poster.setCursor(null);
				poster.setEffect(null);
				poster.setEffect(dropShadow);

			}
		});

		timeline.getKeyFrames().add(
				new KeyFrame(Duration.seconds(0.3), new EventHandler() {
					// KeyFrame event handler

					@Override
					public void handle(Event arg0) {
						// TODO Auto-generated method stub

						if (timeline.getCurrentTime().greaterThanOrEqualTo(
								timeline.getTotalDuration())) {

							timeline.stop();
							popup.hide();

						}

					}

				}));

		Text nome = new Text(topElement.getNome());
		nome.setFill(Color.WHITE);
		

		Text numero = new Text((pos + 1) + ".");
		numero.setFill(Color.WHITE);

		final Hyperlink addLink = new Hyperlink("Add To Favorite");
		addLink.setStyle("-fx-font: 12px Verdana;" + "-fx-text-fill: #528DD9;");
		addLink.setFocusTraversable(false);
		addLink.setCursor(Cursor.HAND);
		addLink.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {

						addLink.setStyle(null);
						addLink.setStyle("-fx-font: 12px Verdana;"
								+ "-fx-text-fill: #69A4F0;");

					}
				});
		addLink.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {

						addLink.setStyle(null);
						addLink.setStyle("-fx-font: 12px Verdana;"
								+ "-fx-text-fill: #528DD9;");

					}
				});
		final GridPane element = new GridPane();

		addLink.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				System.out.println("This link is clicked");

				if (contain == false) {

					Image tick1 = new Image("img/greentick.png", 35, 35, true,
							true, true);
					Label tickLabel = new Label();
					tickLabel.setGraphic(new ImageView(tick1));
					element.add(tickLabel, 1, 1);

					new AddFavoriteTop(topElement, new TopListener() {

						@Override
						public void TopAdded(final Serie serie) {
							// TODO Auto-generated method stub

							Platform.runLater(new Runnable() {
								@Override
								public void run() {

									// inserire codice torrent...

									Preferiti.getInstance().addToPreferiti(
											serie);
									TabPreferiti.updateTab();
								}
							});
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
													.getStagioni().size(); i++) {

												for (int j = 0; j < serie
														.getStagioni().get(i)
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
																					.get(j).getTorrent());
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
															
															else{
																
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
										}
									});

							try {
								FilmistaDb.getInstance().addSeriesToFilmistaDb(
										serie);

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
							});
						}
					});

				}

			}

		});
		// insert element into layouts

		FlowPane title = new FlowPane(Orientation.HORIZONTAL);
		VBox vbox = new VBox();

		title.getChildren().add(numero);
		title.getChildren().add(nome);
		vbox.setAlignment(Pos.TOP_LEFT);
		vbox.getChildren().add(title);
		vbox.getChildren().add(addLink);
		element.setHgap(20);

		// element.setGridLinesVisible(true);
		element.add(poster, 0, 0, 1, 2);
		element.setVgrow(element, Priority.ALWAYS);
		element.add(vbox, 1, 0);

		if (tick != null) {

			Label tickLabel = new Label();
			tickLabel.setGraphic(new ImageView(tick));
			element.add(tickLabel, 1, 1);

		}

		if (pos < 3) {

			hbox1.getChildren().add(element);

		} else if (pos < 5) {

			hbox2.getChildren().add(element);

		}

		else if (pos < 7) {

			hbox3.getChildren().add(element);

		}

	}

}
