package com.topica.threadpool2;

public class TestThreadPool {
	public static void main(String[] args) {
		CustomThreadPool customThreadPool = new CustomThreadPool(2, 3, 5);

		for (int i = 1; i <= 10; i++) {
			Task task = new Task("Task " + i);

			customThreadPool.execute(task);
			System.out.println("Add : " + task.getName());
		}

		customThreadPool.shutdown();
	}
}
