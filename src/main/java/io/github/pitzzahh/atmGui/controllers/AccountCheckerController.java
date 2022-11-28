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
    @FXML
    private void checkCredential() {

        final var fieldText = accountNumberField.getText();
        var debugMessage = new AtomicReference<>("");
        getLogger().debug("Admin account number: {}", $admin);

        final var progressBarService = PBar.showProgressBar(progressBar);
        // TODO: move the label on top of the progress bar.
        progressBarService.setOnScheduled(e -> {
            message.setText("Please Wait");
            message.setStyle("" +
                    "-fx-font-family: JetBrains Mono;" +
                    "fx-font-size: 20px;" +
                    "-fx-text-fill: #2100C4;" +
                    "-fx-font-weight: bold;"
            );
        });
        progressBarService.setOnSucceeded(stateEvent -> {
            message.setStyle("" +
                    "-fx-font-family: JetBrains Mono;" +
                    "-fx-font-size: 20px;" +
                    "-fx-text-fill: #d62828;" +
                    "-fx-font-weight: bold;"
            );
            checker(fieldText, debugMessage);
        });
        progressBarService.start();
    }

    /**
     * To shorten the lambda above.
     * @param fieldText the input from the user.
     * @param debugMessage the message for debugging. also the error message.
     */
    private void checker(String fieldText, AtomicReference<String> debugMessage) {
        if (fieldText.equals($admin)) {
            var adminWindow = getParent("admin_window");
            getStage().close();
            moveWindow(adminWindow);
            if (adminWindow.getScene() != null) getStage().setScene(adminWindow.getScene()); // if scene is present, get it
            else getStage().setScene(new Scene(adminWindow)); // create new scene if new login
            getStage().setTitle("Administrator");
            getStage().setResizable(true);
            getStage().centerOnScreen();
            getStage().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
                if (KeyCode.F11.equals(event.getCode())) getStage().setFullScreen(!getStage().isFullScreen());
            });
            getStage().show();
            progressBar.setVisible(false);
            accountNumberField.clear();
            accountNumberField.setVisible(true);
            debugMessage.set("Welcome admin!");
            progressBar.progressProperty().unbind();
        }
        else {
            try {
                final boolean doesAccountExist = Validator.doesAccountExist(fieldText);
                if (doesAccountExist) {
                    debugMessage.set("Account exists");
                    var clientWindow = getParent("client_window");
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
            } catch (RuntimeException runtimeException) {
                message.setText(runtimeException.getMessage());
                getLogger().error(runtimeException.getMessage());
                accountNumberField.setVisible(true);
                progressBar.setVisible(false);
            }
        }
        getLogger().debug(debugMessage.get());
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
    @FXML
    public void onMouseEntered(MouseEvent mouseEvent) {
        if (stage != null) stage = (Stage) accountNumberField.getScene().getWindow();
        getLogger().debug("Mouse entered the account number field");
        final String $an = accountNumberField.getText().trim();
        final Optional<Tooltip> optionalTooltip = Optional.ofNullable(accountNumberField.getTooltip());
        if ($an.isEmpty()) {
            if (optionalTooltip.isEmpty()) {
                getLogger().debug("Setting tooltip");
                Tooltip toolTip = getFieldToolTip(mouseEvent);
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
    @FXML
    private static Tooltip getFieldToolTip(MouseEvent mouseEvent) {
        return initToolTip(
                "Enter your account number",
                mouseEvent,
                errorToolTipStyle()
        );
    }
}
