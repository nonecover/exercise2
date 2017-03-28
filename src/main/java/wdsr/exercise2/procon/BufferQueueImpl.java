package wdsr.exercise2.procon;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Task: implement Buffer interface using one of *Queue classes from
 * java.util.concurrent package.
 */
public class BufferQueueImpl implements Buffer {
	private final BlockingQueue<Order> queue;

	public BufferQueueImpl() {
		queue = new LinkedBlockingQueue<>();
	}

	public void submitOrder(Order order) throws InterruptedException {
		queue.put(order);
	}

	public Order consumeNextOrder() throws InterruptedException {
		return queue.take();
	}
}
