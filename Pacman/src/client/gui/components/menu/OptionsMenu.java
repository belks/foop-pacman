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
	private JButton toggleFullScreen = null;

	
	
	public OptionsMenu(PacmanGUI gui){
		super(gui.getConfig().get("client.optionsmenu"), gui);
		this.setLayout(new BorderLayout());
		
		
		
		
		this.add(createButtonPanel(), BorderLayout.CENTER);	
	}

	private JPanel createButtonPanel(){
		JPanel optionsPanel = new JPanel();
		optionsPanel.setOpaque(false);
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		
		
		toggleFullScreen = new JButton(this.getGUI().getConfig().get("client.optionsmenu.button.toggleFullScreen"));
		
		JButton[] buttons = {toggleFullScreen};
		for(JButton b : buttons){
			b.setFont(this.getDefaultFont());
			b.addActionListener(this);
			optionsPanel.add(b);
		}
		
		this.setPanelBorder(optionsPanel);
		
		return optionsPanel;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
				
		if(e.getSource().equals(toggleFullScreen)){
			if(this.getGUI().isFullScreen()){
				this.getGUI().setFullScreen(false);
			}else{
				this.getGUI().setFullScreen(true);
			}
		}
	}
	
	
	

}
