package client.gui.components.menu;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import client.gui.PacmanGUI;
import client.gui.components.View;


public class OptionsMenu extends View implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton back = null;
	private JButton toggleFullScreen = null;

	
	
	public OptionsMenu(PacmanGUI client){
		super("Options", client);
		this.setLayout(new BorderLayout());
		
		
		toggleFullScreen = new JButton(client.getConfig().get("client.options.button.toggleFullScreen"));
		
		
		
		JPanel optionsPanel = new JPanel();
		optionsPanel.setOpaque(false);
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		
		JButton[] buttons = {toggleFullScreen};
		for(JButton b : buttons){
			b.setFont(this.getDefaultFont());
			b.addActionListener(this);
			optionsPanel.add(b);
		}
		
		this.add(optionsPanel, BorderLayout.CENTER);
		
		back = new JButton(client.getConfig().get("client.options.button.back"));
		back.addActionListener(this);
		back.setFont(this.getDefaultFont());
		this.add(back, BorderLayout.SOUTH);
		
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(back)){
			this.getGUI().setView(new MainMenu(this.getGUI()));
		}
		
		if(e.getSource().equals(toggleFullScreen)){
			if(this.getGUI().isFullScreen()){
				this.getGUI().setFullScreen(false);
			}else{
				this.getGUI().setFullScreen(true);
			}
		}
	}
	
	
	

}
