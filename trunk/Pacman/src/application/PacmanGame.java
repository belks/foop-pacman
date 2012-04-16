package application;

import java.io.IOException;

import server.ServerMain;
import common.tools.Tools;
import client.Client;


public class PacmanGame {
	
	public static void main(String[] args){
		
		if(Tools.arrayContains(args, "server")){
			try {
				new ServerMain(4444);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			new Client(args);
		}
		
	}
	

}
