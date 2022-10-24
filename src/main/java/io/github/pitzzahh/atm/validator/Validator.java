package io.github.pitzzahh.atm.validator;

import io.github.pitzzahh.atm.Atm;
import io.github.pitzzahh.atm.entity.Client;
import static io.github.pitzzahh.util.utilities.validation.Validator.isWholeNumber;

public final class Validator {

    public static boolean checkAccountNumber(String accountNumber) {
        if (accountNumber.length() != 9) throw new IllegalArgumentException("Account number must be 9 digits long");
        if (isWholeNumber().negate().test(accountNumber)) throw new IllegalArgumentException("Account number must be a number");
        return Atm.getService().getAllClients().get()
                .values()
                .stream()
                .map(Client::accountNumber)
                .anyMatch(accountNumber::equals);
    }

}
