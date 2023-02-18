package songlibfx;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class PrimarySceneController {
	
	@FXML
    private TableView<Song> songTable;
    @FXML
    private TableColumn<Song, String> nameColumn;
    @FXML
    private TableColumn<Song, String> artistColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label artistLabel;
    @FXML
    private Label albumLabel;
    @FXML
    private Label yearLabel;
    
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private Button addButton;
    
    // Reference to the main application
    private Main main;
    
    public PrimarySceneController( ) {
    	
    }
    
    @FXML
    private void initialize() {
    	// Initialize the person table with the two columns
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        artistColumn.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
        
        // Clear song details
        showSongDetails(null);

        // Listen for selection changes and show the song details when changed
        songTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showSongDetails(newValue));
    }
    
    // called by main to give a reference back to itself
    public void setMain(Main main) {
        this.main = main;

        // Add observable list data to the table
        songTable.setItems(main.getSongData());
        
        // alphabetically sort pre-existing data on startup
        songTable.getItems().sort(Comparator.comparing(Song::getName).thenComparing(Song::getArtist));
        
        // preselect first item in songTable on startup
        songTable.getSelectionModel().selectFirst();
    }
    
    private void showSongDetails(Song song) {
        if (song != null) {
            // Fill the labels with info from the person object
            nameLabel.setText(song.getName());
            artistLabel.setText(song.getArtist());
            albumLabel.setText(song.getAlbum());
            yearLabel.setText(Integer.toString(song.getYear()));

        } else {
            // Song is null, remove all the text
        	nameLabel.setText("");
            artistLabel.setText("");
            albumLabel.setText("");
            yearLabel.setText("");
        }
    }
    
    // called when the user clicks delete button
    @FXML
    private void handleDeleteSong() {
    	int selectedIndex = songTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
        	songTable.getItems().remove(selectedIndex);
        	int newSelectedIndex = Math.min(selectedIndex, songTable.getItems().size() - 1);
            songTable.getSelectionModel().select(newSelectedIndex);
        }
        if(songTable.getSelectionModel().isEmpty()) {
        	deleteButton.setDisable(true);
        }
    }
}
