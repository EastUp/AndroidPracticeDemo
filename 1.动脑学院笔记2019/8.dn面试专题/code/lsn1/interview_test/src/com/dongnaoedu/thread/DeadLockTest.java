package com.dongnaoedu.thread;

public class DeadLockTest {

	public static void main(String[] args) {
		MyTask task = new MyTask();
		
		task.setFlag(1);
		Thread t1 = new Thread(task);
		t1.start();
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//改变条件
		task.setFlag(2);
		Thread t2 = new Thread(task);
		t2.start();
		
	}

}
