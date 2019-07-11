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
				synchronized (ThreadPoolImpl.workThreads) {
					if (queue.getSize() == 0 && ThreadPoolImpl.workThreads.size() > ThreadPoolImpl.corePoolSize) {
						Iterator<WorkThread> iterator = ThreadPoolImpl.workThreads.iterator();
						while (iterator.hasNext()) {
							WorkThread workThread2 = iterator.next();
							if (workThread2.isAlive() && (!workThread2.isActive)) {
								System.out.println("===========> KILL: " + workThread2.getName());
								workThread2.interrupt();
								iterator.remove();
								break;
							}
						}
						break;
					}
				}

			}
		} catch (InterruptedException e) {

		}

	}
}
