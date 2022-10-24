module io.github.pitzzahh.atm {
    requires javafx.controls;
    requires javafx.fxml;
    requires util.classes;
    requires automated.teller.machine.API;
    requires java.sql;

    opens io.github.pitzzahh.atm.controllers to javafx.fxml;
    exports io.github.pitzzahh.atm;
}