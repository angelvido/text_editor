package org.angelvido.text_editor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
  @Override
  public void start(Stage primaryStage) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
    Parent root = loader.load();

    MainController controller = loader.getController();
    controller.setStage(primaryStage);

    primaryStage.setTitle("Nuevo archivo - Editor de Texto en JavaFX");
    primaryStage.setScene(new Scene(root, 800, 600));
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}