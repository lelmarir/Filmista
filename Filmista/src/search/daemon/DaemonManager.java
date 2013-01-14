package search.daemon;

import java.util.List;

import org.hamisto.database.FilmistaDb;
import org.hamisto.userInterface.TabDownload;
import org.hamisto.userInterface.TabImpostazioni;
import org.transdroid.daemon.Daemon;
import org.transdroid.daemon.DaemonSettings;
import org.transdroid.daemon.IDaemonAdapter;
import org.transdroid.daemon.OS;
import org.transdroid.daemon.Torrent;
import org.transdroid.daemon.Transmission.TransmissionAdapter;
import org.transdroid.daemon.task.AddByMagnetUrlTask;
import org.transdroid.daemon.task.DaemonTaskResult;
import org.transdroid.daemon.task.GetTorrentDetailsTask;
import org.transdroid.daemon.task.PauseAllTask;
import org.transdroid.daemon.task.PauseTask;
import org.transdroid.daemon.task.ResumeAllTask;
import org.transdroid.daemon.task.RetrieveTask;
import org.transdroid.daemon.task.RetrieveTaskSuccessResult;

import android.os.Bundle;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class DaemonManager {
	public IDaemonAdapter client;



	public DaemonManager() {
		super();
		this.CreateClient();
	}

	public IDaemonAdapter getClient() {
		return client;
	}

	public Torrent AddMagnet(String magnet){
		Bundle bundle = new Bundle();
		bundle.putString("URL", magnet);
		AddByMagnetUrlTask task = new AddByMagnetUrlTask(client, bundle);
		DaemonTaskResult taskResult = task.execute();
		if(taskResult.wasSuccessful()){
			System.out.println(taskResult.wasSuccessful() + taskResult.toString());
			List<Torrent> retrievedTorrents = this.retrieveTorrentList();
			System.out.println(retrievedTorrents.toString());
			return retrievedTorrents.get((retrievedTorrents.size()) - 1);					
		}
		else System.out.println(taskResult.wasSuccessful() + taskResult.toString());
		return null;
	}

	public List<Torrent> retrieveTorrentList(){
		RetrieveTask retrieveTask = new RetrieveTask(this.client);
		RetrieveTaskSuccessResult retrieveTaskResult = (RetrieveTaskSuccessResult) retrieveTask.execute();
		List<Torrent> retrievedTorrents = retrieveTaskResult.getTorrents();
		if(retrieveTaskResult.wasSuccessful()) return retrievedTorrents;
		else return null;
	}

	public Torrent AddTorrentUrl(String url){
		Bundle bundle = new Bundle();
		bundle.putString("URL", url);
		AddByMagnetUrlTask task = new AddByMagnetUrlTask(this.client, bundle);
		DaemonTaskResult taskResult = task.execute();
		if(taskResult.wasSuccessful()){
			System.out.println(taskResult.wasSuccessful() + taskResult.toString());
			List<Torrent> retrievedTorrents = this.retrieveTorrentList();
			System.out.println(retrievedTorrents.toString());
			return retrievedTorrents.get((retrievedTorrents.size()) - 1);	
		}
		else return null;
	}

	public void ResumeAllTorrents(){
		ResumeAllTask task = new ResumeAllTask(this.client);
		task.execute();
	}

	public void PauseAllTorrents(){
		PauseAllTask task = new PauseAllTask(this.client);
		task.execute();
	}

	public DaemonTaskResult GetTorrentDetails(Torrent torrent){
		GetTorrentDetailsTask task = new GetTorrentDetailsTask(this.client, torrent);
		DaemonTaskResult taskResult = task.execute();
		return taskResult;
	}


	private void CreateClient() {
		// TODO Auto-generated method stub
		//TransmissionInterface trans = new TransmissionInterface();

		DaemonSettings settings = this.createSettings();
		this.client = new TransmissionAdapter(settings);

	}
	/*
	private void CreateClient(String downloadDir){
		DaemonSettings settings = this.createSettings(downloadDir);
		this.client = new TransmissionAdapter(settings);

	}

	private void CreateClient(String downloadDir, String address, int port){
		DaemonSettings settings = this.createSettings(downloadDir, address, port);
		this.client = new TransmissionAdapter(settings);

	}

	public IDaemonAdapter CreateClient(String address, int port){
		DaemonSettings settings = this.createSettings(address, port);
		IDaemonAdapter client = new TransmissionAdapter(settings);

		return client;
	}
*/






	private	DaemonSettings createSettings(){

		//Default Settings
		String name = "Transmission";
		Daemon type = Daemon.Transmission;
		String address = "localhost";
		//String address = TabImpostazioni.fieldIndirizzo.getText();
		System.out.println(address);
		int port = 9091;
		//int port = (Integer.parseInt(TabImpostazioni.fieldPorta.getText()));
		System.out.println(port);
		boolean ssl = false;
		boolean sslTrustAll = false;
		String sslTrustKey = null;
		String folder = null;
		boolean useAuthentication = false;
		String username = null;
		String password = null;
		String extraPass = null;
		OS os = OS.Windows;
		String downloadDir = "/home/Manuel Mantovani/Downloads";
		//String downloadDir = "download";
		//String downloadDir = TabImpostazioni.downloadPath.getText();
		System.out.println(downloadDir);
		String ftpUrl = null;
		String ftpPassword = null;
		int timeout = 30;
		boolean alarmOnFinishedDownload = false;
		boolean alarmOnNewTorrent = false;
		String idString = "1";
		boolean isAutoGenerated= false;

		DaemonSettings settings = new DaemonSettings(name, type, address, port, ssl, sslTrustAll, sslTrustKey, folder, useAuthentication, 
				username, password, extraPass, os, downloadDir, ftpUrl, ftpPassword, timeout, alarmOnFinishedDownload, alarmOnNewTorrent, idString, isAutoGenerated);

		return settings;
	}
/*
		        File f=new File("home\\Manuel Mantovani\\.config\\transmission-daemon\\settings.json");

		        FileInputStream fs = null;
		        InputStreamReader in = null;
		        BufferedReader br = null;

		        StringBuffer sb = new StringBuffer();

		        String textinLine;

		        try {
		             fs = new FileInputStream(f);
		             in = new InputStreamReader(fs);
		             br = new BufferedReader(in);

		            while(true)
		            {
		                textinLine=br.readLine();
		                if(textinLine==null)
		                    break;
		                sb.append(textinLine);
		            }
		              String textToEdit1 = "\"download-dir";
		              int cnt1 = sb.indexOf(textToEdit1);
		              sb.replace(cnt1,sb.indexOf(",", cnt1),"\"download-dir\": \"" + downloadDir + "\",");

		            
		              fs.close();
		              in.close();
		              br.close();

		            } catch (FileNotFoundException e) {
		              e.printStackTrace();
		            } catch (IOException e) {
		              e.printStackTrace();
		            }

		            try{
		                FileWriter fstream = new FileWriter(f);
		                BufferedWriter outobj = new BufferedWriter(fstream);
		                outobj.write(sb.toString());
		                outobj.close();

		            }catch (Exception e){
		              System.err.println("Error: " + e.getMessage());
		            }
		    
		*/
		
/*
	private DaemonSettings createSettings(String downloadDir) {
		// TODO Auto-generated method stub
		//Default Settings
		
		String name = "Transmission";
		Daemon type = Daemon.Transmission;
		String address = "localhost";
		int port = 9091;
		boolean ssl = false;
		boolean sslTrustAll = false;
		String sslTrustKey = null;
		String folder = null;
		boolean useAuthentication = false;
		String username = null;
		String password = null;
		String extraPass = null;
		OS os = OS.Windows;
		//String downloadDir = "download";
		String ftpUrl = null;
		String ftpPassword = null;
		int timeout = 30;
		boolean alarmOnFinishedDownload = false;
		boolean alarmOnNewTorrent = false;
		String idString = "1";
		boolean isAutoGenerated= false;

		DaemonSettings settings = new DaemonSettings(name, type, address, port, ssl, sslTrustAll, sslTrustKey, folder, useAuthentication, 
				username, password, extraPass, os, downloadDir, ftpUrl, ftpPassword, timeout, alarmOnFinishedDownload, alarmOnNewTorrent, idString, isAutoGenerated);

		return settings;
	}

	private DaemonSettings createSettings(String downloadDir, String address, int port) {
		// TODO Auto-generated method stub
		//Default Settings
		String name = "Transmission";
		Daemon type = Daemon.Transmission;
		//String address = "localhost";
		//int port = 9091;
		boolean ssl = false;
		boolean sslTrustAll = false;
		String sslTrustKey = null;
		String folder = null;
		boolean useAuthentication = false;
		String username = null;
		String password = null;
		String extraPass = null;
		OS os = OS.Windows;
		//String downloadDir = "download";
		String ftpUrl = null;
		String ftpPassword = null;
		int timeout = 30;
		boolean alarmOnFinishedDownload = false;
		boolean alarmOnNewTorrent = false;
		String idString = "1";
		boolean isAutoGenerated= false;

		DaemonSettings settings = new DaemonSettings(name, type, address, port, ssl, sslTrustAll, sslTrustKey, folder, useAuthentication, 
				username, password, extraPass, os, downloadDir, ftpUrl, ftpPassword, timeout, alarmOnFinishedDownload, alarmOnNewTorrent, idString, isAutoGenerated);

		return settings;
	}

	private DaemonSettings createSettings(String address, int port) {
		// TODO Auto-generated method stub
		//Default Settings
		String name = "Transmission";
		Daemon type = Daemon.Transmission;
		//String address = "localhost";
		//int port = 9091;
		boolean ssl = false;
		boolean sslTrustAll = false;
		String sslTrustKey = null;
		String folder = null;
		boolean useAuthentication = false;
		String username = null;
		String password = null;
		String extraPass = null;
		OS os = OS.Windows;
		String downloadDir = "download";
		String ftpUrl = null;
		String ftpPassword = null;
		int timeout = 30;
		boolean alarmOnFinishedDownload = false;
		boolean alarmOnNewTorrent = false;
		String idString = "1";
		boolean isAutoGenerated= false;

		DaemonSettings settings = new DaemonSettings(name, type, address, port, ssl, sslTrustAll, sslTrustKey, folder, useAuthentication, 
				username, password, extraPass, os, downloadDir, ftpUrl, ftpPassword, timeout, alarmOnFinishedDownload, alarmOnNewTorrent, idString, isAutoGenerated);

		return settings;
	}*/
}

