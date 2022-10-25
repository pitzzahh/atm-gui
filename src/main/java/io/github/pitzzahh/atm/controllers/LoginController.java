package io.github.pitzzahh.atm.controllers;

import static io.github.pitzzahh.atm.util.Util.getWindow;
import io.github.pitzzahh.util.utilities.SecurityUtil;
import static io.github.pitzzahh.atm.Atm.getLogger;
import io.github.pitzzahh.atm.validator.Validator;
import javafx.scene.input.InputMethodEvent;
import io.github.pitzzahh.atm.util.Util;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import io.github.pitzzahh.atm.Atm;
import java.io.IOException;
import javafx.scene.Scene;
import javafx.fxml.FXML;

/**
 * FXML Controller class
 * @author pitzzahh
 */
public class LoginController {

    @FXML
    private TextField accountNumberField;

    @FXML
    private Label message;

    /**
     * Checks if the Enter key is pressed and invokes the check() method.
     * @param keyEvent the key event.
     */
    @FXML
    public void onEnter(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            getLogger().debug("Enter key pressed");
            checkCredential();
        }
    }

    /**
     * Checks if the account number entered exists in the database as an account.
     */
    private void checkCredential() throws IOException {
        try {
            final var fieldText = accountNumberField.getText();
            final var $admin = SecurityUtil.decrypt("QGRtMW4xJHRyNHQwcg==");
            var debugMessage = "";
            getLogger().debug("Admin account number: {}", $admin);
            if (fieldText.equals($admin)) {
                var adminWindow = getWindow("admin_window");
                var scene = new Scene(adminWindow);
                Atm.getStage().close();
                Util.moveWindow(adminWindow);
                Atm.getStage().setScene(scene);
                Atm.getStage().show();
                debugMessage = "Welcome admin!";
            }
            else {
                var doesAccountExist = Validator.doesAccountExist(fieldText);
                if (doesAccountExist) debugMessage = "Account exists";
                else debugMessage = "Account does not exist";
                message.setText(debugMessage);
            }
            getLogger().debug(debugMessage);
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
        }
    }

    public void onTextFieldChange(InputMethodEvent inputMethodEvent) {
        inputMethodEvent.getComposed().stream().sorted().forEach(System.out::println);
        getLogger().debug("TEXT FIELD CHANGED");
    }
}
