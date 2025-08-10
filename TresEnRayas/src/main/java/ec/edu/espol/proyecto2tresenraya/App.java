package ec.edu.espol.proyecto2tresenraya;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        mostrarSeleccionSimbolo();
    }

    public static void mostrarSeleccionSimbolo() throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("seleccionsimbolojugador.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Tres en Raya - Selección de Símbolo");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void mostrarPantallaJuego() throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("pantallajuego.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Tres en Raya - Juego");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
