package net.alek.buttonclicker.components;

import net.alek.buttonclicker.engine.ErrorHandler;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class ATimer {
    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> future;
    private Runnable runnable;

    private long intervalTime;
    private boolean isLooping;

    private long startTime;
    private long pauseTime;
    private long remainingTime;

    private final AtomicBoolean isPaused = new AtomicBoolean(false);

    public ATimer() {
        this.scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        this.isLooping = false;
    }

    public void setTask(Runnable runnable) {
        this.runnable = runnable;
    }

    public void setInterval(long seconds) {
        this.intervalTime = seconds * 1000;
        this.remainingTime = this.intervalTime;
    }

    public Runnable getTask() {
        return runnable;
    }

    public long getInterval() {
        return intervalTime / 1000;
    }

    public boolean isPaused() {
        return isPaused.get();
    }

    public boolean isLooping() {
        return isLooping;
    }

    public void start() {
        if (runnable == null) {
            ErrorHandler.Exception(new IllegalStateException("A Task For The Timer Was Not Set Yet!"));
        }

        stop();
        startTime = System.currentTimeMillis();
        isPaused.set(false);
        scheduleTask(intervalTime);
    }

    public void stop() {
        if (future != null && !future.isCancelled()) {
            future.cancel(false);
        }
        scheduler.shutdownNow();
        scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        remainingTime = intervalTime;
        isPaused.set(false);
    }

    public void pause() {
        if (!isPaused.get() && future != null && !future.isCancelled()) {
            pauseTime = System.currentTimeMillis();
            remainingTime = intervalTime - (pauseTime - startTime);

            future.cancel(false);
            isPaused.set(true);
        }
    }

    public void resume() {
        if (isPaused.get()) {
            startTime = System.currentTimeMillis();
            scheduleTask(remainingTime);
            isPaused.set(false);
        }
    }

    public void restart() {
        stop();
        start();
    }

    public void setLooping(boolean looping) {
        this.isLooping = looping;
    }

    private void scheduleTask(long delayMillis) {
        future = scheduler.schedule(() -> {
            try {
                runnable.run();
            } finally {
                if (isLooping && !isPaused.get()) {
                    startTime = System.currentTimeMillis();
                    scheduleTask(intervalTime);
                } else {
                    scheduler.shutdown();
                }
            }
        }, delayMillis, TimeUnit.MILLISECONDS);
    }
}