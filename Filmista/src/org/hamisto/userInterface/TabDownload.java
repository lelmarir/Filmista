package org.hamisto.userInterface;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ProgressBarTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;



public class TabDownload extends VBox{

	
	
   //private  TableView<DownloadElement> table ;
   //private final ProgressBar progressBar;
	private Label label;
	
 
   
 
	public TabDownload() {
		
		
		updateTabDownload();
		
		
		
		
		
		// TODO Auto-generated constructor stub
		/*table =  new TableView<DownloadElement>();
		table.setLayoutX(20);
		table.setLayoutY(20);
		table.setOpacity(0.9);
		
		progressBar = new ProgressBar();
        progressBar.setPrefHeight(23);
        setAlignment(Pos.CENTER);
	
		final ObservableList<DownloadElement> data =
				
				
	          FXCollections.observableArrayList(
	           new DownloadElement("Jacob", "Smith", "jacob.smith@example.com",0.0)
	          );
		
		
		
		 data.add(
		            new DownloadElement("Jacob1", "Smith1", "jacob.smith@example.com",0.0)
		          );
		 table.setItems(data);
		
		
		
	    //table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setEditable(false);
	
		TableColumn firstNameCol = new TableColumn("Name Download");
		//firstNameCol.setSortType(TableColumn.SortType.ASCENDING);
        firstNameCol.setMinWidth(300);
      
      
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<DownloadElement, String>("name"));
        
        
        TableColumn secondNameCol = new TableColumn("Size");
        
       
        secondNameCol.setMaxWidth(100);
        secondNameCol.setMinWidth(100);
        secondNameCol.setCellValueFactory(
                new PropertyValueFactory<DownloadElement, String>("size"));
        
        TableColumn thirdNameCol = new TableColumn("Speed");
        thirdNameCol.setMaxWidth(100);
        thirdNameCol.setMinWidth(100);
        thirdNameCol.setCellValueFactory(
                new PropertyValueFactory<DownloadElement, String>("speed"));
        
        TableColumn quarterNameCol = new TableColumn("Percentage");
        quarterNameCol.setMinWidth(413);
        
        quarterNameCol.setCellValueFactory(
                new PropertyValueFactory<DownloadElement, String>("percentage"));
        
     
        
        
        Callback<TableColumn, TableCell>  progressCellFactory = new Callback<TableColumn, TableCell>() {
        	
        	@Override
        	public TableCell call(TableColumn arg0) {
        		// TODO Auto-generated method stub
        		return new org.hamisto.userInterface.ProgressBarTableCell();
        	}
		};
        
		quarterNameCol.setCellFactory(progressCellFactory);
        table.setItems(data);
       
        table.getColumns().addAll(firstNameCol, secondNameCol, thirdNameCol, quarterNameCol);*/
        
        
        
        this.setPadding(new Insets(15));
        //this.getChildren().add(table);
        
		this.getStyleClass().add("tab-background");
		
		
	}



	private void updateTabDownload() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
