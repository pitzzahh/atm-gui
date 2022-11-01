package io.github.pitzzahh.atmGui;

import io.github.pitzzahh.util.utilities.classes.enums.Gender;
import static io.github.pitzzahh.atmGui.util.Util.moveWindow;
import static io.github.pitzzahh.atmGui.util.Util.getWindow;
import io.github.pitzzahh.util.utilities.classes.Person;
import io.github.pitzzahh.atm.service.AtmService;
import static java.util.Objects.requireNonNull;
import io.github.pitzzahh.atm.entity.Client;
import io.github.pitzzahh.atm.dao.InMemory;
import io.github.pitzzahh.atmGui.util.Util;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;
import org.slf4j.LoggerFactory;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.time.LocalDate;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import java.time.Month;
import java.util.Arrays;

/**
 * The main class of the application.
 */
public class Atm extends Application {

    private static AtmService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(Atm.class);
    private static Stage stage;

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * <p>
     * NOTE: This method is called on the JavaFX Application Thread.
     * </p>
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages.
     * @throws Exception if something goes wrong
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        initParents();
        var parent = getWindow("main_window");
        var scene = new Scene(parent);
        Arrays.stream(primaryStage.getClass()
                .getDeclaredConstructors())
                .filter(c -> c.getParameterTypes().length == 0)
                .findAny()
                .ifPresent(c -> {c.setAccessible(true);
                    try {
                        Atm.stage = (Stage) c.newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        getStage().setResizable(false);
        getStage().initStyle(StageStyle.UNIFIED);
        getStage().getIcons().add(new Image(requireNonNull(Atm.class.getResourceAsStream("img/mainPage/logo.png"), "logo not found")));
        moveWindow(parent);
        getStage().setScene(scene);
        getStage().centerOnScreen();
        getStage().setTitle("ATM");
        getStage().show();
        LOGGER.info("Application started");
    }

    /**
     * main method
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        service = new AtmService(new InMemory());
        service.saveClient().apply(
                new Client(
                        "123123123",
                        "123123",
                        new Person(
                                "Mark",
                                "Silent",
                                Gender.PREFER_NOT_TO_SAY,
                                "Earth",
                                LocalDate.of(2018, Month.AUGUST, 10)
                        ),
                        5555,
                        false
                )
        );
        launch();
    }

    /**
     * Initializes the parents.
     * The window is loaded from the FXML file.
     * @throws IOException if the parent cannot be loaded.
     */
    private void initParents() throws IOException {
        var mainPage = (Parent) FXMLLoader.load(requireNonNull(Atm.class.getResource("fxml/mainPage.fxml")));
        var adminPage = (Parent) FXMLLoader.load(requireNonNull(Atm.class.getResource("fxml/admin/adminPage.fxml")));
        var clientPage = (Parent) FXMLLoader.load(requireNonNull(Atm.class.getResource("fxml/client/clientPage.fxml")));
        var addClientsPage = (Parent) FXMLLoader.load(requireNonNull(Atm.class.getResource("fxml/admin/addClientsPage.fxml")));
        adminPage.setId("admin_window");
        mainPage.setId("main_window");
        clientPage.setId("client_window");
        addClientsPage.setId("add_clients_window");
        Util.addParents(mainPage, adminPage, clientPage, addClientsPage);
    }

    /**
     * Gets the service for atm.
     * @return the service.
     */
    public static AtmService getService() {
        return service;
    }

    /**
     * Gets the logger for atm.
     * @return the logger.
     */
    public static Logger getLogger() {
        return LOGGER;
    }

    /**
     * Gets the stage for atm.
     * @return the stage.
     */
    public static Stage getStage() {
        return stage;
    }
}