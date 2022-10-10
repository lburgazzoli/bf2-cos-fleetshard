package org.bf2.cos.fleetshard.sync;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class FleetShardSyncScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FleetShardSyncScheduler.class);

    private Map<String, ScheduledFuture<?>> futures;
    private ScheduledExecutorService executor;

    @PostConstruct
    void setUp() {
        futures = new ConcurrentHashMap<>();
        executor = Executors.newScheduledThreadPool(1);
    }

    @PreDestroy
    void cleanUp() {
        if (executor != null) {
            executor.shutdownNow();
            executor = null;
        }
        if (futures != null) {
            futures = null;
        }
    }

    public void schedule(String id, Runnable runnable, Duration interval) {
        if (interval.isZero()) {
            LOGGER.info("Skipping scheduling job of type {} with id {} as the duration is zero", id,
                runnable.getClass().getName());
            return;
        }

        var old = futures.put(id, executor.scheduleWithFixedDelay(
            runnable,
            0,
            interval.toMillis(),
            TimeUnit.MILLISECONDS));

        if (old != null) {
            old.cancel(true);
        }
    }

    public void shutdown(String id) {
        ScheduledFuture<?> future = futures.get(id);

        if (future != null) {
            try {
                future.cancel(true);
            } catch (Exception e) {
                LOGGER.debug("Failure stopping task", e);
            }
        }
    }
}
