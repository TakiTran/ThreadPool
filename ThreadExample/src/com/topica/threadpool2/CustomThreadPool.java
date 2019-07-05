package com.topica.threadpool2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class CustomThreadPool {
	@SuppressWarnings("unused")
	private int corePoolSize;
	private int maximumPoolSize;

	// Internally pool is an array
	private List<WorkerThread> workerThreads;

	// FIFO ordering
	private LinkedBlockingQueue<Runnable> queue;

	public CustomThreadPool(int corePoolSize, int maximumPoolSize, int queueSize) {
		this.corePoolSize = corePoolSize;
		this.maximumPoolSize = maximumPoolSize;
		queue = new LinkedBlockingQueue<Runnable>(queueSize);
		workerThreads = new ArrayList<WorkerThread>();

		for (int i = 0; i < corePoolSize; i++) {
			workerThreads.add(new WorkerThread());
			workerThreads.get(i).start();
		}
	}

	public void execute(Runnable task) {
		synchronized (queue) {
			while(queue.remainingCapacity() == 0) {
				if(workerThreads.size() < maximumPoolSize) {
					workerThreads.add(new WorkerThread());
					workerThreads.get(workerThreads.size() - 1).start();
				}
				try {
					queue.wait();
				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
			}
			queue.add(task);
			queue.notify();
		}
	}

	private class WorkerThread extends Thread {
		private boolean active;
		
		public WorkerThread() {
			super();
			this.active = true;
		}
		
		public void stoped() {
			this.active = false;
		}

		public void run() {
			Runnable task;

			while (true) {
				synchronized (queue) {
					queue.notifyAll();
					task = (Runnable) queue.poll();
				}
				if(task != null) {
					try {
						task.run();
					} catch (RuntimeException e) {
						System.out.println("Thread pool is interrupted due to an issue: " + e.getMessage());
					}
				} else {
					if(active) {
						synchronized(queue) {
							if(queue.isEmpty()) {
								System.out.println("Active thread " + task);
							}
						}
						
					}
				}
				
			}
		}
	}

	public void shutdown() {
		System.out.println("Shutting down thread pool");
		for(WorkerThread workerThread : workerThreads) {
			workerThread.stoped();
		}
	}
}
