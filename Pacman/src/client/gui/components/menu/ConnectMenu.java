package client.gui.components.menu;




import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import client.gui.ClientGUI;
import client.gui.components.View;
import client.gui.components.game.ActiveGame;



public class ConnectMenu extends View implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton connect = null;
	//private JButton startServer = null;



	public ConnectMenu(ClientGUI client){
		super("ConnectMenu", client);
		this.setLayout(new FlowLayout());
		
		this.connect = new JButton(this.getClientGUI().getConfig().get("client.connectmenu.button.connect"));
		this.connect.addActionListener(this);
		
		
		//this.startServer = new JButton(this.getClientGUI().getConfig().get("client.connectmenu.button.startServer"));
		//this.startServer.addActionListener(this);
		
		
		//this.add(startServer);
		this.add(connect);
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(connect)){
			this.getClientGUI().setView(new ActiveGame(this.getClientGUI()));
			this.getClientGUI().getListener().connect("localhost", 4444);
		}
		
		//if(arg0.getSource().equals(startServer)){
		//	this.getClientGUI().getListener().createServer(4444);
		//}
	}

	
	
}
