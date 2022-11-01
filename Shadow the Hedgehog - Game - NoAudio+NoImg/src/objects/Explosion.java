package objects;

import java.awt.geom.Rectangle2D;
import static utilz.Constants.Explosion.*;

public class Explosion {
	private Rectangle2D.Float hitbox;
	private boolean active=true;
	
	
	public Explosion(int x,int y){
		hitbox = new Rectangle2D.Float(x,y, Explosion_DEFAULT_WIDTH, Explosion_DEFAULT_HEIGHT);
	}
	


	private void setpos(int x,int y) {
		// TODO Auto-generated method stub
		hitbox.x=x;
		hitbox.y=y;

	}
	
	public Rectangle2D.Float getHitbox(){
		return hitbox;
	}
	
	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}	
	
	public void updatepos() {
		return;
	}
}

	
	
