package client.components;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import client.Client;

public class Options extends JPanel implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Client client = null;
	private JButton back = null;
	private JButton toggleFullScreen = null;

	Options(Client client){
		super();
		this.client = client;
		this.setLayout(new FlowLayout());
		
		
		toggleFullScreen = new JButton(client.getConfig().get("client.options.button.toggleFullScreen"));
		back = new JButton(client.getConfig().get("client.options.button.back"));
		
		JButton[] buttons = {toggleFullScreen, back};
		for(JButton b : buttons){
			this.add(b);
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

}
