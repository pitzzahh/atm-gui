package io.github.pitzzahh.atm.util;

import java.util.concurrent.atomic.AtomicReference;
import javafx.animation.PauseTransition;
import io.github.pitzzahh.atm.Atm;
import javafx.scene.control.Label;
import javafx.scene.Parent;
import javafx.util.Duration;

public interface Util {

    AtomicReference<Double> horizontal = new AtomicReference<>(0.0);
    AtomicReference<Double> vertical = new AtomicReference<>(0.0);

    static void remove(Label label, Duration duration) {
        var visiblePause = new PauseTransition(duration);
        visiblePause.setOnFinished(
                event -> label.setVisible(false)
        );
        visiblePause.play();
    }

    static void moveWindow(Parent parent) {
        parent.setOnMousePressed(event -> {
            horizontal.set(event.getSceneX());
            vertical.set(event.getSceneY());
        });
        parent.setOnMouseDragged(event -> {
            Atm.getStage().setX(event.getScreenX() - horizontal.get());
            Atm.getStage().setY(event.getScreenY() - vertical.get());
        });
    }

}
