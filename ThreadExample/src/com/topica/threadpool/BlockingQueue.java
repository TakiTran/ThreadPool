package com.topica.threadpool;

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
			if(ThreadPool.workThreads.size() < ThreadPool.maximumPollSize) {
    			System.out.println("===========> Thread " + ThreadPool.incrementThreadNumber() + " created.");
    			String threadName = "Thread-" + ThreadPool.incrementThreadNumber();
    			TaskExecutor temp = new TaskExecutor(ThreadPool.queue);
    			Thread thread = new Thread(temp, threadName);
    			thread.start();
    			ThreadPool.workThreads.add(thread);
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
