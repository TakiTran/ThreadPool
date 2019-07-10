package com.topica.threadpool2;

import java.util.LinkedList;
import java.util.Queue;



public class BlockingQueue<Type> {
	private Queue<Type> queue = new LinkedList<Type>();
	private int EMPTY = 0;
	private int MAX_TASK_IN_QUEUE = -1;

	public BlockingQueue(int size) {
		this.MAX_TASK_IN_QUEUE = size;
	}

	public synchronized void enqueue(Type task) throws InterruptedException {
		while (this.queue.size() == this.MAX_TASK_IN_QUEUE) {
			if (ThreadPoolImpl.workThreads.size() < ThreadPoolImpl.maximumPoolSize) {
				System.out.println("===========> Thread-" + ThreadPoolImpl.incrementThreadNumber() + " created.");
				String threadName = "Thread-" + ThreadPoolImpl.incrementThreadNumber();
				TaskExecutor taskExecutor = new TaskExecutor(ThreadPoolImpl.queue);
				Thread thread = new Thread(taskExecutor, threadName);
				ThreadPoolImpl.workThreads.add(thread);
				ThreadPoolImpl.workThreads.get(ThreadPoolImpl.workThreads.size() - 1).start();
			}
			wait();
		}
		if (this.queue.size() == EMPTY) {
			notifyAll();

		}
		this.queue.offer(task);
	}

	public synchronized Type dequeue() throws InterruptedException {
		while (this.queue.size() == EMPTY) {
			wait();
		}
		if (this.queue.size() == this.MAX_TASK_IN_QUEUE) {
			notifyAll();
		}
		return this.queue.poll();
	}

	public int getSize() {
		return this.queue.size();
	}
}
