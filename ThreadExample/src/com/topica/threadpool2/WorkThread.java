package com.topica.threadpool2;

public class WorkThread extends Thread {
	boolean isActive;

	public WorkThread(Runnable task, String name) {
		super(task, name);
		this.isActive = true;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
