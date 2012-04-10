package common.communication;

import java.util.EventObject;

public interface CommEventListener {
	public void handleCommEvent(CommEventObject e);
}
