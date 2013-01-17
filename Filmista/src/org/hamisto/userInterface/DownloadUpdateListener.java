package org.hamisto.userInterface;

import java.util.List;

import org.transdroid.daemon.Torrent;

public interface DownloadUpdateListener {

	public void UpdateDone(List<Torrent> torrents);
	
}
