package ec.edu.espol.proyecto2tresenraya;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("pantallajuego.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Tres en Raya");
        stage.setScene(scene);
        stage.setResizable(true); // o false si prefieres
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
