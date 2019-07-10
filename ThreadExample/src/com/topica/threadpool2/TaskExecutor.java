package com.topica.threadpool2;

import java.util.Iterator;

import com.topica.threadpool2.BlockingQueue;
import com.topica.threadpool2.Task;

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
				System.out.println("Task " + ((Task) task).getNumber() + " started by " + name);
				task.run();
				System.out.println("Task " + ((Task) task).getNumber() + " finished by " + name);
				System.out.println("QUEUE: " + queue.getSize());
				System.out.println("SIZE: " + ThreadPoolImpl.workThreads.size());
				if (queue.getSize() == 0 && ThreadPoolImpl.workThreads.size() > ThreadPoolImpl.corePoolSize) {
					Iterator<Thread> iterator = ThreadPoolImpl.workThreads.iterator();
					while (iterator.hasNext()) {
						if (iterator.next().isAlive()) {
							iterator.remove();
							System.out.println("===========> SHUTDOWN: " + iterator.next().getName());
							break;
						}

					}
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
