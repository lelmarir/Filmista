package org.hamisto.tabPaneFX;


import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;


public class JFXTabPane extends BorderPane{
	
	public static class Tab{
		private String name;
		private Image image;
		private Pane layout;
		private Button button;
		
		public Tab(String name) {
			this(name, null, null);
		}
		
		public Tab(String name, Pane layout) {
			this(name, null, layout);
		}
		
		public Tab(String name, Image image, Pane layout) {
			super();
			this.name = name;
			this.setImage(image);
			this.layout = layout;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
			getButton().setText(name);
		}

		public Image getImage() {
			return image;
		}

		public void setImage(Image image) {
			this.image = image;
			initButtonImage();
		}

		public Pane getLayout() {
			if(layout == null){
				this.layout = new Pane();
			}
			return layout;
		}

		public void setLayout(Pane layout) {
			this.layout = layout;
		}

		public ObservableList<Node> getChildren() {
			return getLayout().getChildren();
		}
		
		protected Button createButton(){
			final Button button = new Button();
			button.getStyleClass().add(BUTTON_CSS_CLASS);
			button.setPrefSize(120, 100);
			
			button.setText(this.getName());
			button.setFont(new Font("Tahoma", 15));
			button.setContentDisplay(ContentDisplay.TOP);
			final DropShadow shadow = new DropShadow();
			//Adding the shadow when the mouse cursor is on
			button.addEventHandler(MouseEvent.MOUSE_ENTERED, 
			    new EventHandler<MouseEvent>() {
			        @Override 
			        public void handle(MouseEvent e) {
			            button.setEffect(shadow);
			            button.setCursor(Cursor.HAND);
			        }
			});
			//Removing the shadow when the mouse cursor is off
			button.addEventHandler(MouseEvent.MOUSE_EXITED, 
			    new EventHandler<MouseEvent>() {
			        @Override 
			        public void handle(MouseEvent e) {
			            button.setEffect(null);
			        }
			});
			return button;
		}
		
		protected Button getButton(){
			if(this.button == null){
				this.button = createButton();
			}
			return this.button;
		}
		
		private void initButtonImage(){
			if(this.getImage() != null){
				Image image = this.getImage();
				getButton().setGraphic(new ImageView(image));
			}
		}
	}
	
	private static final String BUTTON_CSS_CLASS = "tab-button";
	private static final String BUTTON_CSS_CLASS_CURRENT = "current";
	private FlowPane flow;
	private List<Tab> tabs;
	private Hashtable<Button, Tab> tabsButtons;
	
	private Button prevButton = null;
	
	public JFXTabPane() {
		this.tabs = new LinkedList<Tab>();
		this.tabsButtons = new Hashtable<Button, JFXTabPane.Tab>();
		
		flow = new FlowPane(Orientation.HORIZONTAL);
		flow.setPadding(new Insets(15,15,15,15));
		flow.setHgap(20);
		flow.setAlignment(Pos.CENTER);
		flow.setStyle("-fx-background-color: #D0CFCF;");
		this.setTop(flow);
	}
	
	public boolean addTab(Tab tab){
		assert tab != null;
		doAddTab(tab);
		return this.tabs.add(tab);
	}
	
//	public boolean remove(Tab tab) {
//		return this.tabs.remove(tab);
//	}

	private void doAddTab(Tab tab){
		Button b = tab.getButton();
		
		b.setOnAction(new EventHandler<ActionEvent>() {			
			@Override
			public void handle(ActionEvent arg0) {
				JFXTabPane.this.select(tabsButtons.get(arg0.getSource()));
			}
		});
		b.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent arg0) {
				if(arg0.getCode() == KeyCode.ENTER){
					JFXTabPane.this.select(tabsButtons.get(arg0.getSource()));
				}
			}
		});
		
		this.tabsButtons.put(b, tab);
		this.flow.getChildren().add(b);
	}
	
	public void select(Tab tab){
		if(prevButton != null){
			prevButton.getStyleClass().remove(BUTTON_CSS_CLASS_CURRENT);
		}
		((Button)tab.getButton()).getStyleClass().add(BUTTON_CSS_CLASS_CURRENT);
		this.setCenter(this.tabsButtons.get(tab.getButton()).getLayout());
		this.prevButton = tab.getButton();
	}
}
