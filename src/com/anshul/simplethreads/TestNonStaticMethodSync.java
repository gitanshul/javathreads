package com.anshul.simplethreads;

public class TestNonStaticMethodSync {

	public static void main(String[] args) {
		
		TestNonStaticMethodSync singleInst = new TestNonStaticMethodSync();
		
		Thread t1 = new Thread(new Run1(singleInst));
		Thread t2 = new Thread(new Run2(singleInst));
		
		t1.start();
		t2.start();
	}
	
	public synchronized void method1() throws InterruptedException{
		System.out.println("Entering method1 ");
		Thread.sleep(5000);
		System.out.println("Exiting method1 ");
	}
	
	/**
	 * If you make this method also synchronized then thread 2 will not be able to enter 
	 * method2 until method1 execution is complete since a lock on an object is not 
	 * specific to any method. A lock acquired by thread1 for a method1 on object1 
	 * will block thread 2 to acquire the lock on object1 for any method (method1 or method2 here)
	 * @throws InterruptedException
	 */
	public void method2() throws InterruptedException{
		synchronized (new Object()) {
			System.out.println("Entering method2 ");
			Thread.sleep(5000);
			System.out.println("Exiting method2 ");
		}
	}
	
	public static class Run1 implements Runnable {
		TestNonStaticMethodSync inst;
		
		public Run1(TestNonStaticMethodSync instance) {
			this.inst = instance;
		}
		
		public void run() {
			try {
				inst.method1();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static class Run2 implements Runnable {
		TestNonStaticMethodSync inst;
		
		public Run2(TestNonStaticMethodSync instance) {
			this.inst = instance;
		}
		
		public void run() {
			try {
				inst.method2();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
