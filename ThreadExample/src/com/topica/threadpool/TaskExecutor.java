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
				 System.out.println("Task Started by Thread :" + name+" of task "+((TestTask) task).getNumber());
				task.run();
				System.out.println("Task finished by Thread :" + name);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
