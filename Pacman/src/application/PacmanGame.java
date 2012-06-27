package application;

import java.io.IOException;
import server.ServerMain;
import client.Client;


public class PacmanGame {
	
	public static void main(String[] args){
		
		if(arrayContains(args, "server")){
			try {
				new ServerMain(4444);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			new Client(args);
		}
		
	}
	
	
	private static boolean arrayContains(String[] array, String item){
		boolean found = false;
		for(Object o : array){
			if(o.equals(item)){
				found=true;
				break;
			}
		}
		return found;
	}
	

}
