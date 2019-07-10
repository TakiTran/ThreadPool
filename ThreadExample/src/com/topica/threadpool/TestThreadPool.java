package com.topica.threadpool;


public class TestThreadPool {
	public static void main(String[] args) throws InterruptedException {
		ThreadPool threadPool = new ThreadPool(50, 10, 15);
		for (int taskNumber = 1; taskNumber <= 100; taskNumber++) {
			TestTask task = new TestTask(taskNumber);
			threadPool.submit(task);
		}
		ThreadPool.shutdown();
	}
}
