package client.gui;

import common.communication.CommEventObject;
import common.gameobjects.Game;

/**
 * Replaces the normal CommEventListener to pass not only the comm event, but also the game object directly.
 * 
 * @author Stefan
 *
 */
public interface ExtendedCommEventListener {
	public void handleCommEvent(CommEventObject e, Game g);
}
