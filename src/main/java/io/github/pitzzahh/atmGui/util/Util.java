package io.github.pitzzahh.atmGui.util;

import io.github.pitzzahh.util.utilities.classes.DynamicArray;
import io.github.pitzzahh.util.utilities.SecurityUtil;
import java.util.concurrent.atomic.AtomicReference;
import static io.github.pitzzahh.atmGui.Atm.getStage;
import javafx.scene.control.TextField;
import static java.lang.String.format;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Tooltip;
import io.github.pitzzahh.atmGui.Atm;
import javafx.scene.Parent;

/**
 * Utility interface for the ATM application.
 */
public interface Util {


    String $admin = SecurityUtil.decrypt("QGRtMW4xJHRyNHQwcg==");

    /**
     * Moves the window to where the cursor dragged the window
     * @param parent the parent node.
     */
    static void moveWindow(Parent parent) {
        var horizontal = new AtomicReference<>(0.0);
        var vertical = new AtomicReference<>(0.0);
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
        Fields.parents.insert(parent);
    }

    /**
     * Add a list parent to the parents array.
     * @param p the list of parents.
     */
    static void addParents(Parent... p) {
        Fields.parents.insert(p);
    }

    /**
     * Gets the parent with the specified id.
     * @param id the id of the parent.
     * @return the parent with the specified id.
     */
    static Parent getWindow(String id) {
        return Fields.parents.stream()
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

    static void addTextLimiter(final TextField textField, final int maxLength) {
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            Atm.getLogger().debug($admin.substring(0, maxLength));
            if ((textField.getText().length() > maxLength)) {
                var limitedInput = textField.getText().substring(0, maxLength);
                if (!limitedInput.equals($admin.substring(0, maxLength))) textField.setText(limitedInput);
            }
        });
    }
}
class Fields {
    static DynamicArray<Parent> parents = new DynamicArray<>();
}