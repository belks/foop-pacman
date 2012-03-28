package client.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class MessageBox extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea text = new JTextArea("");
	private PrintStream aPrintStream  = new PrintStream(new FilteredStream(new ByteArrayOutputStream()));
	
	public MessageBox(){
		super(new BorderLayout());
		text.setEditable(false);
		this.setBorder(new TitledBorder("Log: "));
		this.add(new JScrollPane(text), BorderLayout.CENTER);
		this.setVisible(false);
		this.setPreferredSize(new Dimension(800,100));
		//System.setErr(aPrintStream);
		System.setOut(aPrintStream);
	}
	
	
	class FilteredStream extends FilterOutputStream {
	      public FilteredStream(OutputStream aStream) {
	         super(aStream);
	         }

	      public void write(byte b[]) throws IOException {
	         String aString = new String(b);
	         text.append(aString);
	         }

	      public void write(byte b[], int off, int len) throws IOException {
	         String aString = new String(b , off , len);
	         text.append(aString);
	 
	      }
	   }
	
}
