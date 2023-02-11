package songlibfx;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	private ObservableList<Song> songData = FXCollections.observableArrayList();
	
	public Main() {
		// Add some sample data
		songData.add(new Song("test", "artist"));
		songData.add(new Song("test", "artist"));
		songData.add(new Song("test", "artist"));
		songData.add(new Song("test", "artist"));
		songData.add(new Song("test", "artist"));
	}
	
	public ObservableList<Song> getSongData() {
		return songData;
	}

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("PrimaryScene.fxml"));
        primaryStage.setTitle("Song Library");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
