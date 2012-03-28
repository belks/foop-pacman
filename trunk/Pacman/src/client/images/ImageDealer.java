package client.images;

import java.net.URL;
import java.util.LinkedHashMap;

import javax.swing.ImageIcon;

public class ImageDealer {
	static LinkedHashMap<String, ImageIcon> icons = new LinkedHashMap<String, ImageIcon>();
	
	
	private static ImageIcon createImageIcon(String name) {
			URL imgURL = ImageDealer.class.getResource(name);
			if (imgURL != null) {
					return new ImageIcon(imgURL);
			} else {
				System.err.println("Couldn't find file: " + name);
				return null;
			}
	}
	
	
	public static ImageIcon getIcon(String name){
		if(icons.containsKey(name)){
			return icons.get(name);
		}else{
			ImageIcon icon = createImageIcon(name);
			icons.put(name, icon);
			return icon;
		}
	}

}
