module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;
    requires com.google.gson;
    requires okhttp3;
    requires ormlite.jdbc;

    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb;

    exports at.ac.fhcampuswien.fhmdb.database;

    opens at.ac.fhcampuswien.fhmdb.models to com.google.gson;
    opens at.ac.fhcampuswien.fhmdb.database to com.google.gson, ormlite.jdbc;
    //opens at.ac.fhcampuswien.fhmdb.database to com.google.gson;


}
