package application;

import common.tools.Tools;

import client.gui.ClientGUI;

public class PacmanGame {
	
	public static void main(String[] args){
		
		if(Tools.arrayContains(args, "server")){
			//start server only ...
			
		}else{
			new ClientGUI(args);
		}
		
	}
	

}
