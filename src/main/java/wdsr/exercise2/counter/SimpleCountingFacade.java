package wdsr.exercise2.counter;

/**
 * Created by Marek on 05.03.2016.
 * 
 * Task: use 'synchronized' keyword in this file to make
 * SimpleCountingFacadeTest pass.
 */
public class SimpleCountingFacade implements CountingFacade {
	private final BusinessService businessService;

	private final Object lock = new Object();
	private int invocationCounter;

	public SimpleCountingFacade(BusinessService businessService) {
		this.businessService = businessService;
	}

	public void countAndInvoke() {
		synchronized (lock) {
			invocationCounter++;
		}
		businessService.executeAction();
	}

	public int getCount() {
		return invocationCounter;
	}
}
