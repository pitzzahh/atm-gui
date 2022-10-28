module io.github.pitzzahh.atm.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires util.classes;
    requires automated.teller.machine.API;
    requires ch.qos.logback.core;
    requires org.slf4j;

    opens io.github.pitzzahh.atmGui.controllers to javafx.fxml;
    exports io.github.pitzzahh.atmGui;
}