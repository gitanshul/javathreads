package com.anshul.simplethreads;


/**
 * This example used thread signaling to print alternate ping pong
 * @author anshulkhare
 *
 */
public class PingPongThreadSignal {

	static boolean pinged = false;
	static Object lock = new Object();
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new PingThread("PingThread"));
		Thread t2 = new Thread(new PongThread("PongThread"));
		
		t1.start();
		t2.start();
	}
	
	static class PingThread implements Runnable{
		String name;
		
		public PingThread(String s) {
			this.name = s;
		}
		@Override
		public void run() {
			while(true){
				synchronized(lock){							
					if(!pinged){
						System.out.println(this.name+" : Ping");
						pinged = true;
						lock.notify();
					}					
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	static class PongThread implements Runnable{
		String name;
		public PongThread(String s) {
			this.name = s;
		}
		
		@Override
		public void run() {
			while(true){
				synchronized(lock){					
					if(pinged){
						System.out.println(this.name+" : Pong");
						pinged = false;
						lock.notify();
					}					
					try {
						lock.wait();
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
