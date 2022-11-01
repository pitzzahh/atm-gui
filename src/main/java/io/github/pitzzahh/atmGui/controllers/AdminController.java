package io.github.pitzzahh.atmGui.controllers;

import static io.github.pitzzahh.atmGui.Atm.getLogger;
import static io.github.pitzzahh.atmGui.Atm.getStage;
import static io.github.pitzzahh.atmGui.util.Util.*;
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

    @FXML
    private Button manageLockedAccounts;

    @FXML
    private Button manageAccountLoans;

    @FXML
    private Button logout;


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

    public void onMouseEnteredManageLockedAccounts(MouseEvent mouseEvent) {
        var tooltip = initToolTip(
                "Manage Locked Accounts",
                mouseEvent,
                adminButtonFunctionsToolTip()
        );
        tooltip.setShowDuration(Duration.seconds(3));
        manageLockedAccounts.setTooltip(tooltip);
    }

    public void onMouseEnteredManageAccountLoans(MouseEvent mouseEvent) {
        var tooltip = initToolTip(
                "Manage Account Loans",
                mouseEvent,
                adminButtonFunctionsToolTip()
        );
        tooltip.setShowDuration(Duration.seconds(3));
        manageAccountLoans.setTooltip(tooltip);
    }

    public void onMouseEnteredLogout(MouseEvent mouseEvent) {
        var tooltip = initToolTip(
                "Logout Session",
                mouseEvent,
                adminButtonFunctionsToolTip()
        );
        tooltip.setShowDuration(Duration.seconds(3));
        logout.setTooltip(tooltip);
    }

    public void onLogout(MouseEvent mouseEvent) {
        getLogger().info("Logging out...");
        getStage().close();
        var mainWindow = getWindow("main_window");
        getLogger().debug("Loading main window");
        getStage().setTitle("ATM");
        getStage().setResizable(false);
        getStage().centerOnScreen();
        getStage().setScene(mainWindow.getScene());
        getStage().show();
    }
}
