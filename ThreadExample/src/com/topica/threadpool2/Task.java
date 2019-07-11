package com.topica.threadpool2;

public class Task implements Runnable {
	private int number;

	public int getNumber() {
		return number;
	}

	public Task(int number) {
		this.number = number;
	}

	@Override
	public void run() {
		System.out.println("Task: " + number + " executing.");
//		try {
//			Thread.sleep(200);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		System.out.println("Finish task " + number);
	}

}
