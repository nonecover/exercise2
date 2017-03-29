package wdsr.exercise2.procon;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Task: implement Buffer interface without using any *Queue classes from
 * java.util.concurrent package. Any combination of "synchronized", *Lock,
 * *Semaphore, *Condition, *Barrier, *Latch is allowed.
 */
public class BufferManualImpl implements Buffer {
	private static final int CAPACITY = Integer.MAX_VALUE;

	private final Queue<Order> orders = new LinkedList<Order>();
	private final ReentrantLock lock = new ReentrantLock();
	private final Condition isNotEmpty = lock.newCondition();
	private final Condition isNotFull = lock.newCondition();

	public void submitOrder(Order order) throws InterruptedException {
		lock.lockInterruptibly();
		try {
			waitTillFull();
			orders.add(order);
			isNotEmpty.signal();
		} finally {
			lock.unlock();
		}
	}

	private void waitTillFull() throws InterruptedException {
		while (orders.size() == CAPACITY)
			isNotFull.await();
	}

	public Order consumeNextOrder() throws InterruptedException {
		lock.lockInterruptibly();
		try {
			waitTillEmpty();
			Order order = orders.remove();
			isNotFull.signal();
			return order;
		} finally {
			lock.unlock();
		}
	}

	private void waitTillEmpty() throws InterruptedException {
		while (orders.isEmpty())
			isNotEmpty.await();
	}
}
