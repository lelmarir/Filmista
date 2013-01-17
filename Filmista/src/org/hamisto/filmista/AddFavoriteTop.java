package org.hamisto.filmista;

import org.hamisto.userInterface.TopListener;

public class AddFavoriteTop {

	
	static Serie serie;
	public AddFavoriteTop(TopElement element, TopListener listener) {
		// TODO Auto-generated constructor stub
		
		new LoadImage(listener, element).start();
		
		
	}
	

    private static class LoadImage extends Thread {

    TopListener listener;
    TopElement element;
    
	    public LoadImage(TopListener listener, TopElement element) {
	    	
	    	
	    	this.element = element;
	    	this.listener = listener;
	    	
	    }
	   @Override
		public void run() {
			// TODO Auto-generated method stub
			
		   
		   serie = new Serie();
	       serie.setId(this.element.getId());
	       serie.setNome(this.element.getNome());
	       serie.setOverview(this.element.getOverview());
	       serie.loadBanner();
	       
		   done(serie);
		   
		   
		}
	   
		protected void done(Serie element) {
			if (this.listener != null) {
				listener.TopAdded(element);
			}

		}
	}
}
