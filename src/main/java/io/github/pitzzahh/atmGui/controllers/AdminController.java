package io.github.pitzzahh.atmGui.controllers;

import static io.github.pitzzahh.atmGui.Atm.getLogger;
import static io.github.pitzzahh.atmGui.Atm.getStage;
import static io.github.pitzzahh.atmGui.util.Util.*;
import org.controlsfx.control.textfield.TextFields;
import io.github.pitzzahh.atmGui.entity.Client;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
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
    public TableView<Client> addClientsTable;

    @FXML
    public Button addClientButton;

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

    private boolean isOk;

    /**
     * Shows a tooltip when the mouse is hovered over the add clients button.
     * @param mouseEvent the mouse event.
     */
    @FXML
    public void onMouseEnteredAddClientsButton(MouseEvent mouseEvent) {
        var tooltip = initToolTip(
                "Add Clients",
                mouseEvent,
                adminButtonFunctionsToolTipStyle()
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
                adminButtonFunctionsToolTipStyle()
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
                adminButtonFunctionsToolTipStyle()
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
                adminButtonFunctionsToolTipStyle()
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
                adminButtonFunctionsToolTipStyle()
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
                adminButtonFunctionsToolTipStyle()
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
        addActiveButtons(addClients);
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

        var mainWindow = getParent("main_window");
        getMessageLabel(mainWindow).ifPresent(label -> label.setText(""));
        getMainProgressBar(mainWindow).ifPresent(pb -> pb.setVisible(false));
        getStage().setFullScreen(false);
        stage.setTitle("ATM");
        stage.setFullScreen(false);
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.setScene(mainWindow.getScene());
        getLogger().debug("Loading main window");
        stage.show();
    }

    /**
     * Used to suggest address locations when typing an address.
     * @param keyEvent the key event.
     */
    @FXML
    public void oneKeyTyped(KeyEvent keyEvent) {
        var textField = (TextField) keyEvent.getSource();
        var binding = TextFields.bindAutoCompletion(textField, getLocations.get());
        binding.setPrefWidth(textField.getWidth());
        binding.setVisibleRowCount(3);
        binding.setHideOnEscape(true);
    }

    @FXML
    public void onAddClient(MouseEvent mouseEvent) {
        mouseEvent.consume();
        getLogger().debug(String.format("IS OK %s", isOk));
        if (isOk) {
            final var FIRST_NAME = firstName.getText().trim();
            final var LAST_NAME = lastName.getText().trim();
            final var ADDRESS = address.getText().trim();
            fillTable(
                    new Client(
                            FIRST_NAME.concat(" ").concat(LAST_NAME),
                            ADDRESS,
                            dateOfBirth.getValue(),
                            generateRandomAccountNumber(),
                            generateRandomPin()
                    ),
                    addClientsTable
            );
        }
    }

    @FXML
    public void onAddClientAttempt(MouseEvent mouseEvent) {
        final var FIRST_NAME = firstName.getText().trim();
        final var LAST_NAME = lastName.getText().trim();
        final var ADDRESS = address.getText().trim();
        isOk = checkInputs(
                addClientButton,
                mouseEvent,
                FIRST_NAME,
                LAST_NAME,
                ADDRESS,
                dateOfBirth
        );
        if (isOk && addClientButton.getTooltip() != null) addClientButton.setTooltip(null);
    }

    @FXML
    public void onClear(ActionEvent event) {
        event.consume();
        firstName.clear();
        lastName.clear();
        address.clear();
        dateOfBirth.setValue(null);
        addClientButton.setDisable(false);
    }
}
