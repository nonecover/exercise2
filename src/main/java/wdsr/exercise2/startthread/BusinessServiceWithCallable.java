package wdsr.exercise2.startthread;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BusinessServiceWithCallable {
	private final ExecutorService executorService;
	private final NumericHelper helper;

	public BusinessServiceWithCallable(ExecutorService executorService, NumericHelper helper) {
		this.executorService = executorService;
		this.helper = helper;
	}

	/**
	 * Calculates a sum of 100 random numbers. Random numbers are returned by
	 * helper.nextRandom method. Each random number is calculated
	 * asynchronously.
	 * 
	 * @return sum of 100 random numbers.
	 */
	public long sumOfRandomInts() throws InterruptedException, ExecutionException {
		List<Callable<Integer>> randomCalculators = Stream.generate(() -> (Callable<Integer>) () -> helper.nextRandom())
				.limit(100).collect(Collectors.toList());

		return executorService.invokeAll(randomCalculators).stream().mapToInt(future -> {
			try {
				return future.get();
			} catch (InterruptedException | ExecutionException e) {
				throw new IllegalStateException("task interrupted", e);
			}
		}).sum();
	}
}
