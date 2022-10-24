package io.github.pitzzahh.atm;

import io.github.pitzzahh.util.utilities.classes.enums.Gender;
import io.github.pitzzahh.util.utilities.classes.Person;
import io.github.pitzzahh.atm.service.AtmService;
import io.github.pitzzahh.atm.entity.Client;
import io.github.pitzzahh.atm.dao.InMemory;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.time.LocalDate;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;
import java.time.Month;

public class Atm extends Application {

    private static AtmService service;

    @Override
    public void start(Stage stage) throws IOException {
        var fxmlLoader = new FXMLLoader(Atm.class.getResource("login_page.fxml"));
        var scene = new Scene(fxmlLoader.load());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(Objects.requireNonNull(Atm.class.getResourceAsStream("img/logo.png"), "logo not found")));
        stage.setScene(scene);
        stage.show();
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
}