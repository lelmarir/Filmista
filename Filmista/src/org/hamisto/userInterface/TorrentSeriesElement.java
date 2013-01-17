package org.hamisto.userInterface;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.transdroid.daemon.Torrent;

import search.daemon.DaemonManager;

public class TorrentSeriesElement implements Comparator<Torrent>{
	
	List<Torrent> torrents;
	
    static TorrentSeriesElement instance;
	
    public static TorrentSeriesElement getInstance() {
		if (instance == null) {
			instance = new TorrentSeriesElement();
		}
		return instance;
	}
	
	private  TorrentSeriesElement() {
		// TODO Auto-generated constructor stub
		torrents = new ArrayList<Torrent>();
		//charge torrents from daemon....
	}
	
	public boolean addToTorrents(Torrent torrent) {
		if (torrents.contains(torrent) == false) {
			boolean val =torrents.add(torrent);
			return val;
		}
		return false;
	}

	public List<Torrent> getTorrents() {
		
		return torrents;
		
	}
	

	@Override
	public int compare(Torrent o1, Torrent o2) {
		// TODO Auto-generated method stub
		return o1.getName().compareTo(o2.getName());
	}
}


  
	
