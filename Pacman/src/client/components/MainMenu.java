package client.components;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import client.Client;
import common.Config;

public class MainMenu extends JPanel implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton newGame = null;
	private JButton options = null;
	private JButton exit = null;
	private Client client = null;

	public MainMenu(Client client){
		this.setOpaque(true);
		this.client = client;
		Config config = client.getConfig();
		this.setLayout(new GridLayout(3,0,20,20));
		
		newGame = new JButton(config.get("client.mainmenu.button.newgame"));
		options = new JButton(config.get("client.mainmenu.button.options"));
		exit = new JButton(config.get("client.mainmenu.button.exit"));
		
		JButton[] buttons = {newGame, options, exit};
		for(JButton b : buttons){
			//b.setAlignmentX(Component.CENTER_ALIGNMENT);
			b.addActionListener(this);
			this.add(b);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(this.exit)){
			System.exit(0);
			
		}else if(e.getSource().equals(this.options)){
			client.addMessage("Changing from mainmenu to options.");
			client.setView(new Options(client));
			
		}else if(e.getSource().equals(this.newGame)){
			client.addMessage("Changing from mainmenu to newgame.");
			client.setView(new Game(client));
		}
	}

}
