package com.topica.threadpool;

public class TaskExecutor implements Runnable {
	BlockingQueue<Runnable> queue;
	
	public TaskExecutor(BlockingQueue<Runnable> queue) {
		super();
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			while (true) {
				String name = Thread.currentThread().getName();
				Runnable task = queue.dequeue();
				System.out.println("Task " + ((TestTask) task).getNumber() + " started by " + name);
				task.run();
				System.out.println("Task " + ((TestTask) task).getNumber() + " finished by " + name);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
