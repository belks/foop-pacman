package client.gui.components.menu;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URLDecoder;
import java.util.TreeSet;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import common.tools.Config;

import client.gui.PacmanGUI;
import client.gui.components.View;


public class OptionsMenu extends View implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton toggleFullScreen = null;
	private JButton saveValues = null;
	
	
	
	public OptionsMenu(PacmanGUI gui){
		super(gui.getConfig().get("client.optionsmenu"), gui);
		this.setLayout(new BorderLayout());
		this.add(createPanel(), BorderLayout.CENTER);	
	}

	private JPanel createPanel(){
		JPanel optionsPanel = new JPanel();
		optionsPanel.setOpaque(false);
		optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
		this.setPanelBorder(optionsPanel);
		
		
		toggleFullScreen = new JButton(this.getGUI().getConfig().get("client.optionsmenu.button.toggleFullScreen"));
		saveValues = new JButton(this.getGUI().getConfig().get("client.optionsmenu.label.save"));
		
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		JButton[] buttons = {toggleFullScreen, saveValues};
		for(JButton b : buttons){
			b.setFont(View.getDefaultFont());
			b.addActionListener(this);
			buttonPanel.add(b);
		}
		optionsPanel.add(buttonPanel);
		
		
		int maxPlayers = this.getConfig().getInteger("client.playable.players");
		for(int i=1; i<=maxPlayers; i++){
			optionsPanel.add(new PlayerKeyConfigPanel(i, this.getConfig()));
		}
		
		
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
		
		
		if(e.getSource().equals(saveValues)){
			try {
				System.out.println("Saving config file at...");
				String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
				String decodedPath = URLDecoder.decode(path, "UTF-8")+"config.txt";
				System.out.println(decodedPath);
				BufferedWriter writer = new BufferedWriter(new FileWriter(new File(decodedPath)));
				
				for(String key : new TreeSet<String>(this.getGUI().getConfig().getKeySet())){
					writer.write(key+"="+this.getGUI().getConfig().get(key));
					writer.newLine();
				}
				writer.flush();
				writer.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	
	
	
	
	
	
	class PlayerKeyConfigPanel extends JPanel implements KeyListener{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final String[] keys = {"up", "down", "left", "right","log","togglefullscreen"};
		private Config config = null;
		private int player = 0;
		
		PlayerKeyConfigPanel(int playerNumber, Config config){
			this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			this.setOpaque(false);
			this.config = config;
			this.player = playerNumber;
			
			for(String key: keys){
				JPanel p = new JPanel();
				p.setLayout(new FlowLayout());
				p.setOpaque(false);
				
				JLabel l = new JLabel(config.get("client.optionsmenu.label."+key));
				l.setFont(View.getDefaultFont());
				l.setForeground(Color.WHITE);
				l.setOpaque(false);
				
				JTextField tf = new JTextField(10);
				tf.setText(KeyEvent.getKeyText(config.getInteger("client.keys.p"+player+"."+key)));
				tf.setFont(View.getDefaultFont());
				tf.addKeyListener(this);
				tf.setToolTipText(key);
				tf.setHorizontalAlignment(JTextField.CENTER);
				
				p.add(l);
				p.add(tf);
				
				this.add(p);
			}
			TitledBorder tb = new TitledBorder(config.get("client.optionsmenu.label.keyAssignment")+" "+playerNumber);
			tb.setTitleColor(Color.WHITE);
			this.setBorder(tb);
		}
		
		
		
		@Override
		public void keyPressed(KeyEvent arg0) {
			if(arg0.getSource() instanceof JTextField){
				JTextField tf = (JTextField) arg0.getSource();
				String key = tf.getToolTipText();
				int keyCode = arg0.getKeyCode();
				
				boolean unAssigned = true;
				for(String existingKey : keys){
					int oldKey = new Integer(config.get("client.keys.p"+player+"."+existingKey));
					if(keyCode == oldKey){
						unAssigned = false;
					}
				}
				
				if(unAssigned){
					config.put("client.keys.p"+player+"."+key, ""+keyCode);
					tf.setText(KeyEvent.getKeyText(keyCode));
					System.out.print("Key for "+key+" changed to "+KeyEvent.getKeyText(keyCode)+" - "+keyCode);
				}
				
				arg0.consume();
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0){
			arg0.consume();
		}

		@Override
		public void keyTyped(KeyEvent arg0) {	
			arg0.consume();
		}
	}

}
