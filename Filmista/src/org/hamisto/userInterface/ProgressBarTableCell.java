package org.hamisto.userInterface;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableCell;

public class ProgressBarTableCell<S, T> extends TableCell<S, T> {

    private final ProgressBar progressBar;
    private ObservableValue<T> ov;

    public ProgressBarTableCell() {
        this.progressBar = new ProgressBar();
        progressBar.setPrefHeight(18);

       
        setAlignment(Pos.CENTER);
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null) {
            setGraphic(null);
            setText(null);
        } else {
            if (item.toString().equalsIgnoreCase("Processing")) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        if (getGraphic() == null) {
                            setGraphic(progressBar);
                            progressBar.setProgress(-1);
                        } else {
                            ProgressBar objpProgressBar = (ProgressBar) getGraphic();
                            objpProgressBar.setProgress(-1);
                        }
                        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    }
                });
                
            }
            
            
        }
        
        
        
        
    }
    
    
}