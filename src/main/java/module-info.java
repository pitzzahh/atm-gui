module io.github.pitzzahh.atm {
    requires javafx.controls;
    requires javafx.fxml;
    requires util.classes;
    requires automated.teller.machine.API;
    requires java.sql;
    requires ch.qos.logback.core;
    requires org.slf4j;

    opens io.github.pitzzahh.atm.controllers to javafx.fxml;
    exports io.github.pitzzahh.atm;
}