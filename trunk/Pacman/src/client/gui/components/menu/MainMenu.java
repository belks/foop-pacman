package client.gui.components.menu;



import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
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
		this.setLayout(new BorderLayout());
		this.add(new JLabel(ImageDealer.getIcon(this.getGUI().getConfig().get("client.mainmenu.image"))), BorderLayout.CENTER);
		this.add(this.createButtonPanel(), BorderLayout.WEST);
	}

	
	private JPanel createButtonPanel(){
		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.setOpaque(false);
		
		Config config = this.getGUI().getConfig();
		newGame = new JButton(config.get("client.mainmenu.button.newgame"));
		options = new JButton(config.get("client.mainmenu.button.options"));
		
		
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		p1.setOpaque(false);
		JButton[] buttons = {newGame, options};
		for(JButton b : buttons){
			b.addActionListener(this);
			b.setFont(this.getDefaultFont());
			p1.add(b);
		}
		buttonPanel.add(p1, BorderLayout.CENTER);
		
		
		
		exit = new JButton(config.get("client.mainmenu.button.exit"));
		buttonPanel.add(exit, BorderLayout.SOUTH);
		
		
		return buttonPanel;
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
