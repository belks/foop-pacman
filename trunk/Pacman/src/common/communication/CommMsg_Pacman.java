package common.communication;

import java.awt.Color;
import java.awt.Point;

import common.gameobjects.Pacman;

public class CommMsg_Pacman extends CommMsg {

	public static final String PREFIX = "PACMAN:";

	private Pacman pacman;

	@Override
	public String getPrefix() {
		return PREFIX;
	}

	public CommMsg_Pacman(Pacman p) {
		super();
		this.pacman = p;
		pacmanToMessage();
	}

	public CommMsg_Pacman(String msg) {
		super(msg);
		messageToPacman();
	}

	private void pacmanToMessage() {
		Point pos = pacman.getPosition();

		StringBuilder b = new StringBuilder();
		b.append(getPrefix()).append(SEPARATOR);

		b.append(stringToMsg(pacman.getName())).append(SEPARATOR); // 0
		b.append(pacman.getColor().getRGB()).append(SEPARATOR); // 1
		b.append((int) pos.getX()).append(SEPARATOR); // 2
		b.append((int) pos.getY()).append(SEPARATOR); // 3
		b.append(pacman.getCoints()).append(SEPARATOR); // 4
		b.append(pacman.getTotalCoints()).append(SEPARATOR); // 5
		b.append(pacman.getId()).append(SEPARATOR); // 6
		// Direction can be omitted
	}

	private void messageToPacman() {
		String[] split = msg.split(",");

		String name = split[0];
		Color color = new Color(Integer.parseInt(split[1]));
		Point pos = new Point(Integer.parseInt(split[2]),
				Integer.parseInt(split[3]));

		pacman = new Pacman(name, color);
		pacman.setPosition(pos);
		pacman.setCoints(Integer.parseInt(split[4]));
		pacman.setTotalCoints(Integer.parseInt(split[5]));
		pacman.setId(Integer.parseInt(split[6]));
		
	}

}
