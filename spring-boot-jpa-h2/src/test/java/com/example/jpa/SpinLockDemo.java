package com.example.jpa;


import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 手写一个自旋锁
 * <p>
 * 循环比较获取直到成功为止，没有类似于wait的阻塞
 * <p>
 * 通过CAS操作完成自旋锁，A线程先进来调用myLock方法自己持有锁5秒，B随后进来发现当前有线程持有锁，不是null，所以只能通过自旋等待，直到A释放锁后B随后抢到
 *
 * @author: 陌溪
 * @create: 2020-03-15-15:46
 */
public class SpinLockDemo {
	
	// 现在的泛型装的是Thread，原子引用线程
	AtomicReference<Thread> atomicReference = new AtomicReference<>();
	
	@SneakyThrows
	public static void main(String[] args) {
		
		SpinLockDemo spinLockDemo = new SpinLockDemo();
		
		
		CompletableFuture.runAsync(() -> {
			// 开始占有锁
			spinLockDemo.myLock();
			
			
			try {
				
				TimeUnit.SECONDS.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).whenComplete((data, err) -> {
			System.out.println(data);
			// 开始释放锁
			spinLockDemo.myUnLock();
		});
		
		final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, Integer.MAX_VALUE,
				60L, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());
		for (int i = 10; i >= 1; i--) {
			final int finalI = i;
			CompletableFuture.runAsync(() -> {
						
						
						System.out.println(finalI);
						try {
							
							TimeUnit.SECONDS.sleep((long) (finalI * 0.1));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}, threadPoolExecutor)
					.whenComplete((data, err) -> {
						
						
						System.out.println(err.getMessage());
						
					})
					
					.isDone();
			
			
		}
		
		
		// // 让main线程暂停1秒，使得t1线程，先执行
		// try {
		// 	TimeUnit.SECONDS.sleep(1);
		// } catch (InterruptedException e) {
		// 	e.printStackTrace();
		// }
		//
		// 1秒后，启动t2线程，开始占用这个锁
		// new Thread(() -> {
		//
		// 	// 开始占有锁
		// 	spinLockDemo.myLock();
		// 	// 开始释放锁
		// 	spinLockDemo.myUnLock();
		//
		// }, "t2").start();
		
		// CompletableFuture.runAsync(() -> {
		// 	// 开始占有锁
		// 	spinLockDemo.myLock();
		//
		//
		// 	try {
		//
		// 		TimeUnit.SECONDS.sleep(1);
		// 	} catch (InterruptedException e) {
		// 		e.printStackTrace();
		// 	}
		// }).whenComplete((data, err) -> {
		// 	System.out.println(data);
		// 	// 开始释放锁
		// 	spinLockDemo.myUnLock();
		// });
	}
	
	public void myLock() {
		// 获取当前进来的线程
		Thread thread = Thread.currentThread();
		System.out.println(Thread.currentThread().getName() + "\t come in ");
		
		// 开始自旋，期望值是null，更新值是当前线程，如果是null，则更新为当前线程，否者自旋
		while (!atomicReference.compareAndSet(null, thread)) {
		}
	}
	
	/**
	 * 解锁
	 */
	public void myUnLock() {
		
		// 获取当前进来的线程
		Thread thread = Thread.currentThread();
		
		// 自己用完了后，把atomicReference变成null
		atomicReference.compareAndSet(thread, null);
		
		System.out.println(Thread.currentThread().getName() + "\t invoked myUnlock()");
	}
}
