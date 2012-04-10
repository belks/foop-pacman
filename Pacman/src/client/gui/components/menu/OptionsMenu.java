package client.gui.components.menu;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import client.gui.GUI;
import client.gui.components.View;


public class OptionsMenu extends View implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton back = null;
	private JButton toggleFullScreen = null;

	
	
	OptionsMenu(GUI client){
		super("Options", client);
		this.setLayout(new FlowLayout());
		
		
		toggleFullScreen = new JButton(client.getConfig().get("client.options.button.toggleFullScreen"));
		back = new JButton(client.getConfig().get("client.options.button.back"));
		
		JButton[] buttons = {toggleFullScreen, back};
		for(JButton b : buttons){
			this.add(b);
			b.setFont(this.getDefaultFont());
			b.addActionListener(this);
		}
		
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(back)){
			this.getClientGUI().setView(new MainMenu(this.getClientGUI()));
		}
		
		if(e.getSource().equals(toggleFullScreen)){
			if(this.getClientGUI().isFullScreen()){
				this.getClientGUI().setFullScreen(false);
			}else{
				this.getClientGUI().setFullScreen(true);
			}
		}
	}
	
	
	

}
