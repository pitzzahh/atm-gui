package io.github.pitzzahh.atmGui.controllers;

import static io.github.pitzzahh.atmGui.Atm.getLogger;
import static io.github.pitzzahh.atmGui.Atm.getStage;
import io.github.pitzzahh.atmGui.validator.Validator;
import static io.github.pitzzahh.atmGui.util.Util.*;
import java.util.concurrent.atomic.AtomicReference;
import io.github.pitzzahh.atmGui.util.PBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import javafx.stage.Stage;
import java.util.Optional;
import javafx.scene.Scene;
import javafx.fxml.FXML;

/**
 * FXML Controller class
 * @author pitzzahh
 */
public class AccountCheckerController {

    @FXML
    public ProgressBar progressBar;

    @FXML
    private TextField accountNumberField;

    @FXML
    private Label message;

    private Stage stage;

    /**
     * Checks if the Enter key is pressed and invokes the check() method.
     * @param keyEvent the key event.
     */
    @FXML
    public void onEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            getLogger().debug("Enter key pressed");
            accountNumberField.setVisible(false);
            checkCredential();
        }
    }

    /**
     * Checks if the account number entered exists in the database as an account.
     */
    private void checkCredential() {
        try {
            final var fieldText = accountNumberField.getText();
            var debugMessage = new AtomicReference<>("");
            getLogger().debug("Admin account number: {}", $admin);
            if (fieldText.equals($admin)) {
                var service = PBar.showProgressBar(progressBar);
                service.setOnSucceeded(event -> {
                    var adminWindow = getWindow("admin_window");
                    getStage().close();
                    moveWindow(adminWindow);
                    if (adminWindow.getScene() != null) getStage().setScene(adminWindow.getScene()); // if scene is present, get it
                    else getStage().setScene(new Scene(adminWindow)); // create new scene if new login
                    getStage().setTitle("Administrator");
                    getStage().setResizable(true);
                    getStage().centerOnScreen();
                    getStage().show();
                    accountNumberField.clear();
                    accountNumberField.setVisible(true);
                    debugMessage.set("Welcome admin!");
                });
                service.start();
            }
            else {
                var doesAccountExist = Validator.doesAccountExist(fieldText);
                if (doesAccountExist) {
                    debugMessage.set("Account exists");
                    var clientWindow = getWindow("client_window");
                    getStage().close();
                    moveWindow(clientWindow);
                    if (clientWindow.getScene() != null) getStage().setScene(clientWindow.getScene()); // if scene is present, get it
                    else getStage().setScene(new Scene(clientWindow)); // create new scene if new login
                    getStage().setTitle("Client");
                    getStage().centerOnScreen();
                    getStage().show();
                    accountNumberField.clear();
                    accountNumberField.setVisible(true);
                }
                else debugMessage.set("Account does not exist");
                message.setText(debugMessage.get());
                accountNumberField.setVisible(true);
            }
            getLogger().debug(debugMessage.get());
        } catch (RuntimeException runtimeException) {
            message.setText(runtimeException.getMessage());
            getLogger().error(runtimeException.getMessage());
            accountNumberField.setVisible(true);
        }
    }

    /**
     * Checks if the account number field is empty, then clears the message label.
     * @param keyEvent the key event
     */
    @FXML
    public void onKeyTyped(KeyEvent keyEvent) {
        addTextLimiter(accountNumberField, 9);
        if (accountNumberField.getText().isEmpty() && keyEvent.getCode() != KeyCode.ENTER) {
            getLogger().debug("EMPTY ACCOUNT NUMBER FIELD");
            message.setText("");
        }
    }

    /**
     * Shows the tooltip when the mouse is hovered over the account number field. and if th field is empty.
     * @param mouseEvent the mouse event.
     */
    public void onMouseEntered(MouseEvent mouseEvent) {
        if (stage != null) stage = (Stage) accountNumberField.getScene().getWindow();
        getLogger().debug("Mouse entered the account number field");
        var $an = accountNumberField.getText().trim();
        var optionalTooltip = Optional.ofNullable(accountNumberField.getTooltip());
        if ($an.isEmpty()) {
            if (optionalTooltip.isEmpty()) {
                getLogger().debug("Setting tooltip");
                var toolTip = getFieldToolTip(mouseEvent);
                toolTip.setShowDuration(Duration.seconds(3));
                accountNumberField.setTooltip(toolTip);
            }
        } else if (optionalTooltip.isPresent()) {
            getLogger().debug("Hiding tooltip");
            accountNumberField.setTooltip(null);
        }
    }

    /**
     * Creates a simple tooltip when mouse is hovered over the account number field.
     * @param mouseEvent the mouse event.
     * @return a tooltip.
     */
    private static Tooltip getFieldToolTip(MouseEvent mouseEvent) {
        return initToolTip(
                "Enter your account number",
                mouseEvent,
                "-fx-background-color: #CFD7DF; " +
                        "-fx-text-fill: #D50000; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-family: Jetbrains Mono;" +
                        "-fx-font-size: 15px;"
        );
    }
}
