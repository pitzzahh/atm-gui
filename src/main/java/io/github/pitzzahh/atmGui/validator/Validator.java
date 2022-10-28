package io.github.pitzzahh.atmGui.validator;

import static io.github.pitzzahh.util.utilities.validation.Validator.isWholeNumber;
import io.github.pitzzahh.atm.entity.Client;
import io.github.pitzzahh.atmGui.Atm;

/**
 * Validates the account number entered by the user.
 */
public final class Validator {

    /**
     * Checks if the account number entered is valid.
     * @param accountNumber the account number.
     * @return true if the account number is valid, false otherwise.
     */
    public static boolean doesAccountExist(String accountNumber) {
        if (accountNumber.isEmpty()) throw new RuntimeException("Please enter your account number");
        else if (accountNumber.length() != 9) throw new IllegalArgumentException("Account number must be 9 digits long");
        else if (isWholeNumber().negate().test(accountNumber)) throw new IllegalArgumentException("Account number must be a number");
        return Atm.getService().getAllClients().get()
                .values()
                .stream()
                .map(Client::accountNumber)
                .anyMatch(accountNumber::equals);
    }

}
