package songlibfx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Song {

	private final StringProperty name;
	private final StringProperty artist;
	private final StringProperty album;
	private final IntegerProperty year;
	
	public Song() {
		this(null, null);
	}
	
	public Song(String name, String artist) {
		this.name = new SimpleStringProperty(name);
		this.artist = new SimpleStringProperty(artist);
		
		// Some initial dummy data, just for convenient testing.
		this.album = new SimpleStringProperty("some_album");
		this.year = new SimpleIntegerProperty(1234);
	}
	
	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}
	
	public StringProperty nameProperty() {
		return name;
	}

	public String getArtist() {
		return artist.get();
	}

	public void setArtist(String artist) {
		this.artist.set(artist);
	}
	
	public StringProperty artistProperty() {
		return artist;
	}
	
	public String getAlbum() {
		return album.get();
	}

	public void setAlbum(String album) {
		this.album.set(album);
	}
	
	public StringProperty albumProperty() {
		return album;
	}
	
	public int getYear() {
		return year.get();
	}

	public void setYear(int year) {
		this.year.set(year);
	}
	
	public IntegerProperty yearProperty() {
		return year;
	}
}
