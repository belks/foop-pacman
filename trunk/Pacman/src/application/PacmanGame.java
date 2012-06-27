package application;

import java.io.IOException;
import server.ServerMain;
import client.Client;

/**
 * Main class for the game.
 * @author Stefan
 *
 */
public class PacmanGame {
	
	public static void main(String[] args){
		
		if(args.length >= 2 && args[0].equals("server")){
			try {
				new ServerMain(new Integer(args[1]));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			new Client(args);
		}
		
	}

}
