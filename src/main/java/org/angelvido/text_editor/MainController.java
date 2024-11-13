package org.angelvido.text_editor;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainController {

  @FXML
  private TextArea textArea;

  private Stage stage;

  private File currentFile = null;

  private boolean isModified = false;

  public void setStage(Stage stage) {
    this.stage = stage;
    updateWindowTitle();

    textArea.textProperty().addListener((observable, oldValue, newValue) -> {
      if (!isModified) {
        isModified = true;
        updateWindowTitle();
      }
    });
  }

  @FXML
  private void handleNewFile() {
    textArea.clear();
    currentFile = null;
    isModified = false;
    updateWindowTitle();
  }

  @FXML
  private void handleNewWindow() {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("MainView.fxml"));
      Parent root = loader.load();

      Stage newStage = new Stage();
      newStage.setTitle("Nuevo archivo - Editor de Texto en JavaFX");
      newStage.setScene(new Scene(root, 800, 600));

      MainController newController = loader.getController();
      newController.setStage(newStage);

      newStage.initModality(Modality.NONE);
      newStage.show();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  private void handleOpenFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Abrir Archivo");
    File file = fileChooser.showOpenDialog(stage);
    if (file != null) {
      try {
        String content = new String(Files.readAllBytes(file.toPath()));
        textArea.setText(content);
        currentFile = file;
        isModified = false;
        updateWindowTitle();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @FXML
  private void handleSaveFile() {
    if (currentFile != null) {
      saveToFile(currentFile);
    } else {
      handleSaveFileAs();
    }
  }

  @FXML
  private void handleSaveFileAs() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Guardar archivo como");
    File file = fileChooser.showSaveDialog(stage);
    if (file != null) {
      saveToFile(file);
      currentFile = file;
      updateWindowTitle();
    }
  }

  @FXML
  private void handleExit() {
    stage.close();
  }

  // Auxiliar methods

  private void saveToFile(File file) {
    try {
      Files.write(Paths.get(file.getPath()), textArea.getText().getBytes());
      isModified = false;
      updateWindowTitle();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void updateWindowTitle() {
    String title = (currentFile != null) ? currentFile.getName() : "Nuevo archivo";

    if (isModified) {
      title = "(*) " + title;
    }

    stage.setTitle(title + " - Editor de Texto en JavaFX");
  }
}
