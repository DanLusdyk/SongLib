// Daniel Lusdyk, Ian Wilkinson

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
		this.name = new SimpleStringProperty(name.trim());
		this.artist = new SimpleStringProperty(artist.trim());
		
		// Some initial dummy data, just for convenient testing
		this.album = new SimpleStringProperty("None");
		this.year = new SimpleIntegerProperty(0);
	}
	
	public String getName() {
		return name.get();
	}

	public String getNameUpper() {
		return name.get().toUpperCase();
	}

	public void setName(String name) {
		this.name.set(name.trim());
	}
	
	public StringProperty nameProperty() {
		return name;
	}

	public String getArtist() {
		return artist.get();
	}

	public void setArtist(String artist) {
		this.artist.set(artist.trim());
	}
	
	public StringProperty artistProperty() {
		return artist;
	}
	
	public String getAlbum() {
		return album.get();
	}

	public void setAlbum(String album) {
		this.album.set(album.trim());
	}
	
	public StringProperty albumProperty() {
		return album;
	}
	
	public int getYear() {
		return year.get();
	}

	public void setYear(int year) {
		if (year > 0) {	
			this.year.set(year);
		}
	}
	
	public IntegerProperty yearProperty() {
		return year;
	}
}
