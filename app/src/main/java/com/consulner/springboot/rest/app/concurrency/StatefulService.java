package com.consulner.springboot.rest.app.concurrency;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StatefulService {

  private static Logger log = LoggerFactory.getLogger(StatefulService.class);
  Map<Long, String> lockedCache1 = new ConcurrentHashMap<Long, String>();
  Map<Long, String> lockedCache2 = new ConcurrentHashMap<Long, String>();

  Map<Long, String> rwLockedCache = new ConcurrentHashMap<Long, String>();

  private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
  private final Lock r = rwl.readLock();
  private final Lock w = rwl.writeLock();
  private final ReentrantLock lock = new ReentrantLock(true);


  public void add(Long id, String value) {
    log.info("add, waiting for r lock");
    r.lock();
    log.info("add, acquired r lock");
    try {
      rwLockedCache.put(id, value);
    } finally {
      r.unlock();
      log.info("add, released r lock");
    }
  }

  public void compute() {

    // do some stuff that does not require atomic access

    // then do atomic stuff
    try {
      log.info("compute, waiting for w lock");
      w.lock();
      log.info("compute, acquired w lock");
      Map tempMap = rwLockedCache;
      rwLockedCache = new ConcurrentHashMap<Long, String>(256);
    } finally {
      w.unlock();
      log.info("compute, released w lock");
    }

    // then do the rest that does not require atomic access

    // then again do these lines atomically
    log.info("compute, waiting for lock");
    lock.lock();
    log.info("compute, acquired lock");
    try {

      // don't even need to do anything on the collections, just anything
     waitMinutes(3);

    } catch (InterruptedException e) {
      log.error("compute, error, interrupted sleep", e);
    } finally {
      lock.unlock();
      log.info("compute, released lock");
    }
  }

  private void waitMinutes(int minutes) throws InterruptedException {
    Thread.sleep(minutes * 60 * 1000);
  }

  void batchRemove(Collection<Long> idsForRemove) {

    // do some stuff that does not require atomic access

    // do atomic operation
    log.info("batchRemove, waiting for lock");
    lock.lock();
    log.info("batchRemove, lock acquired");
    try {
      // does not even need to do sth on collections, but if it does then the
      // collections must be thread-safe too if used from other places
      // there this lock is not set.
      for (Long id : idsForRemove) {
        lockedCache1.remove(id);
        lockedCache2.remove(id);
      }
    } finally {
      lock.unlock();
      log.info("batchRemove, lock acquired");
    }

    // do the rest that does not need to be done atomically
  }
}
