package songlibfx;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.util.Callback;

public class PrimarySceneController {
	
	@FXML
    private ListView<Song> listViewName;
	@FXML
    private ListView<Song> listViewArtist;

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
    
    public PrimarySceneController() {
    	
    }
    
    @FXML
    private void initialize() {
    	// Define the callback to create custom ListCell objects
    	Callback<ListView<Song>, ListCell<Song>> cellFactoryName = new Callback<ListView<Song>, ListCell<Song>>() {
    	    @Override
    	    public ListCell<Song> call(ListView<Song> listView) {
    	        return new ListCell<Song>() {
    	            @Override
    	            protected void updateItem(Song song, boolean empty) {
    	                super.updateItem(song, empty);
    	                if (song == null || empty) {
    	                    setText(null);
    	                } else {
    	                    setText(song.getName());
    	                }
    	            }
    	        };
    	    }
    	};
    	
    	Callback<ListView<Song>, ListCell<Song>> cellFactoryArtist = new Callback<ListView<Song>, ListCell<Song>>() {
    	    @Override
    	    public ListCell<Song> call(ListView<Song> listView) {
    	        return new ListCell<Song>() {
    	            @Override
    	            protected void updateItem(Song song, boolean empty) {
    	                super.updateItem(song, empty);
    	                if (song == null || empty) {
    	                    setText(null);
    	                } else {
    	                    setText(song.getArtist());
    	                }
    	            }
    	        };
    	    }
    	};
    	
        listViewName.setCellFactory(cellFactoryName);
        listViewArtist.setCellFactory(cellFactoryArtist);
        
        // Clear song details
        showSongDetails(null);
        
        // Add a listener to the selected index property of listViewName
        listViewName.getSelectionModel().selectedIndexProperty().addListener((observable, oldIndex, newIndex) -> {
        	// Set the selected index of listViewArtist to the same index as listViewName
        	listViewArtist.getSelectionModel().select(newIndex.intValue());
        });
        
        // Add a listener to the selected index property of listViewArtist
        listViewArtist.getSelectionModel().selectedIndexProperty().addListener((observable, oldIndex, newIndex) -> {
        	// Set the selected index of listViewArtist to the same index as listViewName
        	listViewName.getSelectionModel().select(newIndex.intValue());
        });

        // Listen for selection changes and show the song details when changed
        listViewName.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showSongDetails(newValue));
        listViewArtist.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showSongDetails(newValue));
    }
    
    // called by main to give a reference back to itself
    public void setMain(Main main) {
        this.main = main;

        // Add observable list data to the list views
        listViewName.setItems(main.getSongData());
        listViewArtist.setItems(main.getSongData());
        
        // alphabetically sort pre-existing data on startup
        listViewName.getItems().sort(Comparator.comparing(Song::getName).thenComparing(Song::getArtist));
        
        // preselect first item on startup
        listViewName.getSelectionModel().selectFirst();
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
    	int selectedIndex = listViewName.getSelectionModel().getSelectedIndex();
    	if(selectedIndex >= 0) {
    		listViewName.getItems().remove(selectedIndex);
    		int newSelectedIndex = Math.min(selectedIndex, listViewName.getItems().size() - 1);
    		listViewName.getSelectionModel().select(newSelectedIndex);
    	}
    	if(listViewName.getSelectionModel().isEmpty()) {
        	deleteButton.setDisable(true);
        }
    }
}
