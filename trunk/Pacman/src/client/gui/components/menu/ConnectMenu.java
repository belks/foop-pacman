package client.gui.components.menu;




import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;
import client.gui.GUIListener;
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
	private JTextField address = new JTextField(10);
	private JTextField port = new JTextField(4);
	private JTextField serverPort = new JTextField(4);


	public ConnectMenu(PacmanGUI gui){
		super(gui.getConfig().get("client.connectmenu"), gui);
		this.setLayout(new BorderLayout());
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.setOpaque(false);
		
		
		JPanel serverPanel = new JPanel();
		serverPanel.setOpaque(false);
		serverPanel.setLayout(new FlowLayout());
		serverPort.setText(this.getGUI().getConfig().get("client.connectmenu.textfield.port"));
		serverPanel.add(serverPort);
		startServer = new JButton(this.getGUI().getConfig().get("client.connectmenu.button.startServer"));
		startServer.setFont(View.getDefaultFont());
		startServer.addActionListener(this);
		serverPanel.add(startServer);
		mainPanel.add(serverPanel);
		
		JPanel connectPanel = new JPanel();
		connectPanel.setOpaque(false);
		connectPanel.setLayout(new FlowLayout());
		
		address.setText(this.getGUI().getConfig().get("client.connectmenu.textfield.address"));
		connectPanel.add(address);
		port.setText(this.getGUI().getConfig().get("client.connectmenu.textfield.port"));
		connectPanel.add(port);
		connect = new JButton(this.getGUI().getConfig().get("client.connectmenu.button.connect"));
		connect.addActionListener(this);
		connectPanel.add(connect);
		mainPanel.add(connectPanel);
		
		
		
		
		JComponent[] comps = {serverPort,startServer,address,port,connect}; 
		for(JComponent c : comps){
			c.setFont(View.getDefaultFont());
		}
		
		
		this.setPanelBorder(mainPanel);
		
		
		this.add(mainPanel, BorderLayout.CENTER);
	}

	


	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(connect)){
			this.getGUI().setView(new ActiveGame(this.getGUI()));
			for(GUIListener l : this.getGUI().getListeners()){
				l.connect(this.address.getText(), new Integer(this.port.getText()));
			}
		}
		
		if(arg0.getSource().equals(startServer)){
			for(GUIListener l : this.getGUI().getListeners()){
				l.createServer(new Integer(this.serverPort.getText()));
			}
			
			this.getGUI().setView(new ActiveGame(this.getGUI()));
			for(GUIListener l : this.getGUI().getListeners()){
				l.connect(this.address.getText(), new Integer(this.serverPort.getText()));
			}
		}
		
	}




	



	
	
}
