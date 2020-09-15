package com.example.common.common.logger.thread.wrapper;

import com.example.common.common.logger.thread.util.ThreadMdcUtils;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.MDC;

public class ThreadPoolExecutorMdcWrapper extends ThreadPoolExecutor {
  public ThreadPoolExecutorMdcWrapper(
      int corePoolSize,
      int maximumPoolSize,
      long keepAliveTime,
      TimeUnit unit,
      BlockingQueue<Runnable> workQueue) {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
  }

  public ThreadPoolExecutorMdcWrapper(
      int corePoolSize,
      int maximumPoolSize,
      long keepAliveTime,
      TimeUnit unit,
      BlockingQueue<Runnable> workQueue,
      ThreadFactory threadFactory) {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
  }

  public ThreadPoolExecutorMdcWrapper(
      int corePoolSize,
      int maximumPoolSize,
      long keepAliveTime,
      TimeUnit unit,
      BlockingQueue<Runnable> workQueue,
      RejectedExecutionHandler handler) {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
  }

  public ThreadPoolExecutorMdcWrapper(
      int corePoolSize,
      int maximumPoolSize,
      long keepAliveTime,
      TimeUnit unit,
      BlockingQueue<Runnable> workQueue,
      ThreadFactory threadFactory,
      RejectedExecutionHandler handler) {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
  }

  @Override
  public void execute(Runnable task) {
    super.execute(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()));
  }

  @Override
  public <T> Future<T> submit(Runnable task, T result) {
    return super.submit(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()), result);
  }

  @Override
  public <T> Future<T> submit(Callable<T> task) {
    return super.submit(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()));
  }

  @Override
  public Future<?> submit(Runnable task) {
    return super.submit(ThreadMdcUtils.wrap(task, MDC.getCopyOfContextMap()));
  }
}
