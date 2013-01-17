package search.find;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

import org.hamisto.userInterface.DownloadUpdateListener;
import org.hamisto.userInterface.TabDownload;
import org.hamisto.userInterface.TorrentSeriesElement;
import org.transdroid.daemon.Torrent;

import search.daemon.DaemonManager;

public class RefreshDataTorrent  {

	  private static class Update{
        
	    public static Timer timer ;
	    private DownloadUpdateListener listener;
	    
	    
       public  Update(DownloadUpdateListener listener){
    	   
    	  this.listener = listener;
          int period = 4000;
          int delay = 0;
        
          timer = new Timer();
			
		  timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
		        
			    DaemonManager manager = new DaemonManager();
			    TorrentSeriesElement.getInstance().getTorrents().clear();
			    List<Torrent> torrentFile;
			    int k = 0;
			    while ((torrentFile = manager.retrieveTorrentList())== null && (k < 7)){
			    	
			    	k++;
			    }
			    
			    TorrentSeriesElement.getInstance().getTorrents().addAll(torrentFile);
				for (int i = 0; i < TorrentSeriesElement.getInstance().getTorrents().size(); i++) {
					
				//nome, percentuale, dimensione, velocità
                    
					long size =TorrentSeriesElement.getInstance().getTorrents().get(i).getTotalSize();
					String size2;
					if (size == 0)
						size2 = "N/D";
					else if (size / 1000000000 != 0)
						size2 = Long.toString(size / 1000000000)
								+ "."
								+ (Long.toString((size % 1000000000) / 10000000))
								+ "GB";
					    
					else
						size2 = Long.toString(size / 1000000) + "."
								+ (Long.toString((size % 1000000) / 10000))
								+ "MB";
                     
					float percentage = TorrentSeriesElement.getInstance().getTorrents().get(i)
							.getDownloadedPercentage();
					String percent;
					if (percentage == 0 || Float.toString(percentage).contains("E"))
						percent = "000";
					else
						percent = Float.toString(percentage).replace(".", "")
								.substring(0, 3); // restituisce X%

					int speed = TorrentSeriesElement.getInstance().getTorrents().get(i).getRateDownload() / 1000;
					String speed2 = Integer.toString(speed); // Velocit‡ kB/s
					
					
	                TorrentSeriesElement.getInstance().getTorrents().get(i).setDimensione(size2);
                    TorrentSeriesElement.getInstance().getTorrents().get(i).setPercentuale(percentage);
					TorrentSeriesElement.getInstance().getTorrents().get(i).setVelDown(speed2);
					System.out.println(TorrentSeriesElement.getInstance().getTorrents().get(i));
					System.out.println("Velocità KB/s: " + speed2 + "percentage % : " + percent + "Size:  " + size2);
					
					
					done(TorrentSeriesElement.getInstance().getTorrents());
				}
			}
		}, delay, period);
	}
       
       protected void done(List<Torrent> torrents) {
			if (this.listener != null) {
				listener.UpdateDone(torrents);
			}

		}
 }	
	public RefreshDataTorrent(DownloadUpdateListener listener) {
		
		new Update(listener);
	}

	
}
