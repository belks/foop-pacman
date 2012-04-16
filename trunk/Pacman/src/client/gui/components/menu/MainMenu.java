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
	
	
	
	public MainMenu(PacmanGUI gui){
		super(gui.getConfig().get("client.mainmenu"), gui);
		this.setLayout(new BorderLayout());
		this.setSubView(new JLabel(ImageDealer.getIcon(this.getGUI().getConfig().get("client.mainmenu.image"))));
		this.add(this.createButtonPanel(), BorderLayout.WEST);
	}

	
	private JPanel createButtonPanel(){
		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.setOpaque(false);
		
		Config config = this.getGUI().getConfig();
		newGame = new JButton(config.get("client.mainmenu.button.newgame"));
		options = new JButton(config.get("client.mainmenu.button.options"));
		
		
		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		p1.setOpaque(false);
		JButton[] buttons = {newGame, options};
		for(JButton b : buttons){
			b.addActionListener(this);
			b.setFont(View.getDefaultFont());
			p1.add(b);
		}
		buttonPanel.add(p1, BorderLayout.CENTER);
		
		
		
		exit = new JButton(config.get("client.mainmenu.button.exit"));
		exit.addActionListener(this);
		exit.setFont(View.getDefaultFont());
		buttonPanel.add(exit, BorderLayout.SOUTH);
		this.setPanelBorder(buttonPanel);	
		
		return buttonPanel;
	}
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(this.exit)){
			System.exit(0);
			
		}else if(e.getSource().equals(this.options)){
			this.setSubView(new OptionsMenu(this.getGUI()));
			
		}else if(e.getSource().equals(this.newGame)){
			this.setSubView(new ConnectMenu(this.getGUI()));
		}
	}
	
	
	

}
