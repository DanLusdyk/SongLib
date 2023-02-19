package songlibfx;

import java.util.Comparator;
import java.util.function.UnaryOperator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;

public class PrimarySceneController {
	
	@FXML
    private ListView<Song> listViewName;
	@FXML
    private ListView<Song> listViewArtist;

	@FXML
	private Label rightPaneLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label artistLabel;
    @FXML
    private Label albumLabel;
    @FXML
    private Label yearLabel;
    
    @FXML
    private ButtonBar songDetailsButtonBar;
    @FXML
    private ButtonBar editButtonBar;
    
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private Button addButton;
    
    @FXML
    private Button cancelEditButton;
    @FXML
    private Button makeEditButton;
    
    @FXML
    private TextField textFieldName;
    @FXML
    private TextField textFieldArtist;
    @FXML
    private TextField textFieldAlbum;
    @FXML
    private TextField textFieldYear;
    
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
        
        // make textFieldYear numerical input only
        UnaryOperator<Change> integerFilter = change -> {
            String input = change.getText();
            if (input.matches("[0-9]*")) { 
                return change;
            }
            return null;
        };
        textFieldYear.setTextFormatter(new TextFormatter<String>(integerFilter));
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
        
        // disable delete and edit button on startup if song list is empty
        if(listViewName.getSelectionModel().isEmpty()) {
        	deleteButton.setDisable(true);
        	editButton.setDisable(true);
        }
        
        // edit button bar invisible on start
        editButtonBar.setVisible(false);
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
        	editButton.setDisable(true);
        }
    }
    
    // called when the user clicks edit button
    @FXML
    private void handleEditSong() {
    	rightPaneLabel.setText("Edit A Song");
    	
    	textFieldName.setVisible(true);
    	textFieldArtist.setVisible(true);
    	textFieldAlbum.setVisible(true);
    	textFieldYear.setVisible(true);
    	
    	songDetailsButtonBar.setVisible(false);
    	editButtonBar.setVisible(true);
    	
    	listViewName.setMouseTransparent(true);
    	listViewArtist.setMouseTransparent(true);
    	
    	int selectedIndex = listViewName.getSelectionModel().getSelectedIndex();
    	Song selectedSong = listViewName.getItems().get(selectedIndex);
    	
    	textFieldName.setText(selectedSong.getName());
    	textFieldArtist.setText(selectedSong.getArtist());
    	textFieldAlbum.setText(selectedSong.getAlbum());
    	textFieldYear.setText(String.valueOf(selectedSong.getYear()));
    }
    
    @FXML
    private void handleCancelEdit() {
    	rightPaneLabel.setText("Song Details");
    	
    	textFieldName.setVisible(false);
    	textFieldArtist.setVisible(false);
    	textFieldAlbum.setVisible(false);
    	textFieldYear.setVisible(false);
    	
    	editButtonBar.setVisible(false);
    	songDetailsButtonBar.setVisible(true);
    	
    	listViewName.setMouseTransparent(false);
    	listViewArtist.setMouseTransparent(false);
    }
    
    @FXML
    private void handleMakeChanges() {
    	if(textFieldName.getText().isBlank()) {
    		Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Name");
            alert.setHeaderText("No Song Name Entered");
            alert.setContentText("A name must be entered for the song.");

            alert.showAndWait();
    	}
    	if(textFieldArtist.getText().isBlank()) {
    		Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("No Artist");
            alert.setHeaderText("No Artist Entered");
            alert.setContentText("An artist must be entered for the song.");

            alert.showAndWait();
    	}
    	
    	if(textFieldName.getText().isBlank() || textFieldArtist.getText().isBlank()) return;
    	
    	boolean alreadyExists = false;
    	int selectedIndex = listViewName.getSelectionModel().getSelectedIndex();
    	for(int i = 0; i < listViewName.getItems().size(); i++) {
    		if(i == selectedIndex) continue;
    		if(textFieldName.getText().equals(listViewName.getItems().get(i).getName()) && textFieldArtist.getText().equals(listViewName.getItems().get(i).getArtist())) {
    			alreadyExists = true;
    			
    			Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(main.getPrimaryStage());
                alert.setTitle("Duplicate Name And Artist");
                alert.setHeaderText("A Song With This Name And Artist Already Exists");
                alert.setContentText("Your edited song must have a unique name and artist.");

                alert.showAndWait();
    		}
    	}
    	
    	if(alreadyExists) {
    		return;
    	}
    	
    	Song selectedSong = listViewName.getItems().get(selectedIndex);
    	listViewName.getItems().get(selectedIndex).setName(textFieldName.getText());
    	listViewName.getItems().get(selectedIndex).setArtist(textFieldArtist.getText());
    	listViewName.getItems().get(selectedIndex).setAlbum(textFieldAlbum.getText());
    	if(textFieldYear.getText().isEmpty()) {
    		yearLabel.setVisible(false);
    		selectedSong.setYear(0);
    	} else {
    		yearLabel.setVisible(true);
    		listViewName.getItems().get(selectedIndex).setYear(Integer.parseInt(textFieldYear.getText()));
    	}
    	
    	rightPaneLabel.setText("Song Details");
    	
    	textFieldName.setVisible(false);
    	textFieldArtist.setVisible(false);
    	textFieldAlbum.setVisible(false);
    	textFieldYear.setVisible(false);
    	
    	editButtonBar.setVisible(false);
    	songDetailsButtonBar.setVisible(true);
    	
    	listViewName.setMouseTransparent(false);
    	listViewArtist.setMouseTransparent(false);
    	
    	listViewName.getItems().sort(Comparator.comparing(Song::getName).thenComparing(Song::getArtist));
    	int newIndex = selectedIndex;
    	for(int i = 0; i < listViewName.getItems().size(); i++) {
    		if(listViewName.getItems().get(i).getName().equals(textFieldName.getText()) && listViewName.getItems().get(i).getArtist().equals(textFieldArtist.getText())) {
    			newIndex = i;
    			break;
    		}
    	}
    	listViewName.getSelectionModel().select(newIndex);
    }
}
