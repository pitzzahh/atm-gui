package io.github.pitzzahh.atm.controllers;

import io.github.pitzzahh.atm.validator.Validator;
import javafx.scene.control.TextField;
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

    @FXML
    // TODO: fix bug not called when enter is pressed
    public void onEnter(KeyEvent keyEvent) {
        var isEnter = keyEvent.getCode().getName().equals("Enter");
        if (isEnter) {
            try {
                var exist = Validator.doesAccountExist(accountNumberField.getText());
                if (exist) message.setText("Account number exists");
                else message.setText("Account number does not exist");
            } catch (RuntimeException runtimeException) {
                message.setText(runtimeException.getMessage());
            }
        }
    }
}
