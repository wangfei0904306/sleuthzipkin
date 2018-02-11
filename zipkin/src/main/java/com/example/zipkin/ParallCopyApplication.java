package com.example.zipkin;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 这是一个测试
 */
public class ParallCopyApplication {

	private final static int THREAD_NUM = 4;

	public static List<String> work(List<String> source) {
		long start = System.currentTimeMillis();
		if (Objects.isNull(source) || source.isEmpty()) {
			return new ArrayList<>();
		}
		int size = source.size();
		List<String> target = new ArrayList<>(size);
		int threadNum = THREAD_NUM;
		if (size < THREAD_NUM) {
			threadNum = size;
		}
		CountDownLatch latch = new CountDownLatch(threadNum);
		int recordNum = (size / threadNum) + 1;
		IntStream.range(0, threadNum).forEach(i -> {
			List<String> work = source.stream().skip(i * recordNum).limit(recordNum).collect(Collectors.toList());
			Thread thread = new Thread(new CopyThread(work, target, latch));
			thread.start();
		});
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("复制【" + size + "】个数据，使用【" + (end - start) + "】毫秒");
		return target;
	}

	public static void print(List<String> source) {
		if (Objects.isNull(source)) {
			return;
		}
		source.forEach(System.out::println);

	}


	static class CopyThread implements Runnable {

		private List<String> source;

		private List<String> target;

		private CountDownLatch latch;

		public CopyThread(List<String> source, List<String> target, CountDownLatch latch) {
			this.source = source;
			this.target = target;
			this.latch = latch;
		}

		@Override
		public void run() {
			target.addAll(source);
			latch.countDown();
			System.out.println("work 线程ID：" + Thread.currentThread().getId());
		}
	}

	public static List<String> streamTest(List<String> source) {
		List<String> target = new ArrayList<>(source.size());
		int recordNum = (source.size() / THREAD_NUM) + 1;
		IntStream.range(0, THREAD_NUM).parallel().forEach(i -> {
			List<String> work = source.stream().skip(i * recordNum).limit(recordNum).collect(Collectors.toList());
			target.addAll(work);
			System.out.println("stream 线程ID：" + Thread.currentThread().getId());
		});
		return target;
	}

	public static void main(String[] args){
		int len = 10000000;
		//int len = 10;
		List<String> source = new ArrayList<>(len);
		IntStream.range(0, len).parallel().forEach(i -> {
			source.add(i + "-strin--salkdfjsladkfjlaskdfjlaskfd");
		});
		System.out.println("source大小" + source.size());

		long start = System.currentTimeMillis();
		List<String> target = work(source);
		long end = System.currentTimeMillis();
		System.out.println("work使用【" + (end - start) + "】毫秒 size: " + target.size());
		long start2 = System.currentTimeMillis();
		List<String> target2 = streamTest(source);
		long end2 = System.currentTimeMillis();
		System.out.println("stream使用【" + (end2 - start2) + "】毫秒 size: " + target2.size());

		long start3 = System.currentTimeMillis();
		List<String> target3 = new ArrayList<>(len);
		target3.addAll(source);
		long end3 = System.currentTimeMillis();
		System.out.println("直接复制使用【" + (end3 - start3) + "】毫秒 size: " + target3.size());

		//print(target);
		//print(target2);
	}
}
