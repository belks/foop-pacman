package client.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;


/**
 * Displays System.out during game
 */
public class MessageBox extends JPanel{

	
	private static final long serialVersionUID = 1L;
	private JTextArea text = new JTextArea("");
	private PrintStream aPrintStream  = new PrintStream(new FilteredStream(new ByteArrayOutputStream()));
	
	
	public MessageBox(){
		super(new BorderLayout());
		text.setEditable(false);
		this.setBorder(new TitledBorder("Log: "));
		this.add(new JScrollPane(text), BorderLayout.CENTER);
		this.setVisible(false);
		this.setPreferredSize(new Dimension(800,200));
		//System.setErr(aPrintStream);
		System.setOut(aPrintStream);
	}
	
	
	class FilteredStream extends FilterOutputStream  implements Runnable{
	      public FilteredStream(OutputStream aStream) {
	         super(aStream);
	      }
	      
	      private int maxTextLenght = 5000000;

	      public void write(byte b[]) throws IOException {
	         String aString = new String(b).trim();
	         
	         if(text.getText().length() > maxTextLenght){
	        	 SwingUtilities.invokeLater(this);
	         }
	         
	         if(!aString.isEmpty()){
	        	 text.append(getTime()+" - "+aString+"\n");
	        	 text.setCaretPosition(text.getText().length());
	         }
	      }

	      public void write(byte b[], int off, int len) throws IOException {
	         String aString = new String(b , off , len);
	         this.write(aString.getBytes());
	      }
	      
	      private String getTime(){
	    	  SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
	    	  return df.format(new Date(System.currentTimeMillis()));
	      }
	      
	      
	      @Override
	  	  public void run() {
	  			if(text.getText().length() > maxTextLenght && maxTextLenght > 0){
	  				text.setText(text.getText().substring(maxTextLenght-1, text.getText().length()-1));
	  			}
	  	  }
	}


	
	
}
