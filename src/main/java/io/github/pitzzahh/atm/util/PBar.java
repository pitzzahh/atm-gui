package io.github.pitzzahh.atm.util;

import javafx.scene.control.ProgressBar;
import io.github.pitzzahh.atm.Atm;
import javafx.concurrent.Service;
import javafx.concurrent.Task;


public class PBar {

    public static Service<Void> showProgressBar(ProgressBar progressBar) {
        var service = new TaskService();
        progressBar.setVisible(true);
        Atm.getLogger().debug(progressBar.isVisible() ? "Progress bar is visible" : "Progress bar is not visible");
        service.setOnSucceeded(event -> progressBar.progressProperty().unbind());
        progressBar.progressProperty().bind(service.progressProperty());
        return service;
    }

    private static class TaskService extends Service<Void> {
        @Override
        protected Task<Void> createTask() {
            return new Task<>() {
                @Override
                protected Void call() throws Exception {
                    for (int i = 0; i <= 100; i++) {
                        Thread.sleep(40);
                        updateProgress(i, 100);
                    }
                    return null;
                }
            };
        }
    }
}
