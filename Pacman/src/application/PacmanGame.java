package application;

import java.io.IOException;

import server.ServerMain;
import common.tools.Tools;
import client.Client;


public class PacmanGame {
	
	public static void main(String[] args){
		
		if(Tools.arrayContains(args, "server")){
			try {
				new ServerMain(args);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			new Client(args);
		}
		
	}
	

}
