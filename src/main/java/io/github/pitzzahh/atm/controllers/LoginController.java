package io.github.pitzzahh.atm.controllers;

import static io.github.pitzzahh.atm.Atm.getLogger;
import io.github.pitzzahh.atm.validator.Validator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Label;
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


    public void onEnter(KeyEvent keyEvent) {
        accountNumberField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                getLogger().info("Enter key pressed");
                check();
            }
        });
    }

    private void check() {
        try {
            var doesAccountExist = Validator.doesAccountExist(accountNumberField.getText());
            var exist = "";
            if (doesAccountExist) exist = "Account number exists";
            else exist = "Account number does not exist";
            message.setText(exist);
            getLogger().debug(exist);
        } catch (RuntimeException runtimeException) {
            message.setText(runtimeException.getMessage());
            getLogger().error(runtimeException.getMessage());
        }
    }
}
