package client.gui.images;

import java.awt.Image;
import java.net.URL;
import java.util.LinkedHashMap;

import javax.swing.ImageIcon;

public class ImageDealer {
	private static LinkedHashMap<String, ImageIcon> icons = new LinkedHashMap<String, ImageIcon>();
	
	
	private static ImageIcon createImageIcon(String name) {
			URL imgURL = ImageDealer.class.getResource(name);
			
			if (imgURL != null) {
				ImageIcon icon = new ImageIcon(imgURL);
				
				if(icon != null){
					System.out.println("Loaded image "+name+" successfully.");
					return icon;
					
				}else{
					System.out.println("Found image "+name+", but was unable to load it. Image == null");
					return null;
				}
				
			} else {
				System.out.println("Couldn't find image "+name+" !!!");
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
	
	
	public static Image getImage(String name){
		return getIcon(name).getImage();
	}

}
