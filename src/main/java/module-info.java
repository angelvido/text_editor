module org.angelvido.text_editor {
  requires javafx.controls;
  requires javafx.fxml;


  opens org.angelvido.text_editor to javafx.fxml;
  exports org.angelvido.text_editor;
}