package io.github.pitzzahh.atm.controllers;

import io.github.pitzzahh.atm.validator.Validator;
import javafx.scene.input.InputMethodEvent;
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
    public void onEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode().getName().equals("Enter")) {
            if (Validator.checkAccountNumber(accountNumberField.getText())) {
                message.setText("Account number is valid");
            } else {
                message.setText("Account number is invalid");
            }
        }
        //Util.remove(message, Duration.seconds(3));
    }

    @FXML
    public void onInput(InputMethodEvent inputMethodEvent) {
        var exist = Validator.checkAccountNumber(accountNumberField.getText());
        if (exist) {
            message.setText("Account number exists");
        } else {
            message.setText("Account number does not exist");
        }
        //Util.remove(message, Duration.seconds(3));
    }
}
