package songlibfx;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	private Stage primaryStage;
	private AnchorPane rootLayout;
	
	private ObservableList<Song> songData = FXCollections.observableArrayList();
	
	public Main() {
		// Add some sample data
		songData.add(new Song("test", "artist"));
		songData.add(new Song("test2", "artist2"));
		songData.add(new Song("test", "artist"));
		songData.add(new Song("test", "artist"));
		songData.add(new Song("test", "artist"));
	}
	
	public ObservableList<Song> getSongData() {
		return songData;
	}

    @Override
    public void start(Stage primaryStage) throws Exception {
    	this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SongLib");

        showPrimaryScene();
    }
    
    public void showPrimaryScene() {
    	try {
            // Load root layout from fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("PrimaryScene.fxml"));
            rootLayout = (AnchorPane) loader.load();
            
            // Give the controller access to the main app.
            PrimarySceneController controller = loader.getController();
            controller.setMain(this);
            
            // Show the scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
