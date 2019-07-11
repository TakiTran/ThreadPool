package com.topica.threadpool2;

import java.util.ArrayList;
import java.util.Iterator;
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

	@Override
	public void shutdown() {
		while (workThreads.size() > 0) {
			System.out.println("============> CheckSize: " + workThreads.size());
			if (workThreads.size() == corePoolSize) {
				boolean checkRunning = false;
				for (WorkThread thread : workThreads) {
					if (thread.isActive) {
						checkRunning = true;
						break;
					}
				}
				System.out.println("============> CheckActive: " + checkRunning);
				if (!checkRunning) {
					System.out.println("======== START SHUTDOWN =======");
					Iterator<WorkThread> iterator = workThreads.iterator();
					while (iterator.hasNext()) {
						WorkThread workThread = iterator.next();
						if (queue.getSize() == 0) {
							System.out.println("============> SHUTDOWN: " + workThread.getName());
							iterator.remove();
							workThread.interrupt();
						}
					}
					System.out.println("=========> THREAD POll SIZE: " + workThreads.size());
				}
			}
		}
	}

	@Override
	public int getSize() {
		return workThreads.size();
	}

	@Override
	public int getCorePoolSize() {
		return corePoolSize;
	}

	@Override
	public int getMaxPoolSize() {
		// TODO Auto-generated method stub
		return maximumPoolSize;
	}

}
