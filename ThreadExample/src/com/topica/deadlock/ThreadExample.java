package com.topica.deadlock;
public class ThreadExample {
	public static Object lock1 = new Object();
	public static Object lock2 = new Object();
	
	public static void main(String args[]) {
		Thread1 thread1 = new Thread1();
		Thread2 thread2 = new Thread2();
		thread1.start();
		thread2.start();
	}

	public static class Thread1 extends Thread {
		public void run() {
			synchronized (lock1) {
				System.out.println("Thread 1: holding lock 1...");
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
		
				}
				System.out.println("Thread 1: waiting lock 2...");
				synchronized (lock2) {
					System.out.println("Thread 1: holding lock 1&2...");
				}
			}
		}
	}

	public static class Thread2 extends Thread {
		public void run() {
			synchronized (lock2) {
				System.out.println("Thread 2: holding lock 2...");
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					
				}
				System.out.println("Thread 2: waiting lock 1...");
				synchronized (lock1) {
					System.out.println("Thread 2: holding lock 1&2...");
				}
			}
		}
	}
}
