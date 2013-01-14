package search.daemon;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.transdroid.daemon.Torrent;

import com.sun.javafx.tk.Toolkit.Task;

public class Refresh extends Thread {
	    public Refresh(){
	         Refresh.go();
	    }
	
public static void go() {

    

		int delay = 1000;   // delay for - no delay
		int period = 3000;
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
		        public void run() {
		            DaemonManager manager = new DaemonManager();
		            List <Torrent> torrents = manager.retrieveTorrentList();
		            for (int i=0; i<torrents.size();i++){
		            	String name = torrents.get(i).getName();
		            	
		            	long size = torrents.get(i).getTotalSize();
		            	String size2;
		            	if (size == 0) size2="N/D";
		            	else if (size/1000000000 != 0) size2=Long.toString(size/1000000000) + "." + (Long.toString((size%1000000000)/10000000)) + "GB";
		            	else size2 = Long.toString(size/1000000) + "." + (Long.toString((size%1000000)/10000)) + "MB";
		            	
		            	float percentage = torrents.get(i).getDownloadedPercentage();
		            	//String percent;
		            	//if (percentage==0) percent="000";
		            	//else percent = Float.toString(percentage).replace(".", "").substring(0, 3); //restituisce X%
		            	
		            	int speed = torrents.get(i).getRateDownload()/1000;
		            	String speed2 = Integer.toString(speed); //Velocità kB/s
		            	
		            	System.out.println(name + size2 + percentage + speed2);
		       
		            }
		        }
		    }, delay, period);
	}

	
}
