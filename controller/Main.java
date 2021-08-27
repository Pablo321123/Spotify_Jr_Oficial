package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage window) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/mainScreen.fxml"));
        Scene scene = new Scene(root);
        scene.setFill(Color.TRANSPARENT);
        window.setTitle("Spotify Jr");
        //window.initStyle(StageStyle.TRANSPARENT);        
        window.setScene(scene);
        window.show();

        // FXMLLoader loader = new FXMLLoader(getClass().getResource("InterfacePrincipal.fxml"));
        // Scene scene = new Scene(loader.load());
        // scene.setFill(Color.TRANSPARENT);
        // window.setScene(scene);
        // window.initStyle(StageStyle.TRANSPARENT);
        // window.setResizable(false);
        // window.setTitle("Calculadora - Lucas e Pedro");
        // primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/imagens/icone.png")));
        // ((JanelaPrincipalController)loader.getController()).init(primaryStage);
        // primaryStage.show();

    }

    public static void main(String[] args) {
        launch();

    }

}