package io.github.pitzzahh.atm;

import io.github.pitzzahh.util.utilities.classes.enums.Gender;
import io.github.pitzzahh.util.utilities.classes.Person;
import io.github.pitzzahh.atm.service.AtmService;
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
import java.util.Objects;
import org.slf4j.Logger;
import java.time.Month;

public class Atm extends Application {

    private static AtmService service;
    private static final Logger LOGGER = LoggerFactory.getLogger(Atm.class);

    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Atm.class.getResource("login_page.fxml")));
        var scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(Objects.requireNonNull(Atm.class.getResourceAsStream("img/logo.png"), "logo not found")));
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.setScene(scene);
        stage.show();
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

    public static AtmService getService() {
        return service;
    }
    public static Logger getLogger() {
        return LOGGER;
    }
}