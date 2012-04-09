package gui.components;


import gui.Client;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import gui.images.ImageDealer;
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
		super();
		this.setOpaque(false);
		this.client = client;
		Config config = client.getConfig();
		this.setLayout(new BorderLayout());
		
		
		this.add(new JLabel(ImageDealer.getIcon(config.get("client.mainmenu.image"))), BorderLayout.CENTER);
		
		
		newGame = new JButton(config.get("client.mainmenu.button.newgame"));
		options = new JButton(config.get("client.mainmenu.button.options"));
		exit = new JButton(config.get("client.mainmenu.button.exit"));
		
		JPanel buttonPanel = new JPanel(new GridLayout(3,0,10,10));
		buttonPanel.setOpaque(false);
		JButton[] buttons = {newGame, options, exit};
		for(JButton b : buttons){
			b.addActionListener(this);
			b.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
			buttonPanel.add(b);
		}
		this.add(buttonPanel, BorderLayout.SOUTH);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(this.exit)){
			System.exit(0);
			
		}else if(e.getSource().equals(this.options)){
			client.setView(new OptionsMenu(client));
			
		}else if(e.getSource().equals(this.newGame)){
			client.setView(new ConnectMenu(client));
		}
	}
	
	
	public String toString(){
		return "MainMenu";
	}

}
