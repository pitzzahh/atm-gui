package io.github.pitzzahh.atm.util;

import io.github.pitzzahh.util.utilities.classes.DynamicArray;
import java.util.concurrent.atomic.AtomicReference;
import static io.github.pitzzahh.atm.Atm.getStage;
import static java.lang.String.format;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Tooltip;
import javafx.scene.Parent;

/**
 * Utility interface for the ATM application.
 */
public interface Util {

    AtomicReference<Double> horizontal = new AtomicReference<>(0.0);
    AtomicReference<Double> vertical = new AtomicReference<>(0.0);
    DynamicArray<Parent> parents = new DynamicArray<>();

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
            getStage().setX(event.getScreenX() - horizontal.get());
            getStage().setY(event.getScreenY() - vertical.get());
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

    /**
     * Sets the tooltip for the specified control.
     * @param tip the tooltip.
     * @param event the mouse event.
     * @return the tooltip.
     * @see Tooltip
     */
    static Tooltip initToolTip(String tip, MouseEvent event, String styles) {
        var toolTip = new Tooltip(tip);
        toolTip.setX(event.getScreenX());
        toolTip.setY(event.getScreenY());
        toolTip.setStyle(styles);
        return toolTip;
    }

    static String adminButtonFunctionsToolTip() {
        return "-fx-background-color: #003049; " +
               "-fx-text-fill: white; " +
               "-fx-font-weight: bold; " +
               "-fx-font-family: Jetbrains Mono;" +
               "-fx-font-size: 15px;";
    }

}
