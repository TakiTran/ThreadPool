package com.topica.threadpool2;

public class Task implements Runnable{
	private String mName;

	public Task(String name) {
		this.mName = name;
	}

	public String getName() {
		return mName;
	}

	@Override
	public void run() {
		try {
			System.out.println("Executing : " + mName);
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
