package io.github.pitzzahh.atm.controllers;

import static io.github.pitzzahh.atm.util.Util.adminButtonFunctionsToolTip;
import static io.github.pitzzahh.atm.util.Util.initToolTip;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.util.Duration;
import javafx.fxml.FXML;

public class AdminController {

    @FXML
    private Button addClients;

    @FXML
    private Button removeClients;

    @FXML
    private Button viewClients;

    public void onMouseEnteredAddClientsButton(MouseEvent mouseEvent) {
        var tooltip = initToolTip(
                "Add Clients",
                mouseEvent,
                adminButtonFunctionsToolTip()
                );
        tooltip.setShowDuration(Duration.seconds(3));
        addClients.setTooltip(tooltip);
    }

    public void onMouseEnteredRemoveClientsButton(MouseEvent mouseEvent) {
        var tooltip = initToolTip(
                "Remove Clients",
                mouseEvent,
                adminButtonFunctionsToolTip()
        );
        tooltip.setShowDuration(Duration.seconds(3));
        removeClients.setTooltip(tooltip);
    }

    public void onMouseEnteredViewClientsButton(MouseEvent mouseEvent) {
        var tooltip = initToolTip(
                "View the list of Clients information",
                mouseEvent,
                adminButtonFunctionsToolTip()
        );
        tooltip.setShowDuration(Duration.seconds(3));
        viewClients.setTooltip(tooltip);
    }
}
