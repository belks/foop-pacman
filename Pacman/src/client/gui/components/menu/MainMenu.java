package client.gui.components.menu;



import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.gui.PacmanGUI;
import client.gui.components.View;
import client.gui.images.ImageDealer;
import common.tools.Config;

public class MainMenu extends View implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton newGame = null;
	private JButton options = null;
	private JButton exit = null;
	
	
	
	public MainMenu(PacmanGUI client){
		super("MainMenu", client);
		
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
			b.setFont(this.getDefaultFont());
			buttonPanel.add(b);
		}
		this.add(buttonPanel, BorderLayout.SOUTH);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(this.exit)){
			System.exit(0);
			
		}else if(e.getSource().equals(this.options)){
			this.getGUI().setView(new OptionsMenu(this.getGUI()));
			
		}else if(e.getSource().equals(this.newGame)){
			this.getGUI().setView(new ConnectMenu(this.getGUI()));
		}
	}
	
	
	

}
