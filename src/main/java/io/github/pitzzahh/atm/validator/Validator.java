package io.github.pitzzahh.atm.validator;

import static io.github.pitzzahh.util.utilities.validation.Validator.isWholeNumber;
import static io.github.pitzzahh.atm.Atm.getLogger;
import io.github.pitzzahh.atm.entity.Client;
import static java.lang.String.format;
import io.github.pitzzahh.atm.Atm;

public final class Validator {

    public static boolean doesAccountExist(String accountNumber) {
        if (accountNumber.isEmpty()) throw new RuntimeException("Please enter your account number");
        else if (accountNumber.length() != 9) throw new IllegalArgumentException("Account number must be 9 digits long");
        else if (isWholeNumber().negate().test(accountNumber)) throw new IllegalArgumentException("Account number must be a number");
        var doesExist = Atm.getService().getAllClients().get()
                .values()
                .stream()
                .map(Client::accountNumber)
                .anyMatch(accountNumber::equals);
        getLogger().debug(format("Does account exist %s", doesExist));
        return doesExist;
    }

}
