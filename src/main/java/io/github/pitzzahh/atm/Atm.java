package io.github.pitzzahh.atm;

import io.github.pitzzahh.atm.database.DatabaseConnection;
import io.github.pitzzahh.atm.entity.Client;
import io.github.pitzzahh.atm.service.AtmService;
import io.github.pitzzahh.atm.dao.InDatabase;
import io.github.pitzzahh.util.utilities.Print;
import io.github.pitzzahh.util.utilities.classes.Person;
import io.github.pitzzahh.util.utilities.classes.enums.Gender;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Atm extends Application {

    private static AtmService service;

    @Override
    public void start(Stage stage) throws IOException {
        var fxmlLoader = new FXMLLoader(Atm.class.getResource("hello-view.fxml"));
        var scene = new Scene(fxmlLoader.load());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        service = new AtmService(new InDatabase());
        service.setDataSource()
                        .accept(
                                new DatabaseConnection()
                                        .setDriverClassName("org.postgresql.Driver")
                                        .setUrl("jdbc:postgresql://localhost:5432/postgres")
                                        .setUsername("postgres")
                                        .setPassword("!P4ssW0rd@123")
                                        .getDataSource()
                        );

        service.getAllClients()
                .get()
                .values()
                .forEach(Print::println);
        launch();
    }

    public static AtmService getService() {
        return service;
    }
}