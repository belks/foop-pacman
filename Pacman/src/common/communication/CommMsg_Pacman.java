package common.communication;

import java.awt.Color;
import java.awt.Point;

import common.gameobjects.*;

public class CommMsg_Pacman extends CommMsg {

	static final String PREFIX = "PACMAN:";

	private Pacman pacman;

	public Pacman getPacman() {
		return pacman;
	}

	@Override
	String getPrefix() {
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
		b.append(getPrefix()).append(SEPARATOR); // 0

		b.append(stringToMsg(pacman.getName())).append(SEPARATOR); // 1
		b.append(pacman.getColor().getRGB()).append(SEPARATOR); // 2
		b.append((int) pos.getX()).append(SEPARATOR); // 3
		b.append((int) pos.getY()).append(SEPARATOR); // 4
		b.append(pacman.getCoints()).append(SEPARATOR); // 5
		b.append(pacman.getTotalCoints()).append(SEPARATOR); // 6
		b.append(pacman.getId()); // 7
		b.append(pacman.getDirectionInt());

		msg = b.toString();
	}

	private void messageToPacman() {
		String[] split = msg.split(",");

		String name = split[1];
		Color color = new Color(Integer.parseInt(split[2]));
		Point pos = new Point(Integer.parseInt(split[3]),
				Integer.parseInt(split[4]));

		pacman = new Pacman(name, color);
		pacman.setPosition(pos);
		pacman.setCoints(Integer.parseInt(split[5]));
		pacman.setTotalCoints(Integer.parseInt(split[6]));
		pacman.setId(Integer.parseInt(split[7]));

		IStrategy direction;
		switch (Integer.parseInt(split[8])) {
		case 0:
			direction = new Right();
			break;
		case 1:
			direction = new Up();
			break;
		case 2:
			direction = new Left();
			break;
		case 3:
			direction = new Down();
			break;
		default:
			direction = new Up();
		}
		pacman.setDirection(direction);

	}
}
