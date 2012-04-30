package client.gui.components.menu;




import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.LinkedList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import client.gui.GUIListener;
import client.gui.PacmanGUI;
import client.gui.components.View;
import client.gui.components.game.ActiveGame;


/**
 * Menu that offers buttons and input fields for server creation and connection
 */
public class ConnectMenu extends View implements ActionListener{

	
	private static final long serialVersionUID = 1L;
	private JButton connect = null;
	private JButton startServer = null;
	private JTextField address = new JTextField(10);
	private JTextField port = new JTextField(4);
	private JTextField serverPort = new JTextField(4);
	private PlayerSelection players = new PlayerSelection(3);
	

	public ConnectMenu(PacmanGUI gui){
		super(gui.getConfig().get("client.connectmenu"), gui);
		this.setLayout(new BorderLayout());
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setOpaque(false);
		
		
		
		try {
			String adr = InetAddress.getLocalHost().toString();
			JLabel clientIP = new JLabel(this.getConfig().get("client.connectmenu.label.clientip")+" "+adr);		
			this.setLabelStyle(clientIP);
			mainPanel.add(clientIP);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		JPanel north = new JPanel(new FlowLayout());
		north.setOpaque(false);
		
		JLabel playerLabel = new JLabel(this.getConfig().get("client.connectmenu.label.playerName"));
		this.setLabelStyle(playerLabel);
		north.add(playerLabel);
		
		
		north.add(players);
		
		mainPanel.add(north);
		
		
		JPanel serverPanel = new JPanel();
		serverPanel.setOpaque(false);
		serverPanel.setLayout(new FlowLayout());
		
		JLabel portLabel = new JLabel(this.getConfig().get("client.connectmenu.label.port"));
		this.setLabelStyle(portLabel);
		serverPanel.add(portLabel);
		
		serverPort.setText(this.getConfig().get("client.connectmenu.textfield.port"));
		serverPanel.add(serverPort);
		
		startServer = new JButton(this.getConfig().get("client.connectmenu.button.startServer"));
		startServer.setFont(View.getDefaultFont());
		startServer.addActionListener(this);
		serverPanel.add(startServer);
		
		TitledBorder border1 = new TitledBorder(this.getConfig().get("client.connectmenu.border.createserver"));
		border1.setTitleColor(Color.WHITE);
		serverPanel.setBorder(border1);
		mainPanel.add(serverPanel);
		
		JPanel connectPanel = new JPanel();
		connectPanel.setOpaque(false);
		connectPanel.setLayout(new FlowLayout());
		
		address.setText(this.getConfig().get("client.connectmenu.textfield.address"));
		connectPanel.add(address);
		
		port.setText(this.getConfig().get("client.connectmenu.textfield.port"));
		connectPanel.add(port);
		
		connect = new JButton(this.getConfig().get("client.connectmenu.button.connect"));
		connect.addActionListener(this);
		connectPanel.add(connect);
		
		TitledBorder border2 = new TitledBorder(this.getConfig().get("client.connectmenu.border.connectclient"));
		border2.setTitleColor(Color.WHITE);
		connectPanel.setBorder(border2);
		mainPanel.add(connectPanel);
		
		
		
		
		JComponent[] comps = {serverPort,startServer,address,port,connect,players}; 
		for(JComponent c : comps){
			c.setFont(View.getDefaultFont());
			if(c instanceof JTextField){
				((JTextField) c).setHorizontalAlignment(JTextField.CENTER);
			}
		}
		
		
		this.setPanelBorder(mainPanel);
		
		
		this.add(mainPanel, BorderLayout.CENTER);
	}

	
	


	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0.getSource().equals(connect) || arg0.getSource().equals(startServer)){
			ActiveGame activeGame = new ActiveGame(this.getGUI(), this.players.getActivePlayers());
			this.getGUI().setView(activeGame);	
						
			if(arg0.getSource().equals(startServer)){
				activeGame.setAsServerHost(true);
				this.createServer(new Integer(this.serverPort.getText()));
				this.connect(this.address.getText(), new Integer(this.serverPort.getText()));
			}else{
				//only connect
				this.connect(this.address.getText(), new Integer(this.port.getText()));
			}
		
		}
		
		
	}
	
	
	
	private void connect(String address, int port){
		System.out.println("Opening connection for "+this.players.getActivePlayers().size()+" players.");
		for(String playerName : this.players.getActivePlayers()){
			for(GUIListener l : this.getGUI().getListeners()){
				boolean ok = l.connect(address,port, playerName);
				if(!ok){
					this.getGUI().showMessageBox(true);
				}
			}			
		}				
	}
	
	
	private void createServer(int port){
		for(GUIListener l : this.getGUI().getListeners()){
			boolean ok = l.createServer(port);
			if(!ok){
				this.getGUI().showMessageBox(true);
			}
		}
	}




	
	class PlayerSelection extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private JCheckBox[] activePlayers = null;
		private JTextField[] playerNames = null;

		
		PlayerSelection(int players){
			this.setOpaque(false);
			this.setLayout(new GridLayout(players,2));
			
			activePlayers = new JCheckBox[players];
			playerNames = new JTextField[players];
			
			for(int i=0; i<players; i++){
				JCheckBox active = new JCheckBox();
				if(i==0){
					active.setSelected(true);
					active.setEnabled(false);
				}
				active.setOpaque(false);
				active.setHorizontalAlignment(SwingConstants.CENTER);
				active.setFont(View.getDefaultFont());
				this.add(active);
				activePlayers[i]=active;
				
				JTextField playerName = new JTextField(20);
				playerName.setText("Player "+i);
				playerName.setFont(View.getDefaultFont());
				this.add(playerName);
				playerNames[i]=playerName;
			}
		}
		
		
		public LinkedList<String> getActivePlayers(){
			LinkedList<String> players = new LinkedList<String>();
			for(int i=0; i<activePlayers.length; i++){
				if(activePlayers[i].isSelected()){
					players.add(playerNames[i].getText());
				}
			}						
			return players;
		}
	}


	
	
}
