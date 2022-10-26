package io.github.pitzzahh.atm.controllers;

import static io.github.pitzzahh.atm.Atm.getStage;
import static io.github.pitzzahh.atm.util.Util.initToolTip;
import static io.github.pitzzahh.atm.util.Util.getWindow;
import io.github.pitzzahh.util.utilities.SecurityUtil;
import static io.github.pitzzahh.atm.Atm.getLogger;
import java.util.concurrent.atomic.AtomicReference;
import io.github.pitzzahh.atm.validator.Validator;
import javafx.scene.control.ProgressBar;
import io.github.pitzzahh.atm.util.PBar;
import io.github.pitzzahh.atm.util.Util;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.fxml.FXML;

/**
 * FXML Controller class
 * @author pitzzahh
 */
public class LoginController {

    @FXML
    public ProgressBar progressBar;

    @FXML
    private TextField accountNumberField;

    @FXML
    private Label message;

    /**
     * Checks if the Enter key is pressed and invokes the check() method.
     * @param keyEvent the key event.
     */
    @FXML
    public void onEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            getLogger().debug("Enter key pressed");
            checkCredential();
        }
    }

    /**
     * Checks if the account number entered exists in the database as an account.
     */
    private void checkCredential() {
        try {
            final var fieldText = accountNumberField.getText();
            final var $admin = SecurityUtil.decrypt("QGRtMW4xJHRyNHQwcg==");
            var debugMessage = new AtomicReference<>("");
            getLogger().debug("Admin account number: {}", $admin);
            if (fieldText.equals($admin)) {
                var adminWindow = getWindow("admin_window");
                var scene = new Scene(adminWindow);
                var service = PBar.showProgressBar(progressBar);
                service.setOnSucceeded(event -> {
                    getStage().close();
                    Util.moveWindow(adminWindow);
                    getStage().setScene(scene);
                    getStage().show();
                    debugMessage.set("Welcome admin!");
                });
                service.start();
            }
            else {
                var doesAccountExist = Validator.doesAccountExist(fieldText);
                if (doesAccountExist) debugMessage.set("Account exists");
                else debugMessage.set("Account does not exist");
                message.setText(debugMessage.get());
            }
            getLogger().debug(debugMessage.get());
        } catch (RuntimeException runtimeException) {
            message.setText(runtimeException.getMessage());
            getLogger().error(runtimeException.getMessage());
        }
    }

    /**
     * Checks if the account number field is empty, then clears the message label.
     * @param keyEvent the key event
     */
    @FXML
    public void onKeyTyped(KeyEvent keyEvent) {
        if (accountNumberField.getText().isEmpty() && keyEvent.getCode() != KeyCode.ENTER) {
            getLogger().debug("EMPTY ACCOUNT NUMBER FIELD");
            message.setText("");
            getLogger().debug("showing tooltip");
            accountNumberField.getTooltip().show(getStage());
        }
        else accountNumberField.getTooltip().hide();
    }

    /**
     * Creates a simple tooltip when mouse is hovered over the account number field.
     * @param mouseEvent the mouse event.
     */
    public void onMouseEntered(MouseEvent mouseEvent) {
        getLogger().debug("Mouse entered the account number field");
        var $an = accountNumberField.getText().trim();
        var toolTip = initToolTip(
                "Enter your account number",
                mouseEvent,
                "-fx-background-color: #CFD7DF; " +
                        "-fx-text-fill: #D50000; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-family: Jetbrains Mono;" +
                        "-fx-font-size: 15px;"
        );
        if ($an.isEmpty()) {
            getLogger().debug("Setting tooltip");
            accountNumberField.setTooltip(toolTip);
            accountNumberField.getTooltip().setShowDuration(Duration.seconds(3));
        } else accountNumberField.getTooltip().hide();
    }
}
