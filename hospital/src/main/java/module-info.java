module com.hospital.practicantes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.hospital.practicantes to javafx.fxml;
    opens com.hospital.practicantes.controllers to javafx.fxml;
    opens com.hospital.practicantes.entities to javafx.base;


    exports com.hospital.practicantes;
    exports com.hospital.practicantes.entities;
    exports com.hospital.practicantes.daos;
    exports com.hospital.practicantes.interfaces;
    exports com.hospital.practicantes.persistence;
    exports com.hospital.practicantes.facade;
}
