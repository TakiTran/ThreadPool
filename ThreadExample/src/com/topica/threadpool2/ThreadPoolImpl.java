package com.topica.threadpool2;

import java.util.ArrayList;
import java.util.List;

import com.topica.threadpool2.BlockingQueue;
import com.topica.threadpool2.TaskExecutor;

public class ThreadPoolImpl implements ThreadPool {

	static BlockingQueue<Runnable> queue;
	static int corePoolSize;
	static int maximumPoolSize;
	static List<WorkThread> workThreads = new ArrayList<WorkThread>();

	public ThreadPoolImpl(int corePoolSize, int maximumPoolSize, int queueSize) {
		ThreadPoolImpl.maximumPoolSize = maximumPoolSize;
		ThreadPoolImpl.corePoolSize = corePoolSize;
		queue = new BlockingQueue<Runnable>(queueSize);
		String threadName = null;
		TaskExecutor taskExecutor = null;
		for (int i = 0; i < corePoolSize; i++) {
			threadName = "Thread-" + i;
			taskExecutor = new TaskExecutor(queue);
			WorkThread thread = new WorkThread(taskExecutor, threadName);
			workThreads.add(thread);
			workThreads.get(i).start();
			System.out.println("===========> " + workThreads.get(i).getName() + " created.");
		}
	}

	public static int incrementThreadNumber() {
		return workThreads.size();
	}

	@Override
	public void submit(Runnable task) {
		try {
			queue.enqueue(task);
		} catch (InterruptedException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void shutdown() {
		for (Thread thread : workThreads) {
			thread.stop();
			System.out.println(thread.getName() + " has shutdown.");
		}
	}

	@Override
	public int getSize() {
		return workThreads.size();
	}

}
