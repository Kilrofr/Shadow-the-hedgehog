package objects;

import main.Game;

public class Spike extends GameObject {

	public Spike(int x, int y, int objType) {
		super(x, y, objType);
		initHitbox(x, y, (int) (12 * Game.SCALE), (int) (32 * Game.SCALE));
		xDrawOffset=0;
		yDrawOffset=(int)(Game.SCALE*16);
		hitbox.y+=yDrawOffset;
		
	
	}

}
