package io.github.pitzzahh.atm;

import io.github.pitzzahh.util.utilities.classes.enums.Gender;
import io.github.pitzzahh.util.utilities.classes.Person;
import io.github.pitzzahh.atm.service.AtmService;
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

    private double horizontal = 0;
    private double vertical = 0;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(requireNonNull(Atm.class.getResource("login_page.fxml")));
        var scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(requireNonNull(Atm.class.getResourceAsStream("img/logo.png"), "logo not found")));
        root.setOnMousePressed(event -> {
            horizontal = event.getSceneX();
            vertical = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - horizontal);
            stage.setY(event.getScreenY() - vertical);
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