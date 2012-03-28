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

	Options(Client client){
		super();
		this.client = client;
		this.setLayout(new FlowLayout());
		JButton back = new JButton("back");
		this.add(back);
		back.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		client.setView(new MainMenu(client));
	}

}
