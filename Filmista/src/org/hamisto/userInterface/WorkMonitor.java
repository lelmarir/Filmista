package org.hamisto.userInterface;


import java.util.LinkedList;
import java.util.List;

public class WorkMonitor {
	
	public static interface WorkListener {
		public void worked(WorkMonitor source);
	}
	
	private int maxWork;
	private int currentWork;
	private List<WorkListener> listeners;
	
	public WorkMonitor(WorkListener listener) {
		this.maxWork = 0;
		this.listeners = new LinkedList<WorkMonitor.WorkListener>();
		this.addListener(listener);
	}
	
	public void work(){
		work(1);
	}
	
	public void work(int work){
		this.currentWork += work;
		for(WorkListener l : this.listeners){
			l.worked(this);
		}
	}
	
	public float getProgress(){
		return ((float)currentWork) / ((float)maxWork);
	}

	public int getMaxWork() {
		return maxWork;
	}

	public void setMaxWork(int maxWork) {
		this.maxWork = maxWork;
	}
	
	public void addMaxWork(int add){
		this.maxWork += add;
	}

	public int getCurrentWork() {
		return currentWork;
	}

	public void setCurrentWork(int currentWork) {
		this.currentWork = currentWork;
	}
	
	public void addListener(WorkListener listener){
		this.listeners.add(listener);
	}
}
