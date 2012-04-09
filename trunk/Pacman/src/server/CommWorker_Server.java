import java.net.Socket;
import common.CommWorker;

public class CommWorker_Server extends CommWorker {

	private int clientNum;

	public CommWorker_Server(Socket address, int clientNum) {
		super(address);
		this.clientNum = clientNum;
	}

	@Override
	protected void processInput(String line) {
		System.out.println(clientNum + ": " + line);
	}

}
