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
				WorkThread workThread = (WorkThread) WorkThread.currentThread();
				Runnable task = queue.dequeue();
				workThread.setActive(true);
				System.out.println("Task " + ((Task) task).getNumber() + " started by " + workThread.getName()
						+ ". Active: " + workThread.isActive);
				task.run();
				workThread.setActive(false);
				System.out.println("Task " + ((Task) task).getNumber() + " finished by " + workThread.getName()
						+ ". Active: " + workThread.isActive);
				System.out.println("QUEUE: " + queue.getSize());
				System.out.println("SIZE: " + ThreadPoolImpl.workThreads.size());
				if (queue.getSize() == 0 && ThreadPoolImpl.workThreads.size() > ThreadPoolImpl.corePoolSize) {
					Iterator<WorkThread> iterator = ThreadPoolImpl.workThreads.iterator();
					while (iterator.hasNext()) {
						if (iterator.next().isAlive() && (!iterator.next().isActive)) {
							iterator.remove();
							System.out.println("===========> SHUTDOWN: " + iterator.next().getName());
							break;
						}
					}
					break;
				}
			}
		} catch (InterruptedException e) {

		}

	}
}
