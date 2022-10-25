package io.github.pitzzahh.atm.controllers;

import static io.github.pitzzahh.atm.Atm.getLogger;
import io.github.pitzzahh.atm.validator.Validator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
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
    public void onEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            getLogger().info("Enter key pressed");
            check();
        }
    }

    /**
     * Checks if the account number entered exists in the database as an account.
     */
    private void check() {
        try {
            var doesAccountExist = Validator.doesAccountExist(accountNumberField.getText());
            var exist = "";
            if (doesAccountExist) exist = "Account exists";
            else exist = "Account does not exist";
            message.setText(exist);
            getLogger().debug(exist);
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

}
