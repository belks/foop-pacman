package client.images;

import java.net.URL;

import javax.swing.ImageIcon;

public class ImageDealer {
	
	
	public static ImageIcon createImageIcon(String path) {
			URL imgURL = ImageDealer.class.getResource(path);
			if (imgURL != null) {
					return new ImageIcon(imgURL);
			} else {
				System.err.println("Couldn't find file: " + path);
				return null;
			}
	}

}
