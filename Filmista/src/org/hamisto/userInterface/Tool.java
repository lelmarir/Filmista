package org.hamisto.userInterface;



import javafx.scene.Parent;
import javafx.scene.image.Image;

public class Tool {
    private  String name;
    private  Image icon;
    private  Parent content;
    
      public Tool(){
    	
    	  this.icon = null;
    	  this.content = null;
    	  this.name = null;
    	
          }
      
      public Tool(Image icon) {
  		// TODO Auto-generated method stub
      	 
      	 this.icon = icon;
      	 this.content = null;
     	 this.name = null;

  	}

    public Tool(String name, Parent content, Image icon) {
        this.name = name;
        this.content = content;
        this.icon = icon;
    }
    
    
    public Parent getContent() {
        return content;
    }

    public String getName() {
        return name;
    }

    public Image getIcon() {
        return icon;
    }
}