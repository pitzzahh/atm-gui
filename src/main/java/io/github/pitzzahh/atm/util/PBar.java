package io.github.pitzzahh.atm.util;

import io.github.pitzzahh.atm.Atm;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;


public class PBar {

    public static void showProgress(ProgressBar progressBar) {
        var service = new TaskService();
        var thread = new Thread(service.createTask());
        thread.start();
        progressBar.progressProperty().bind(service.progressProperty());
    }

    private static class TaskService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    for (int i = 0; i < 100; i++) {
                        Thread.sleep(40);
                        Atm.getLogger().debug("Progress: {}", i);
                        updateProgress(i, 100);
                    }
                    return null;
                }
            };
        }
    }
}
