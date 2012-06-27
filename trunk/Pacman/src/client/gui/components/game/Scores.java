package client.gui.components.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import common.gameobjects.Pacman;

import client.gui.PacmanGUI;
import client.gui.components.View;
import client.gui.components.menu.MainMenu;

/**
 * Displays the players total points at the end of the game.
 * @author Stefan
 *
 */
public class Scores extends View implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JButton backToMenu = null;

	
	/**
	 * Constructor
	 * @param title
	 * @param client
	 * @param pacs
	 */
	public Scores(String title, PacmanGUI client, List<Pacman> pacs) {
		super(title, client);
		
		JLabel headline = new JLabel(this.getConfig().get("client.scores.headline"));
		this.setStyle(headline);
		this.add(headline, BorderLayout.NORTH);
		
		Pacman winner = null;
		JPanel center = new JPanel(new GridLayout(0,2));
		center.setOpaque(false);
		for(Pacman p : pacs){
			if(winner == null){
				winner = p;
			}else{
				if(p.getTotalCoints() > winner.getTotalCoints()){
					winner = p;
				}
			}
						
			JLabel l = new JLabel(p.getName());
			this.setStyle(l);
			center.add(l);
			
			l = new JLabel(p.getTotalCoints()+"");
			this.setStyle(l);
			center.add(l);
		}
		
		
		JLabel winnerLabel = new JLabel(winner.getName()+" "+this.getConfig().get("client.scores.winner"));
		this.setStyle(winnerLabel);
		center.add(winnerLabel);
		
		this.add(center, BorderLayout.CENTER);
		
				
		backToMenu = new JButton(this.getConfig().get("client.scores.button.back"));
		backToMenu.addActionListener(this);
		backToMenu.setFont(View.getDefaultFont());
		this.add(backToMenu, BorderLayout.SOUTH);
		
	}

	/**
	 * Shorthand for styling labels.
	 * @param l
	 */
	private void setStyle(JLabel l){
		l.setHorizontalAlignment(JLabel.CENTER);
		l.setForeground(Color.WHITE);
		l.setFont(View.getDefaultFont());
		l.setOpaque(false);
	}

	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.getGUI().setView(new MainMenu(this.getGUI()));
	}

}
