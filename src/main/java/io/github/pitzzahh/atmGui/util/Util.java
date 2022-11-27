package io.github.pitzzahh.atmGui.util;

import io.github.pitzzahh.atmGui.Atm;
import io.github.pitzzahh.util.utilities.classes.DynamicArray;
import io.github.pitzzahh.util.utilities.SecurityUtil;

import static io.github.pitzzahh.atmGui.Atm.getLogger;
import static io.github.pitzzahh.atmGui.Atm.getStage;
import java.util.concurrent.atomic.AtomicReference;
import io.github.pitzzahh.util.utilities.FileUtil;
import io.github.pitzzahh.atmGui.entity.Client;
import static java.lang.String.format;
import javafx.scene.layout.BorderPane;
import javafx.scene.input.MouseEvent;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.util.Duration;
import java.io.IOException;
import java.io.File;
import java.util.*;

/**
 * Utility interface for the ATM application.
 */
public interface Util {

    /**
     * admin credentials.
     */
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
     * takes an array of parents
     */
    Consumer<Parent[]> addParents = Fields.parents::insert;

    /**
     * Gets the parent with the specified id.
     * @param id the id of the parent.
     * @return the parent with the specified id.
     */
    static Parent getParent(String id) {
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

    /**
     * Gets the button styles for admin window
     * @return styles for admin window buttons.
     */
    static String adminButtonFunctionsToolTipStyle() {
        return "-fx-background-color: #003049; " +
               "-fx-text-fill: white; " +
               "-fx-font-weight: bold; " +
               "-fx-font-family: Jetbrains Mono;" +
               "-fx-font-size: 15px;";
    }

    static String errorToolTipStyle() {
        return "-fx-background-color: #CFD7DF; " +
                "-fx-text-fill: #D50000; " +
                "-fx-font-weight: bold; " +
                "-fx-font-family: Jetbrains Mono;" +
                "-fx-font-size: 15px;";
    }

    /**
     * Adds a limit to the specified text field.
     * @param textField the text field to add the limit.
     * @param maxLength the maximum length of the text field.
     */
    static void addTextLimiter(final TextField textField, final int maxLength) {
        textField.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if ((textField.getText().length() > maxLength)) {
                var limitedInput = textField.getText().substring(0, maxLength);
                if (!limitedInput.equals($admin.substring(0, maxLength))) textField.setText(limitedInput);
            }
        });
    }

    /**
     * Sets the center border pane to the specific window.
     * @param actionEvent the action event.
     * @param id the id of the window.
     */
    static void setCenterScreenOfBorderPane(ActionEvent actionEvent, String id) {
        final var BORDER_PANE = ((BorderPane)(((Button) actionEvent.getSource()).getParent().getParent()));
        BORDER_PANE.setCenter(Util.getParent(id));
    }

    /**
     * Get all the locations address as a {@code Set<String>}
     */
    Supplier<Set<String>> getLocations = () -> Fields.locations;

    /**
     * Used to modify the progress bar from the main window.
     * @param parent the main window parent.
     * @return an {@code Optional<ProgressBar>}.
     */
    static Optional<ProgressBar> getMainProgressBar(Parent parent) {
        return parent.getChildrenUnmodifiable().stream().findAny()
                .map(n -> (Pane) n)
                .map(Pane::getChildren)
                .map(e -> e.get(e.size() - 1))
                .map(e -> (ProgressBar) e)
                .stream().findAny();
    }

    /**
     * Used to modify the message label from the main window.
     * @param parent the main window parent.
     * @return an {@code Optional<Label>}.
     */
    static Optional<Label> getMessageLabel(Parent parent) {
        return parent.getChildrenUnmodifiable().stream().findAny()
                .map(n -> (Pane) n)
                .map(Pane::getChildren)
                .map(e -> e.get(e.size() - 5))
                .map(e -> (HBox) e)
                .map(HBox::getChildren)
                .map(e -> (Label) e.get(0))
                .stream()
                .findAny();
    }

    static void addActiveButtons(Button button) {
        boolean b = Fields.activeButtons.stream()
                .anyMatch(button1 -> button1.getId().equals(button.getId()));
        if (!b) Fields.activeButtons.insert(button);
    }

    static Optional<Button> getActiveButton(String id) {
        return Fields.activeButtons.stream()
                .filter(button -> button.getId().equals(id))
                .findAny();
    }

    static boolean checkInputs(Button button, MouseEvent event, String firstName, String lastName, String address, DatePicker date) {
        if (firstName.isEmpty() && (lastName.isEmpty() || address.isEmpty() || date == null)) {
            Tooltip tooltip = initToolTip(
                    "Cannot Save Client, First Name is empty",
                    event,
                    errorToolTipStyle()
            );
            tooltip.setShowDuration(Duration.seconds(3));
            button.setTooltip(tooltip);
            getLogger().error("NO FIRST NAME");
            return false;
        }
        if (lastName.isEmpty() && (address.isEmpty() || date == null)) {
            Tooltip tooltip = initToolTip(
                    "Cannot Save Client, Last Name is empty",
                    ((MouseEvent) event.getSource()),
                    errorToolTipStyle()
            );
            tooltip.setShowDuration(Duration.seconds(3));
            button.setTooltip(tooltip);
            getLogger().error("NO LAST NAME");
            return false;
        }
        if (address.isEmpty() && date != null) {
            Tooltip tooltip = initToolTip(
                    "Cannot Save Client, Address is empty",
                    ((MouseEvent) event.getSource()),
                    errorToolTipStyle()
            );
            tooltip.setShowDuration(Duration.seconds(3));
            button.setTooltip(tooltip);
            getLogger().error("NO ADDRESS");
            return false;
        }
        if (date == null) {
            Tooltip tooltip = initToolTip(
                    "Cannot Save Client, Date is empty",
                    ((MouseEvent) event.getSource()),
                    errorToolTipStyle()
            );
            tooltip.setShowDuration(Duration.seconds(3));
            button.setTooltip(tooltip);
            getLogger().error("NO DATE");
            return false;
        }
        return true;
    }

    static void fillTable(Client client, TableView<Client> tableView) {
        tableView.getItems().add(client);
    }

    static String generateRandomAccountNumber() {
        return String.valueOf(new Random().nextInt(999999999) + 1);
    }

    static String generateRandomPin() {
        return String.valueOf(new Random().nextInt(9999) + 1);
    }
}

/**
 * Fields for the Util interface.
 */
class Fields {
    /**
     * The parents array.
     */
    static DynamicArray<Parent> parents = new DynamicArray<>();
    static DynamicArray<Button> activeButtons = new DynamicArray<>();
    static Set<String> locations;
    static {
        try {
            locations = FileUtil.getFileContents(
                            new File("src/main/resources/io/github/pitzzahh/atmGui/locations/locations.txt"),
                            0,
                            ","
                    ).stream()
                    .map(Arrays::toString)
                    .map(e -> e.replaceAll("[\\[\\]]", ""))
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
