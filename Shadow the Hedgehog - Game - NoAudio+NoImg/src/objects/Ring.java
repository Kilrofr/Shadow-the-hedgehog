package objects;

import main.Game;

public class Ring extends GameObject{
	private float hoverOffset;
	private int maxHoverOffset, hoverDir=1;

	public Ring(int x, int y, int objType) {
		super(x, y, objType);
		doAnimation=true;
		initHitbox(x, y, (int) (10 * Game.SCALE), (int) (5 * Game.SCALE));
		xDrawOffset=(int)(8*Game.SCALE);
		yDrawOffset=(int)(3*Game.SCALE);
		
		maxHoverOffset=(int)(10*Game.SCALE);
	}
	public void update() {
		updateAnimationTick();
		updateHover();
		
	}
	private void updateHover() {
		// TODO Auto-generated method stub
		hoverOffset=(0.075f * Game.SCALE * hoverDir);
		
		if (hoverOffset>=maxHoverOffset)
			hoverDir=-1;
		else if(hoverOffset<0)
			hoverDir=1;
		
		hitbox.y=y+hoverOffset;
	}
	
	

}
