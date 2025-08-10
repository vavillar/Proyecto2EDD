module ec.edu.espol.proyecto2tresenraya {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    // Necesarios para que FXML pueda acceder a las clases con @FXML
    opens ec.edu.espol.proyecto2tresenraya to javafx.fxml;
    opens ec.edu.espol.proyecto2tresenraya.controllers to javafx.fxml;
    opens ec.edu.espol.proyecto2tresenraya.imagenes_recursos to javafx.fxml;

    // Para que otros módulos puedan usar estas clases (si se exporta la librería)
    exports ec.edu.espol.proyecto2tresenraya;
    exports ec.edu.espol.proyecto2tresenraya.controllers;
    exports ec.edu.espol.proyecto2tresenraya.ia;
    exports ec.edu.espol.proyecto2tresenraya.modelo;
    exports ec.edu.espol.proyecto2tresenraya.EDD;
}
