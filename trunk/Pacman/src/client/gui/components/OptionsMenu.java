package client.gui.components;


import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import client.gui.ClientGUI;


public class OptionsMenu extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClientGUI client = null;
	private JButton back = null;
	private JButton toggleFullScreen = null;

	OptionsMenu(ClientGUI client){
		super();
		this.client = client;
		this.setLayout(new FlowLayout());
		this.setOpaque(false);
		
		toggleFullScreen = new JButton(client.getConfig().get("client.options.button.toggleFullScreen"));
		back = new JButton(client.getConfig().get("client.options.button.back"));
		
		JButton[] buttons = {toggleFullScreen, back};
		for(JButton b : buttons){
			this.add(b);
			b.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
			b.addActionListener(this);
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(back)){
			client.setView(new MainMenu(client));
		}
		
		if(e.getSource().equals(toggleFullScreen)){
			if(client.isFullScreen()){
				client.setFullScreen(false);
			}else{
				client.setFullScreen(true);
			}
		}
	}
	
	
	public String toString(){
		return "Options";
	}

}
