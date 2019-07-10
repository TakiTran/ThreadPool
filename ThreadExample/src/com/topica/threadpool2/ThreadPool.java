package com.topica.threadpool2;

public interface ThreadPool {
	public void submit(Runnable task);
	public void shutdown();
	public int getSize();
}
