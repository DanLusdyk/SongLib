package songlibfx;

import javafx.fxml.FXML;
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
    
    // Reference to the main application
    private Main main;
    
    public PrimarySceneController( ) {
    	
    }
    
    @FXML
    private void initialize() {
    	// Initialize the person table with the two columns.
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        artistColumn.setCellValueFactory(cellData -> cellData.getValue().artistProperty());
        
        // Clear song details.
        showSongDetails(null);

        // Listen for selection changes and show the song details when changed.
        songTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showSongDetails(newValue));
    }
    
    public void setMain(Main main) {
        this.main = main;

        // Add observable list data to the table
        songTable.setItems(main.getSongData());
    }
    
    private void showSongDetails(Song song) {
        if (song != null) {
            // Fill the labels with info from the person object.
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
}
