package com.topica.threadpool;

public class TestTask implements Runnable {
	private int number;

	public int getNumber() {
		return number;
	}

	public TestTask(int number) {
		this.number = number;
	}

	@Override
	public void run() {
		System.out.println("Task: " + number + " executing.");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Finish task " + number );
	}
}
