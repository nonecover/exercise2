package wdsr.exercise2.counter;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Marek on 05.03.2016.
 * 
 * Task: use {@see java.util.concurrent.locks.Lock} to make
 * CountingFacadeWithLockTest pass.
 */
public class CountingFacadeWithLock implements CountingFacade {
	private final BusinessService businessService;
	private ReentrantLock lock = new ReentrantLock();

	private int invocationCounter;

	public CountingFacadeWithLock(BusinessService businessService) {
		this.businessService = businessService;
	}

	public void countAndInvoke() {
		lock.lock();
		try {
			invocationCounter++;
			businessService.executeAction();
		} finally {
			lock.unlock();
		}
	}

	public int getCount() {
		return invocationCounter;
	}
}
