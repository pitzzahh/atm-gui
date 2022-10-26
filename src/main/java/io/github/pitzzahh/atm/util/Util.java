package io.github.pitzzahh.atm.util;

import io.github.pitzzahh.util.utilities.classes.DynamicArray;
import java.util.concurrent.atomic.AtomicReference;
import javafx.animation.PauseTransition;
import static java.lang.String.format;
import io.github.pitzzahh.atm.Atm;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.scene.Parent;

public interface Util {

    AtomicReference<Double> horizontal = new AtomicReference<>(0.0);
    AtomicReference<Double> vertical = new AtomicReference<>(0.0);
    DynamicArray<Parent> parents = new DynamicArray<>();

    static void remove(Label label, Duration duration) {
        var visiblePause = new PauseTransition(duration);
        visiblePause.setOnFinished(
                event -> label.setVisible(false)
        );
        visiblePause.play();
    }

    /**
     * Moves the window to where the cursor dragged the window
     * @param parent the parent node.
     */
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

    /**
     * Adds a parent to the parents array.
     * @param parent the parent to add.
     */
    static void addParent(Parent parent) {
        parents.insert(parent);
    }

    /**
     * Add a list parent to the parents array.
     * @param p the list of parents.
     */
    static void addParents(Parent... p) {
        parents.insert(p);
    }

    /**
     * Gets the parent with the specified id.
     * @param id the id of the parent.
     * @return the parent with the specified id.
     */
    static Parent getWindow(String id) {
        return parents.stream()
                .filter(parent -> parent.getId().equals(id))
                .findAny()
                .orElseThrow(() -> new IllegalStateException(format("Cannot find parent with [%s] id", id)));
    }

}
