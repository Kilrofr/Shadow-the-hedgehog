package objects;

import static utilz.Constants.objectsConstants.*;
import static utilz.Constants.EnemyConstants.GetSpriteAmount;




import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

import main.Game;

public class GameObject {
	protected int x,y,objType;
	protected Rectangle2D.Float hitbox;
	protected boolean doAnimation, active = true;
	protected int aniTick, aniIndex, aniSpeed=25;
	protected int xDrawOffset, yDrawOffset;
	
	public GameObject(int x,int y,int objType) {
		this.x=x;
		this.y=y;
		this.objType=objType;
	}

	protected void initHitbox(float x, float y, int width, int height) {
		hitbox = new Rectangle2D.Float(x, y,(int) (width* Game.SCALE), (int)(height * Game.SCALE));
	}
	public void drawHitbox(Graphics g, int xLvlOffset) {
		// For debugging the hitbox
		g.setColor(Color.PINK);
		g.drawRect((int) hitbox.x - xLvlOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
	}

	
	protected void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(objType)){
				aniIndex = 0;
			}
			if(objType==RING) {
				doAnimation=true;
				active=true;
			}
				
		}
	}
	
	public void reset() {
		aniIndex =0;
		aniTick = 0;
		active = true;
		
		if(objType==RING) 
			doAnimation=false;
		else
			doAnimation=true;
		
	}

	public int getObjType() {
		return objType;
	}


	public Rectangle2D.Float getHitbox() {
		return hitbox;
	}



	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getxDrawOffset() {
		return xDrawOffset;
	}


	public int getyDrawOffset() {
		return yDrawOffset;
	}
	
	public int getAnimationIndex() {
		return aniIndex;
	}

	
}
