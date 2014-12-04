package com.anshul.simplethreads;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class PingPongQueue {

	public static void main(String[] args) {

		BlockingQueue<String> pinpongqueue = new ArrayBlockingQueue<String>(20);

		for(int i=0; i<10; i++){
			pinpongqueue.add("Ping");
		}
		for(int i=0; i<10; i++){
			pinpongqueue.add("Pong");
		}
		
		Thread t1 = new Thread(new PingThread(pinpongqueue, "PingThread"));
		Thread t2 = new Thread(new PongThread(pinpongqueue, "PongThread"));
		
		t1.start();
		t2.start();

	}
		
	static class PingThread implements Runnable{
		String name;
		BlockingQueue<String> myQ;
		
		public PingThread(BlockingQueue<String> q, String threadName) {
			this.myQ = q;
			this.name = threadName;
		}
		
		@Override
		public void run() {
			while(true){
				String s = myQ.poll();
//				System.out.println("PingThread : Polled - "+s);
				if(s==null) break;
				if(s.equals("Ping")){
					System.out.println(this.name+": "+s);
				}else{
					myQ.add(s);
				}
			}
		}
	}
	
	static class PongThread implements Runnable{
		
		String name;
		BlockingQueue<String> myQ;
		
		public PongThread(BlockingQueue<String> q, String threadName) {
			this.myQ = q;
			this.name = threadName;
		}
		
		@Override
		public void run() {
			while(true){
				String s = myQ.poll();
//				System.out.println("PongThread : Polled - "+s);
				if(s==null) break;
				if(s.equals("Pong")){
					System.out.println(this.name+": "+s);
				}else{
					myQ.add(s);
				}
			}
		}
	}
}
