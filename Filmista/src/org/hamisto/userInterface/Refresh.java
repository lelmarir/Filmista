/*package search.daemon;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.transdroid.daemon.Torrent;

import com.sun.javafx.tk.Toolkit.Task;

public class Refresh extends Thread {
	    
public void go() {

    

		int delay = 0;   // delay for - no delay
		int period = 3000;
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
		        public void run() {
		            DaemonManager manager = new DaemonManager();
		            List <Torrent> torrents = manager.retrieveTorrentList();
		            for (int i=0; i<torrents.size();i++){
		            	String name = torrents.get(i).getName();
		            	long size = torrents.get(i).getTotalSize();
		            	if (size == 0) ;
		            	float percentage = torrents.get(i).getDownloadedPercentage();
		            	int percentInt = (Integer.parseInt(Float.toString(percentage).replace(".", "").substring(0, 2))); //restituisce X%
		            	int speed = torrents.get(i).getRateDownload();
		            	System.out.println(name);
		            	System.out.println(size);
		            	System.out.println(percentage);
		            	System.out.println(speed);
		            }
		        }
		    }, delay, period);
	}

	
}
*/