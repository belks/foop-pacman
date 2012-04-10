package client.gui.components.menu;




import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import client.gui.PacmanGUI;
import client.gui.components.View;
import client.gui.components.game.ActiveGame;



public class ConnectMenu extends View implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton connect = null;
	private JButton startServer = null;



	public ConnectMenu(PacmanGUI gui){
		super(gui.getConfig().get("client.connectmenu"), gui);
		this.setLayout(new BorderLayout());
		
		this.connect = new JButton(this.getGUI().getConfig().get("client.connectmenu.button.connect"));
		this.startServer = new JButton(this.getGUI().getConfig().get("client.connectmenu.button.startServer"));
		
		
		
		JPanel connectOptions = new JPanel();
		connectOptions.setOpaque(false);
		connectOptions.setLayout(new BoxLayout(connectOptions, BoxLayout.Y_AXIS));
		
		JButton[] buttons = {startServer, connect};
		for(JButton b:buttons){
			b.setFont(this.getDefaultFont());
			b.addActionListener(this);
			connectOptions.add(b);
		}
		
		this.setPanelBorder(connectOptions);
	
		this.add(connectOptions, BorderLayout.WEST);
	}

	


	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(connect)){
			this.getGUI().setView(new ActiveGame(this.getGUI()));
			this.getGUI().getListener().connect("localhost", 4444);
		}
		
		if(arg0.getSource().equals(startServer)){
			this.getGUI().getListener().createServer(4444);
		}
		
	}

	
	
}
