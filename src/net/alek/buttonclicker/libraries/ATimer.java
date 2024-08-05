package net.alek.buttonclicker.libraries;

import java.util.Timer;
import java.util.TimerTask;

public class ATimer {
    private Timer timer;
    private TimerTask task;
    private Runnable runnable;
    private long intervalTime;
    private boolean isPaused;
    private boolean isLooping;
    private long pauseTime;
    private long remainingTime;

    public ATimer() {
        this.timer = new Timer();
        this.isPaused = false;
        this.isLooping = false;
    }

    public void setTask(Runnable runnable) {
        this.runnable = runnable;
    }

    public void setDelay(long seconds) {
        this.intervalTime = seconds * 1000;
        this.remainingTime = this.intervalTime;
    }

    public Runnable getTask() {
        return this.runnable;
    }

    public long getIntervalTime() {
        return this.intervalTime / 1000;
    }

    public boolean isPaused() {
        return this.isPaused;
    }

    public boolean isLooping() {
        return this.isLooping;
    }

    public void start() {
        if (runnable == null) {
            throw new IllegalStateException("A Task For The Timer Was Not Set Yet!");
        }
        stop();
        scheduleTask(intervalTime);
    }

    public void stop() {
        if (task != null) {
            task.cancel();
        }
        timer.purge();
        isPaused = false;
        remainingTime = intervalTime;
    }

    public void pause() {
        if (!isPaused && task != null) {
            task.cancel();
            pauseTime = System.currentTimeMillis();
            if (intervalTime != 0) {
                remainingTime = intervalTime - (pauseTime % intervalTime);
            } else {
                remainingTime = 0;
            }
            timer.purge();
            isPaused = true;
        }
    }

    public void resume() {
        if (isPaused) {
            scheduleTask(remainingTime);
            isPaused = false;
        }
    }

    public void restart() {
        stop();
        start();
    }

    public void setLooping(boolean isLooping) {
        this.isLooping = isLooping;
    }

    private void scheduleTask(long delay) {
        task = new TimerTask() {
            @Override
            public void run() {
                runnable.run();
                if (isLooping) {
                    scheduleTask(intervalTime);
                }
            }
        };
        timer.schedule(task, delay);
    }
}