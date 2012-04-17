package client;

import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import common.communication.CommEventListener;
import common.communication.CommEventObject;
import common.gameobjects.Level;

public class TestClient extends JFrame implements CommEventListener {

	private JTextArea test = new JTextArea();
	private CommWorker_Client comm;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestClient myTest = new TestClient();
	}

	public TestClient() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.add(test);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setText("Hello WOrld");

		this.pack();
		this.validate();
		this.setVisible(true);

		connect();
	}

	private void connect() {
		try {
			comm = new CommWorker_Client(new Socket("localhost", 4444));
			comm.addCommEventListener(this);
			Thread t = new Thread(comm);
			//t.setDaemon(true);
			t.start();
		} catch (Exception ex) {
			test.setText(ex.getMessage());
		}
	}

	@Override
	public void handleCommEvent(CommEventObject e) {
		drawLevel();
	}

	private void drawLevel() {
		Level l = comm.getGame().getLevel();
		if (l != null) {
			StringBuilder b = new StringBuilder();
			byte[][] m = l.getMap();
			for (int i = 0; i < m.length; i++) {
				for (int j = 0; j < m[0].length; j++) {
					b.append(m[i][j]);
				}
				b.append("\n");
			}
			test.setText(b.toString());
		}
		this.pack();
		this.validate();
		this.setVisible(true);
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		comm.shutdown();
	}
}
