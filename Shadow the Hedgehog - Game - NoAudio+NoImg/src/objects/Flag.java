package objects;

import main.Game;

public class Flag extends GameObject {

	public Flag (int x, int y, int objType) {
		super(x, y, objType);
		initHitbox(x, y, (int) (1 * Game.SCALE), (int) (10 * Game.SCALE));
		xDrawOffset=0;
		yDrawOffset=(int)(Game.SCALE*16);
		hitbox.y+=yDrawOffset;
		
	
	}

}

