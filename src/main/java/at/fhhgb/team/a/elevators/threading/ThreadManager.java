package at.fhhgb.team.a.elevators.threading;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadManager {

    private static ThreadManager instance;

    private ScheduledExecutorService executorService;

    private ThreadManager() {
        executorService = Executors.newScheduledThreadPool(5);
    }

    public static ThreadManager getInstance() {
        if (null == instance) {
            instance = new ThreadManager();
        }
        return instance;
    }

    public void scheduleRunnable(Runnable runnable, int periodInMs) {
        if (executorService.isTerminated()) {
            executorService = Executors.newScheduledThreadPool(5);
        }

        executorService.scheduleAtFixedRate(runnable, 0, periodInMs, TimeUnit.MILLISECONDS);
    }

    public void stopCurrentTasks() {
        executorService.shutdownNow();
    }
}
