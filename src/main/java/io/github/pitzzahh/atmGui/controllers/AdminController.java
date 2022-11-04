package io.github.pitzzahh.atmGui.controllers;

import static io.github.pitzzahh.atmGui.Atm.getLogger;
import static io.github.pitzzahh.atmGui.util.Util.*;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.fxml.FXML;

/**
 * FXML Controller class for Admin page
 */
public class AdminController {

    @FXML
    public TextField firstName;

    @FXML
    public TextField lastName;

    @FXML
    public TextField address;

    @FXML
    public DatePicker dateOfBirth;

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

    /**
     * Shows a tooltip when the mouse is hovered over the add clients button.
     * @param mouseEvent the mouse event.
     */
    @FXML
    public void onMouseEnteredAddClientsButton(MouseEvent mouseEvent) {
        var tooltip = initToolTip(
                "Add Clients",
                mouseEvent,
                adminButtonFunctionsToolTip()
                );
        tooltip.setShowDuration(Duration.seconds(3));
        addClients.setTooltip(tooltip);
    }

    /**
     * Shows a tooltip when the mouse is hovered over the remove clients button.
     * @param mouseEvent the mouse event.
     */
    @FXML
    public void onMouseEnteredRemoveClientsButton(MouseEvent mouseEvent) {
        var tooltip = initToolTip(
                "Remove Clients",
                mouseEvent,
                adminButtonFunctionsToolTip()
        );
        tooltip.setShowDuration(Duration.seconds(3));
        removeClients.setTooltip(tooltip);
    }

    /**
     * Shows a tooltip when the mouse is hovered over the view clients button.
     * @param mouseEvent the mouse event.
     */
    @FXML
    public void onMouseEnteredViewClientsButton(MouseEvent mouseEvent) {
        var tooltip = initToolTip(
                "View the list of Clients information",
                mouseEvent,
                adminButtonFunctionsToolTip()
        );
        tooltip.setShowDuration(Duration.seconds(3));
        viewClients.setTooltip(tooltip);
    }

    /**
     * Shows a tooltip when the mouse is hovered over the manage account loans button.
     * @param mouseEvent the mouse event.
     */
    @FXML
    public void onMouseEnteredManageLockedAccounts(MouseEvent mouseEvent) {
        var tooltip = initToolTip(
                "Manage Locked Accounts",
                mouseEvent,
                adminButtonFunctionsToolTip()
        );
        tooltip.setShowDuration(Duration.seconds(3));
        manageLockedAccounts.setTooltip(tooltip);
    }

    /**
     * Shows a tooltip when the mouse is hovered over the manage account loans button.
     * @param mouseEvent the mouse event.
     */
    @FXML
    public void onMouseEnteredManageAccountLoans(MouseEvent mouseEvent) {
        var tooltip = initToolTip(
                "Manage Account Loans",
                mouseEvent,
                adminButtonFunctionsToolTip()
        );
        tooltip.setShowDuration(Duration.seconds(3));
        manageAccountLoans.setTooltip(tooltip);
    }

    /**
     * Shows a tooltip when mouse is hovered over the logout button.
     * @param mouseEvent the mouse event.
     */
    @FXML
    public void onMouseEnteredLogout(MouseEvent mouseEvent) {
        var tooltip = initToolTip(
                "Logout Session",
                mouseEvent,
                adminButtonFunctionsToolTip()
        );
        tooltip.setShowDuration(Duration.seconds(3));
        logout.setTooltip(tooltip);
    }

    /**
     * Shows the add clients window.
     * @param actionEvent the action event.
     */
    @FXML
    public void onAddClients(ActionEvent actionEvent) {
        setCenterScreenOfBorderPane(actionEvent, "add_clients_window");
    }

    // TODO: make the scene with same id
    public void onRemoveClients(ActionEvent actionEvent) {
        setCenterScreenOfBorderPane(actionEvent, "remove_clients_window");
    }

    // TODO: make the scene with same id
    public void onViewClients(ActionEvent actionEvent) {
        setCenterScreenOfBorderPane(actionEvent, "view_clients_window");
    }

    // TODO: make the scene with same id
    public void onManageLockedAccounts(ActionEvent actionEvent) {

    }

    // TODO: make the scene with same id
    public void onManageAccountLoans(ActionEvent actionEvent) {

    }

    /**
     * Logs out the current session.
     * Returns to the main page.
     * @param actionEvent the action event.
     */
    @FXML
    public void onLogout(ActionEvent actionEvent) {
        getLogger().info("Logging out...");
        var parent = (AnchorPane) ((((Button) actionEvent.getSource()).getParent().getParent())).getParent();
        var stage = (Stage) parent.getScene().getWindow();
        stage.close();

        var mainWindow = getWindow("main_window");
        stage.setTitle("ATM");
        stage.setFullScreen(false);
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setScene(mainWindow.getScene());
        getLogger().debug("Loading main window");
        stage.show();
    }
}
