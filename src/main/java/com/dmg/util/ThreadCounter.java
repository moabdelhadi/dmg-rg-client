package com.dmg.util;

public class ThreadCounter {
	
	private volatile int threadsCount;
	
	public synchronized  void changeCount(boolean up){
		
		if(up){
			threadsCount++;
		}else{
			threadsCount--;
		}
		
		if(threadsCount<0){
			throw new RuntimeException("Thread Count Lwer than limit");
		}
		
	}
	
	public synchronized int getCount() {
		return threadsCount;
	}
	
	

}
