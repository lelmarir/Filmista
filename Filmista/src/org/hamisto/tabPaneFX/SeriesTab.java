package org.hamisto.tabPaneFX;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;


public class SeriesTab extends BorderPane{
	
	public static class Tab{
		
		private Image image;
		private Parent layout;
		private Label label;
		
		public Tab(){
			
			this(null,null,null);
		}
		public Tab(Image image, Control layout, Label label) {
			super();
			this.setImage(image);
			this.label = label;
			this.layout = layout;
		}

		

		public Image getImage() {
			return image;
		}

		public void setImage(Image image) {
			this.image = image;
			initLabelImage();
		}

		public Parent getLayout() {
			if(layout == null){
				this.layout = new Pane();
			}
			return layout;
		}

		public void setLayout(Parent layout) {
			this.layout = layout;
		}
		
		protected Label createLabel(){
			final Label label = new Label();
			final String borderStyle2 = "-fx-effect: dropshadow(one-pass-box, gray, 4, 0, 2, 2);";
			final String borderStyle = "-fx-effect: innershadow(three-pass-box, gray, 14 , 0.5, 1, 1);";
	
			label.setStyle(borderStyle2);
			label.setCursor(Cursor.HAND);
			
			
			label.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
				    
					label.getStyleClass().remove(borderStyle2);
					label.setStyle(borderStyle);
					
				}
			});

			
			return label;
		}
		
		protected Label getLabel(){
			if(this.label == null){
				this.label = createLabel();
			}
			return this.label;
		}
		
		private void initLabelImage(){
			if(this.getImage() != null){
				Image image = this.getImage();
				getLabel().setGraphic(new ImageView(image));
			}
		}
		
		/*
		 * before tab transition
		 */
		protected void beforeAactivate(Boolean cancel) {
			;
		}
		
		/*
		 * after tab activation
		 */
		protected void afterActivate() {
			;
		}
	
	}
	

	private FlowPane flow;
	private List<Tab> tabs;
	private Hashtable<Label, Tab> tabsLabels;
	

	


	
		
		
		
		public  SeriesTab() {
			// TODO Auto-generated constructor stub
	
			this.tabs = new LinkedList<Tab>();
			this.tabsLabels = new Hashtable<Label,SeriesTab.Tab>();
			
			
		    flow = new FlowPane(Orientation.HORIZONTAL);
		    flow.setAlignment(Pos.CENTER_LEFT);
		    flow.setStyle("-fx-background-color: #D0CFCF;");
		    flow.setHgap(0);
		    flow.setPadding(new Insets(15));
		    
			this.setTop(flow);
		}
		
		public boolean addTab(Tab tab){
			assert tab != null;
			doAddTab(tab);
			return this.tabs.add(tab);
		}
		
//		public boolean remove(Tab tab) {
//			return this.tabs.remove(tab);
//		}

		private void doAddTab(Tab tab){
			final Label l = tab.getLabel();
			
		l.setOnMouseReleased(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					
					
				    l.setStyle("-fx-effect: dropshadow(one-pass-box, gray, 4, 0, 2, 2);");
					SeriesTab.this.select(tabsLabels.get(e.getSource()));
				}
			});
			
			this.tabsLabels.put(l, tab);
			this.flow.getChildren().add(l);
		}
		
		public void select(Tab tab){
			Boolean cancel = false;
			tab.beforeAactivate(cancel);
			if(cancel == true) {
				return;
			}
			
			/*if(prevButton != null){
				prevButton.getStyleClass().remove(BUTTON_CSS_CLASS_CURRENT);
			}
			((Button)tab.getButton()).getStyleClass().add(BUTTON_CSS_CLASS_CURRENT);
			this.setCenter(this.tabsButtons.get(tab.getButton()).getLayout());
			this.prevButton = tab.getButton();
			*/
			this.setCenter(this.tabsLabels.get(tab.getLabel()).getLayout());
			tab.afterActivate();
		}
		
		
		public Tab getTab(Tab tab){
			
			return tab;
			
		}
	}

