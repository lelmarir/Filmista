package org.hamisto.userInterface;





import java.io.File;

import org.hamisto.database.FilmistaDb;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;




 
public class TabImpostazioni extends VBox{
	
	private final String LABEL_STYLE = "-fx-text-fill: black; -fx-font-size: 14;"
	        + "-fx-effect: dropshadow(one-pass-box, black, 5, 0, 1, 1);";
	
	//Download settings
	private FlowPane download;
	private VBox vboxDownload;
	private VBox nameSetting;
	
	
    public static TextField downloadPath;
	private final Button browse;
	public static DirectoryChooser chooser;
	static Text downloadSettings;
	public static CheckBox seriesFolder;
	public static RadioButton enableDirectoryChooser;
	public static CheckBox seasonFolder;
	private final ToggleGroup groupDownload;
	public static RadioButton disableDirectoryChooser;
	private Line lineDownload;
	private GridPane gridDownload;
	
	//Language
	private VBox vboxLanguage;
	FlowPane flowLanguage;
	
	
	private Text language;
	private Line languageLine;
	private Label selectLanguage;
	private final Label enFlag;
	private final Label itaFlag;
	private final ToggleGroup langGroup;
    public static  RadioButton english;
    static  RadioButton italian;
    
    
    //lanConfig
    
    private FlowPane flowLanConfig;
    private VBox vboxLanCofing;
    
    
    
    public static Text lanConfig;
    private Line lanLine;
    public static CheckBox checkLanConfig;
    private Label customConfig;
    public static Text porta;
    public static TextField fieldPorta;
    private Text indirizzo;
    public static TextField fieldIndirizzo;
    private final GridPane configGrid;
    
    
	public TabImpostazioni() {
         
		
		
		// TODO Auto-generated method stub

		this.setSpacing(50);
		this.setPadding(new Insets(40));
		this.getStyleClass().add("tab-background");
		
		
		
		
		
		//Download Configuration

		
	
		
		
	    chooser = new DirectoryChooser();
	    
		/*************/
		chooser.initialDirectoryProperty();
		chooser.setTitle("JavaFX Projects");
		
	

		browse = new Button(" Browse... ");
        browse.getStyleClass().add("custom-browse");
        

	    downloadPath = new TextField("");
		downloadPath.setStyle(LABEL_STYLE);
		downloadPath.setEditable(true);
		downloadPath.setFocusTraversable(false);
		downloadPath.setPrefWidth(400);
		downloadPath.setPrefHeight((downloadPath.getHeight() + 24));

		
		
		downloadSettings = new Text("Downloads");
		downloadSettings.getStyleClass().add("text-items");
		downloadSettings.setFill(Color.GREY);
		
		
		seriesFolder = new CheckBox("Create a folder for each Series");
		//seriesFolder.setStyle("-fx-text-fill: black");
		/************/
		seriesFolder.setFocusTraversable(false);
		
		
		groupDownload = new ToggleGroup();
		
		
		enableDirectoryChooser = new RadioButton("Save files to :");
		//enableDirectoryChooser.setStyle("-fx-text-fill: black");
		enableDirectoryChooser.setFocusTraversable(false);
		/****************/

		
		
		disableDirectoryChooser = new RadioButton(
				"Always ask me where to save files");
		disableDirectoryChooser.setFocusTraversable(false);
		
		
		
	    seasonFolder = new CheckBox("Create a folder for each Season");
	       /*********/
		seasonFolder.setFocusTraversable(false);
		
		lineDownload = new Line(400, 0, 0, 0);
		lineDownload.setFill(null);
		lineDownload.setStroke(Color.GREY);
		lineDownload.setStrokeWidth(1);
		lineDownload.setStrokeLineCap(StrokeLineCap.BUTT);
		
		//addComponent to downloadSettings
		
        nameSetting = new VBox();
        download = new FlowPane(Orientation.HORIZONTAL);
		download.setHgap(20);
		vboxDownload = new VBox(15);
		gridDownload = new GridPane();
		gridDownload.setVgap(15);
		
		
		
		nameSetting.getChildren().add(downloadSettings);
		nameSetting.getChildren().add(lineDownload);
		

		vboxDownload.getChildren().add(download);
		vboxDownload.getChildren().add(disableDirectoryChooser);
		
		download.getChildren().add(enableDirectoryChooser);
		download.getChildren().add(downloadPath);
		download.getChildren().add(browse);

		
		gridDownload.setHgrow(downloadSettings, Priority.ALWAYS);
		gridDownload.add(seriesFolder, 0, 0);
		gridDownload.add(seasonFolder, 0, 1);
		gridDownload.setMargin(seasonFolder, new Insets(0, 0, 0, 18));

		
		

		// Language
		
		language = new Text("Language");
		language.getStyleClass().add("text-items");
		language.setFill(Color.GREY);
		
		languageLine = new Line(400, 0, 0, 0);
		languageLine.setFill(null);
		languageLine.setStroke(Color.GREY);
		languageLine.setStrokeWidth(1);
		languageLine.setStrokeLineCap(StrokeLineCap.BUTT);

		selectLanguage = new Label();
		selectLanguage.setText("Select Language for TvDb information: ");
		//selectLanguage.setStyle("-fx-text-fill: black");
		
		enFlag = new Label();
		enFlag.setGraphic(new ImageView(new Image(TabImpostazioni.class
				.getResourceAsStream("images/EnglishFlag.jpg"), 40, 40, true, true)));
		itaFlag = new Label();
		itaFlag.setGraphic(new ImageView(new Image(TabImpostazioni.class
				.getResourceAsStream("images/Italy_flag.gif"), 40, 40, true,
				true)));
		
	    langGroup = new ToggleGroup();
		
		english = new RadioButton();
		english.setFocusTraversable(false);
		 /*********/
		
	    italian = new RadioButton();
		italian.setFocusTraversable(false);
		
	
		vboxLanguage = new VBox();
		flowLanguage = new FlowPane(Orientation.HORIZONTAL);
		flowLanguage.setHgap(10);
		
		
		
		flowLanguage.getChildren().add(selectLanguage);
		flowLanguage.getChildren().add(english);
		flowLanguage.getChildren().add(enFlag);
		flowLanguage.getChildren().add(italian);
		flowLanguage.setMargin(italian, new Insets(0, 0, 0, 90));
		flowLanguage.getChildren().add(itaFlag);
        
		
		vboxLanguage.getChildren().addAll(language, languageLine);
		vboxLanguage.getChildren().add(flowLanguage);
		vboxLanguage.setMargin(flowLanguage, new Insets(40, 0, 0, 0));
		
		
	

		// Lan Configuration
		
		lanConfig = new Text("Lan Configuration");
		lanConfig.getStyleClass().add("text-items");
		lanConfig.setFill(Color.GREY);
		
		
		lanLine = new Line(400, 0, 0, 0);
		lanLine.setFill(null);
		lanLine.setStroke(Color.GREY);
		lanLine.setStrokeWidth(1);
		lanLine.setStrokeLineCap(StrokeLineCap.BUTT);
	

		checkLanConfig = new CheckBox();
		checkLanConfig.setFocusTraversable(false);
		  /**********/
		
		customConfig = new Label("Custom Configuration");
		

		
		
		porta = new Text("Port: ");
		porta.setFill(Color.WHITE);
		
		fieldPorta = new TextField();
		/********/
		fieldPorta.setMaxWidth(100);
		fieldPorta.setMinHeight((fieldPorta.getHeight() + 20));
		fieldPorta.setStyle(LABEL_STYLE);

		indirizzo = new Text("Address: ");
		indirizzo.setFill(Color.WHITE);

		fieldIndirizzo = new TextField();
		/**********/
		fieldIndirizzo.setMinHeight((fieldIndirizzo.getHeight() + 20));
		fieldIndirizzo.setStyle(LABEL_STYLE);
		fieldIndirizzo.setMaxWidth(300);

		
		
		
		flowLanConfig = new FlowPane(Orientation.HORIZONTAL);
		flowLanConfig.setHgap(10);
		flowLanConfig.getChildren().addAll(checkLanConfig, customConfig);
		
		configGrid = new GridPane();
		configGrid.setHgap(15);
		configGrid.setVgap(15);
		configGrid.setVgrow(fieldPorta, Priority.ALWAYS);
		configGrid.setHgrow(fieldIndirizzo, Priority.ALWAYS);
		configGrid.add(porta, 0, 0);
		configGrid.add(fieldPorta, 1, 0);
		configGrid.add(indirizzo, 0, 1);
		configGrid.add(fieldIndirizzo, 1, 1);
		
		
		vboxLanCofing = new VBox();
		vboxLanCofing.getChildren().addAll(lanConfig, lanLine);
		vboxLanCofing.getChildren().add(flowLanConfig);
		vboxLanCofing.setMargin(flowLanConfig, new Insets(40, 0, 0, 0));
		vboxLanCofing.getChildren().add(configGrid);
		vboxLanCofing.setMargin(configGrid, new Insets(20, 0, 20, 40));

		
         //Db code
		
	
		if (FilmistaDb.getInstance().numbRecordSettings() == 0){
			
			
			chooser.setInitialDirectory(new File(System.getProperty("user.home")+"/Downloads"));
			downloadPath.setText(chooser.getInitialDirectory().toString());
			seriesFolder.setSelected(true);
			enableDirectoryChooser.setSelected(true);
			seasonFolder.setSelected(true); 
			english.setSelected(true);
			checkLanConfig.setSelected(false); 
			fieldIndirizzo.setText("http://localhost:9091/transmission/web/");
			fieldPorta.setText("9091");
			
		}
		else {
			FilmistaDb.getInstance().setTabSettings();
			downloadPath.setText(chooser.getInitialDirectory().toString());	
		}
		//Download events
		english.setToggleGroup(langGroup);
		italian.setToggleGroup(langGroup);
		enableDirectoryChooser.setToggleGroup(groupDownload);
		disableDirectoryChooser.setToggleGroup(groupDownload);
		
		
		
		
		browse.addEventHandler(MouseEvent.MOUSE_CLICKED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {

						File file = chooser.showDialog(ToolsApp.GetStage());

						if (file != null) {

							downloadPath.setText(file.getPath());

						}

					}
				});

		browse.addEventHandler(MouseEvent.MOUSE_PRESSED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {

						browse.getStyleClass().add("mouse-pressed");

					

					}
				});

		browse.addEventHandler(MouseEvent.MOUSE_RELEASED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {

						browse.getStyleClass().removeAll("mouse-pressed");

					

					}
				});

		
		
       if(enableDirectoryChooser.isSelected()){
    	   
    	   browse.setDisable(false);
		   downloadPath.setDisable(false);
       }
       
       else{
    	   
    	   groupDownload.selectToggle(disableDirectoryChooser);
    	   browse.setDisable(true);
		   downloadPath.setDisable(true);
    	   
       }


		

		groupDownload.selectedToggleProperty().addListener(
				new ChangeListener<Toggle>() {
					public void changed(ObservableValue<? extends Toggle> ov,
							Toggle old_toggle, Toggle new_toggle) {
						if (groupDownload.getSelectedToggle() != null) {

							if (groupDownload.getSelectedToggle().equals(disableDirectoryChooser)) {

								browse.setDisable(true);
								downloadPath.setDisable(true);

							}

							else {
								browse.setDisable(false);
								downloadPath.setDisable(false);
							}
						}
					}
				});


		//Language events
		
		
        if(english.isSelected() == true){
		    
        	
        	
			itaFlag.setDisable(true);
			
		}
		
		else{
			
			langGroup.selectToggle(italian);
			enFlag.setDisable(true);
			
		}
	    
        
        
        langGroup.selectedToggleProperty().addListener(
				new ChangeListener<Toggle>() {
					public void changed(ObservableValue<? extends Toggle> ov,
							Toggle old_toggle, Toggle new_toggle) {
						
						
						if (langGroup.getSelectedToggle() != null) {

							if (langGroup.getSelectedToggle().equals(english)) {

								itaFlag.setDisable(true);
								enFlag.setDisable(false);

							}

							else {

								enFlag.setDisable(true);
								itaFlag.setDisable(false);
							}
						}
					}
				});
        
        
        //Lan Events
		
		
        checkLanConfig.selectedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(ObservableValue ov, Boolean old_val,
							Boolean new_val) {
              
						if (new_val) {

							configGrid.setDisable(false);
						}

						else {

							configGrid.setDisable(true);
						}
						System.out.println(new_val);
					}
				});
        
        
        

		if (checkLanConfig.isSelected()) {
            
			configGrid.setDisable(false);
		}

		else {

			configGrid.setDisable(true);
		}
		
		

		checkLanConfig.selectedProperty().addListener(
				new ChangeListener<Boolean>() {
					public void changed(ObservableValue ov, Boolean old_val,
							Boolean new_val) {

						if (new_val) {

							configGrid.setDisable(false);
						}

						else {

							configGrid.setDisable(true);
						}
						System.out.println(new_val);
					}
				});
		
		
        //add Download Settings
		
		
		this.getChildren().add(nameSetting);
		this.getChildren().add(vboxDownload);
		this.getChildren().add(gridDownload);
		
		
        //add Language Settings
		
		
		this.getChildren().add(vboxLanguage);
		this.setMargin(vboxLanguage, new Insets(30, 0, 0, 0));
		

		
        //add Lan Config
		
		
		this.getChildren().add(vboxLanCofing);
		this.setMargin(vboxLanCofing, new Insets(30, 0, 0, 0));

	

	}
	
	
 
 }
 

