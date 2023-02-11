package songlibfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Controller {

    @FXML
    private Button addButton;

    @FXML
    private TextField album;

    @FXML
    private TextField artist;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private TextField name;

    @FXML
    private ListView<String> songList;

    @FXML
    private TextField songYear;

    @FXML
    void addSong(MouseEvent event) {
        songList.getItems().add(name.getText());
    }

}
