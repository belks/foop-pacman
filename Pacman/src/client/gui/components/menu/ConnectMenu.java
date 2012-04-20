package client.gui.components.menu;




import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import common.tools.Config;

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


	public ConnectMenu(PacmanGUI gui){
		super(gui.getConfig().get("client.connectmenu"), gui);
		Config conf = this.getGUI().getConfig();
		this.setLayout(new BorderLayout());
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setOpaque(false);
		
		
		
		try {
			String adr = InetAddress.getLocalHost().toString();
			JLabel clientIP = new JLabel(conf.get("client.connectmenu.label.clientip")+" "+adr);		
			this.setLabelStyle(clientIP);
			mainPanel.add(clientIP);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		
		JPanel serverPanel = new JPanel();
		serverPanel.setOpaque(false);
		serverPanel.setLayout(new FlowLayout());
		
		JLabel portLabel = new JLabel(conf.get("client.connectmenu.label.port"));
		this.setLabelStyle(portLabel);
		serverPanel.add(portLabel);
		
		serverPort.setText(conf.get("client.connectmenu.textfield.port"));
		serverPanel.add(serverPort);
		
		startServer = new JButton(conf.get("client.connectmenu.button.startServer"));
		startServer.setFont(View.getDefaultFont());
		startServer.addActionListener(this);
		serverPanel.add(startServer);
		
		TitledBorder border1 = new TitledBorder(conf.get("client.connectmenu.border.createserver"));
		border1.setTitleColor(Color.WHITE);
		serverPanel.setBorder(border1);
		mainPanel.add(serverPanel);
		
		JPanel connectPanel = new JPanel();
		connectPanel.setOpaque(false);
		connectPanel.setLayout(new FlowLayout());
		
		address.setText(conf.get("client.connectmenu.textfield.address"));
		connectPanel.add(address);
		
		port.setText(conf.get("client.connectmenu.textfield.port"));
		connectPanel.add(port);
		
		connect = new JButton(conf.get("client.connectmenu.button.connect"));
		connect.addActionListener(this);
		connectPanel.add(connect);
		
		TitledBorder border2 = new TitledBorder(conf.get("client.connectmenu.border.connectclient"));
		border2.setTitleColor(Color.WHITE);
		connectPanel.setBorder(border2);
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
			this.connect(this.address.getText(), new Integer(this.port.getText()));		
		}
				
		if(arg0.getSource().equals(startServer)){
			this.createServer(new Integer(this.serverPort.getText()));
			this.connect(this.address.getText(), new Integer(this.serverPort.getText()));
		}
		
	}
	
	
	
	private void connect(String address, int port){
		this.getGUI().setView(new ActiveGame(this.getGUI()));
		
		for(GUIListener l : this.getGUI().getListeners()){
			boolean ok = l.connect(address,port);
			if(!ok){
				this.getGUI().showMessageBox(true);
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




	



	
	
}
