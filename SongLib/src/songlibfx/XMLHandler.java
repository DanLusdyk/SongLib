// Daniel Lusdyk, Ian Wilkinson

package songlibfx;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLHandler {
//	"SongLib/data/songFile.xml"
	File songFile;
	Path currentPath = Paths.get(System.getProperty("user.dir"));
	Path filePath = Paths.get(currentPath.toString(), "SongLib", "data", "songFile.xml");
	private final String path = filePath.toString();


	public void startHandler(){
	    try {
	    	System.out.println(path);
	    	songFile = new File(path);
	        if (songFile.createNewFile()) {
	          initXML();
	        } else {
		         System.out.println(songFile.getAbsolutePath());
	          System.out.println("File already exists.");
	        }
	      } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	    
	}
	
	public void initXML() {
		
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	            
	        // root elements	        
	        Document doc = docBuilder.newDocument();

	            
           
            Element root = doc.createElement("SongList");
            doc.appendChild(root);
            
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(path));
            transformer.transform(source, result);
            
            System.out.println("File saved!");
            
            source = new DOMSource(doc);

		} catch (Exception e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
		}   
	}
	
	
	public void writeSong(ObservableList<Song> songs){
			try {
				File xmlFile = new File(path);
				DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		        Document doc = documentBuilder.newDocument();
		           
	            Element root = doc.createElement("SongList");
	            doc.appendChild(root);
	    		for(int i = 0; i < songs.size(); i++) {
		    		Song song = songs.get(i);
					Element songElement = doc.createElement("songElement");
					
					Element nameElement = doc.createElement("Name");
					nameElement.appendChild(doc.createTextNode(song.getName()));
					songElement.appendChild(nameElement);
					
					Element artistElement = doc.createElement("Artist");
					artistElement.appendChild(doc.createTextNode(song.getArtist()));
					songElement.appendChild(artistElement);
					
					Element albumElement = doc.createElement("Album");
					albumElement.appendChild(doc.createTextNode(song.getAlbum()));
					songElement.appendChild(albumElement);
					
					Element yearElement = doc.createElement("Year");
					String yearString = String.valueOf(song.getYear());
					yearElement.appendChild(doc.createTextNode(yearString));
					songElement.appendChild(yearElement);
					
					root.appendChild(songElement);	
	    		}
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(xmlFile);
				transformer.transform(source, result);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
 

    

            
          
	}
	
	public ObservableList<Song> readSong() {
		File file = new File(path);
		ObservableList<Song> songList = FXCollections.observableArrayList();
		
	    try {
	      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	      Document doc = dBuilder.parse(file);
	      doc.getDocumentElement().normalize();
	      NodeList nodeList = doc.getElementsByTagName("songElement");
	      for (int i = 0; i < nodeList.getLength(); i++) {
	        Element element = (Element) nodeList.item(i);
	        Song song = (Song) ElementToSong(element) ;
	        songList.add(song);
	      }
	    } catch (ParserConfigurationException | SAXException | IOException e) {
	      e.printStackTrace();
	    }

	    return songList;
		

		
	}
	// Helper Function
	private Song ElementToSong(Element element) {
		Node childNode = element.getFirstChild();
		String nameString = childNode.getFirstChild().getNodeValue();
		childNode = childNode.getNextSibling();
		String artistString = childNode.getFirstChild().getNodeValue();
		childNode = childNode.getNextSibling();
		String albumString = childNode.getFirstChild().getNodeValue();
		childNode = childNode.getNextSibling();
		String yearString = childNode.getFirstChild().getNodeValue();
		int year = Integer.parseInt(yearString);

		System.out.println(nameString + artistString + albumString + yearString);

		Song song = new Song(nameString, artistString);
		song.setAlbum(albumString);
		song.setYear(year);
		return song;
	}
	
}
