package io.github.pitzzahh.atm;

import io.github.pitzzahh.atm.util.Util;
import io.github.pitzzahh.util.utilities.classes.enums.Gender;
import io.github.pitzzahh.util.utilities.classes.Person;
import io.github.pitzzahh.atm.service.AtmService;
import static io.github.pitzzahh.atm.util.Util.getWindow;
import static java.util.Objects.requireNonNull;
import io.github.pitzzahh.atm.entity.Client;
import io.github.pitzzahh.atm.dao.InMemory;
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

public class Atm extends Application {

    private static AtmService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(Atm.class);

    private static Stage stage;
    @Override
    public void start(Stage stage) throws IOException {
        initParents();
        var parent = getWindow("main_window");
        var scene = new Scene(parent);
        Atm.stage = stage;
        getStage().initStyle(StageStyle.UNDECORATED);
        getStage().getIcons().add(new Image(requireNonNull(Atm.class.getResourceAsStream("img/loginPage/logo.png"), "logo not found")));
        Util.moveWindow(parent);
        getStage().setScene(scene);
        getStage().show();
        LOGGER.info("Application started");
    }

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
    private void initParents() throws IOException {
        var mainPage = (Parent) FXMLLoader.load(requireNonNull(Atm.class.getResource("mainPage.fxml")));
        var adminPage = (Parent) FXMLLoader.load(requireNonNull(Atm.class.getResource("adminPage.fxml")));
        adminPage.setId("admin_window");
        mainPage.setId("main_window");
        Util.addParents(mainPage, adminPage);
    }
    public static AtmService getService() {
        return service;
    }
    public static Logger getLogger() {
        return LOGGER;
    }

    public static Stage getStage() {
        return stage;
    }
}