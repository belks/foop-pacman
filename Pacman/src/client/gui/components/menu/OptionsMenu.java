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
import client.gui.PacmanGUI;
import client.gui.components.View;


public class OptionsMenu extends View implements ActionListener, KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String[] keys = {"up", "down", "left", "right"};
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
		
		
		JButton[] buttons = {toggleFullScreen, saveValues};
		for(JButton b : buttons){
			b.setFont(View.getDefaultFont());
			b.addActionListener(this);
			optionsPanel.add(b);
		}
		
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setOpaque(false);
		
		for(int i = 0; i<4; i++){
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout());
			p.setOpaque(false);
			
			JLabel l = new JLabel(this.getGUI().getConfig().get("client.optionsmenu.label."+keys[i]));
			l.setFont(View.getDefaultFont());
			l.setForeground(Color.WHITE);
			l.setOpaque(false);
			
			JTextField tf = new JTextField(10);
			tf.setText(KeyEvent.getKeyText(this.getGUI().getConfig().getInteger("client.keys."+keys[i])));
			tf.setFont(View.getDefaultFont());
			tf.addKeyListener(this);
			tf.setToolTipText(""+i);
			
			p.add(l);
			p.add(tf);
			
			buttonPanel.add(p);
		}
		TitledBorder tb = new TitledBorder(this.getGUI().getConfig().get("client.optionsmenu.label.keyAssignment"));
		tb.setTitleColor(Color.WHITE);
		buttonPanel.setBorder(tb);
		optionsPanel.add(buttonPanel);
		
		
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

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getSource() instanceof JTextField){
			JTextField tf = (JTextField) arg0.getSource();
			int index = new Integer(tf.getToolTipText());
			int keyCode = arg0.getKeyCode();
			
			boolean unAssigned = true;
			for(String key : keys){
				int oldKey = new Integer(this.getGUI().getConfig().get("client.keys."+key));
				if(keyCode == oldKey){
					unAssigned = false;
				}
			}
			
			if(unAssigned){
				this.getGUI().getConfig().put("client.keys."+keys[index], ""+keyCode);
				tf.setText(KeyEvent.getKeyText(keyCode));
				System.out.print("Key for "+keys[index]+" changed to "+KeyEvent.getKeyText(keyCode)+" - "+keyCode);
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
