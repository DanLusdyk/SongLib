// Daniel Lusdyk, Ian Wilkinson

package songlibfx;

import java.io.IOException;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.w3c.dom.Element;

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
	private XMLHandler xmlHandler = new XMLHandler();
	private ObservableList<Song> songData = FXCollections.observableArrayList();
	
	public Main() {
		
		//Start xmlHandler
		xmlHandler.startHandler();
		songData = xmlHandler.readSong();
		
		//Initialize SongData to Data recorded in Xml file

//		songData =  xmlHandler.readSong();
//		songData.add(new Song("abc", "zrtistssssssssssssssssss"));
//		songData.add(new Song("abc", "crtist2"));
//		songData.add(new Song("abc", "artist"));
//		songData.add(new Song("testzb", "zartist"));
//		songData.add(new Song("testza", "artist"));
		
	}
	
	public ObservableList<Song> getSongData() {
		return songData;
	}

    @Override
    public void start(Stage primaryStage) throws Exception {
    	this.primaryStage = primaryStage;
        this.primaryStage.setTitle("SongLib");
        this.primaryStage.setResizable(false);

        showPrimaryScene();
    }
    
    //Used to Write into XML on exit
    @Override
    public void stop(){
        System.out.println("Stage is closing");
        for (int i = 0 ; i < songData.size(); i++) {
        	System.out.println(songData.get(i).getName());
        }
        xmlHandler.writeSong(songData);
    }
    
    public void showPrimaryScene() {
    	try {
            // Load root layout from fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("PrimaryScene.fxml"));
            rootLayout = (AnchorPane) loader.load();
            
            // Give the controller access to the main app
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
    
    public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
}
