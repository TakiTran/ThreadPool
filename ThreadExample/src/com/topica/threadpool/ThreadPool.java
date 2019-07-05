package com.topica.threadpool;

import java.util.ArrayList;
import java.util.List;

public class ThreadPool {
	static BlockingQueue<Runnable> queue;
	static int corePollSize;
	static int maximumPollSize;
	static List<Thread> workThreads = new ArrayList<Thread>();

	public ThreadPool(int queueSize, int corePollSize, int maximumPollSize) {
		ThreadPool.maximumPollSize = maximumPollSize;
		queue = new BlockingQueue<>(queueSize);
		String threadName = null;
		TaskExecutor task = null;
		for (int count = 0; count < corePollSize; count++) {
			threadName = "Thread-" + count;
			task = new TaskExecutor(queue);
			Thread thread = new Thread(task, threadName);
			thread.start();
			workThreads.add(thread);
		}
	}

	public void submitTask(Runnable task) throws InterruptedException {
		queue.enqueue(task);
	}

	@SuppressWarnings("deprecation")
	public static void shutdown() {
		for (Thread thread : workThreads) {
			thread.stop();
			System.out.println(thread.getName() + " has shutdown.");
		}
	}

	public static int incrementThreadNumber() {
		return workThreads.size() + 1;
	}
}
