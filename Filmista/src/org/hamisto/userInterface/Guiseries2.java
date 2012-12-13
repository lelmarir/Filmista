package org.hamisto.userInterface;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.hamisto.tabPaneFX.JFXTabPane;

public class Guiseries2 extends Application {

	JFXTabPane tabPane;

	public Guiseries2() {

//		Stage primaryStage = new Stage();
//		try {
//			start(primaryStage);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/*
	 * //setto propiet�� progress bar progressBar.setIndeterminate(true);
	 * progressBar.putClientProperty("JProgressBar.style", "circular");
	 * 
	 * 
	 * 
	 * setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 * 
	 * 
	 * //setto i layout flow.setLayout(new FlowLayout(FlowLayout.LEFT));
	 * results1.setLayout(new BoxLayout(results1, BoxLayout.Y_AXIS));
	 * 
	 * //aggiungo componenti base per la ricerca flow.add(text);
	 * results1.add(flow); //scroll.add(results1); this.add(scroll);
	 * 
	 * 
	 * //setto propiet�� JFrame setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 * setSize(1000, 700);
	 * 
	 * }
	 * 
	 * @Override public void actionPerformed(ActionEvent e) { // TODO
	 * Auto-generated method stub
	 * 
	 * if(e.getSource()==this.text){ flow.add(progressBar); results1.updateUI();
	 * 
	 * 
	 * Serie.CreateSeriesListWorker(text.getText(), new SerieWorkerListener() {
	 * 
	 * @Override public void WorkerDone(List<Serie> series) { // TODO: rimuovere
	 * indicatore caricameto
	 * 
	 * flow.remove(progressBar); flow.updateUI();
	 * 
	 * 
	 * // TODO: visualizare lista serie showSeriesList(series);
	 * 
	 * }
	 * 
	 * 
	 * }); } }
	 * 
	 * 
	 * private void showSeriesList(final List<Serie> series) { if(jfxpanel ==
	 * null){ jfxpanel = new JFXPanel(); }else{ jfxpanel.removeAll(); }
	 * results1.add(jfxpanel);
	 * 
	 * results1.updateUI(); scroll.updateUI(); Platform.runLater(new Runnable()
	 * {
	 * 
	 * @Override public void run() { initFX(jfxpanel); //Platform.exit();
	 * 
	 * } });
	 * 
	 * 
	 * }
	 * 
	 * 
	 * private static void initFX(JFXPanel fxpanel) { // TODO Auto-generated
	 * method stub //int listSize = series.size(); GridPane root = new
	 * GridPane(); root.setHgap(10); root.setVgap(10); root.setPadding(new
	 * Insets(25, 25, 25, 25));
	 * 
	 * for (int i = 0; i < 5; i++) {
	 * 
	 * 
	 * //List<Stagione> listaSeason = series.get(i).getStagioni();
	 * 
	 * Label post = new Label(); Image image5 = new
	 * Image("http://www.thetvdb.com//banners/_cache/posters/82696-1.jpg"
	 * ,160,200,true,true,true); post.setGraphic(new ImageView(image5));
	 * //TextArea descr = new TextArea(series.get(i).getOverview());
	 * 
	 * //descr.setWrapText(true); //descr.setEditable(false);
	 * 
	 * // Text name = new Text(series.get(i).getNome()); //Scene scene = new
	 * Scene(root, Color.ALICEBLUE); root.add(post, 0, i); //root.add(name, 1,
	 * i); //root.add(descr, i+1, i+1, 4, 1);
	 * 
	 * }
	 * 
	 * 
	 * Scene scene = new Scene(root); scene.setFill(null);
	 * fxpanel.setScene(scene);
	 * 
	 * }
	 */
	
	@Override
	public void start(final Stage primaryStage) throws Exception {
		
		tabPane = new JFXTabPane();
		// aggiungo search al Flow
		

		// setto tabPane
		JFXTabPane.Tab tab1 = new JFXTabPane.Tab("Preferiti");
		tab1.setImage(new Image("img/preferiti.png", 70, 70, true, true, true));
		tabPane.addTab(tab1);

		JFXTabPane.Tab tab2 = new JFXTabPane.Tab("Cerca");
		tab2.setImage(new Image("img/cerca.png", 70, 70, true, true,true));
		ScrollPane sPane = new ScrollPane();
		sPane.setContent(new SearchTab());
		tab2.setLayout(sPane);
		tabPane.addTab(tab2);
		tabPane.select(tab2);

		JFXTabPane.Tab tab3 = new JFXTabPane.Tab("Impostazioni");
		tab3.setImage(new Image("img/impostazioni.png", 70, 70, true, true, true));
		tabPane.addTab(tab3);

		Scene scene = new Scene(tabPane, 900, 700);
		tabPane.getStylesheets().add(
				Guiseries2.class.getResource("style.css").toExternalForm()
				);
		primaryStage.setScene(scene);
		
		//close Window of primaryStage
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			 
	          @Override
	          public void handle(WindowEvent event) {
	          if(event.getSource() == WindowEvent.WINDOW_CLOSE_REQUEST){
	          primaryStage.close();}
	          }
		});
		primaryStage.show();
	}
}
