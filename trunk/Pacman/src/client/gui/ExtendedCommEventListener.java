package client.gui;

import common.communication.CommEventObject;
import common.gameobjects.Game;

public interface ExtendedCommEventListener {
	public void handleCommEvent(CommEventObject e, Game g);
}
