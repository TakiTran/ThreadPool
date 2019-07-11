package com.topica.threadpool2;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		ThreadPool threadPool = new ThreadPoolImpl(2, 5, 10);
		for (int taskNumber = 1; taskNumber < 50; taskNumber++) {
			Task task = new Task(taskNumber);
			threadPool.submit(task);
		}
		threadPool.shutdown();
	}
}
