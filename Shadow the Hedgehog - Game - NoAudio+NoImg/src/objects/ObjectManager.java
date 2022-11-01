package objects;

import static utilz.Constants.objectsConstants.*;
import static utilz.Constants.Explosion.*;
import static utilz.Constants.PlayerConstants.*;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import audio.AudioPlayer;
import entities.Player;
import gamestates.Playing;
import levels.Level;
import main.Game;
import utilz.LoadSave;

public class ObjectManager {
	
	private BufferedImage[] ringimgs;
	private BufferedImage spikeimg,flagimg, explosionImg;
	private Playing playing;
	private ArrayList<Ring>rings;
	private ArrayList<Spike>spikes;
	public ArrayList<Explosion>explosions = new ArrayList<>();
	private ArrayList<Flag>flags;
	public static int nbreRing=0;

	
	public ObjectManager(Playing playing) {
		this.playing=playing;
		loadImgs();
	}
	
	public void loadObjects(Level newLevel) {
		// TODO Auto-generated method stub
		rings=newLevel.getRings();
		spikes=newLevel.getSpikes();
		flags=newLevel.getFlags();
		explosions.clear();
	}
	public void CheckObjectTouchedPlayer(Rectangle2D.Float hitbox) {
		for(Ring r : rings)
			if(r.isActive()) {
				if (hitbox.intersects(r.getHitbox())) {
					r.setActive(false);
					applyEffectToPlayer(r);
				}
			}
	}
	
	
	public void applyEffectToPlayer(Ring r) {
		if(r.getObjType() == RING) {
			nbreRing+=1;
			playing.getGame().getAudioPlayer().playEffect(AudioPlayer.ring);

			//System.out.println("nbre ring: " + nbreRing);
		}

	}

	
	public void Explosionblast(Explosion e) {
	// TODO Auto-generated method stub		
			explosions.add(new Explosion((int)e.getHitbox().x,(int)e.getHitbox().y));
	return;
		
}
			
		

	
	
	private void loadImgs() {
		// TODO Auto-generated method stub
		ringimgs = new BufferedImage[30];
		BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.RINGS );
		for(int i = 0; i < ringimgs.length;i++)
			//ringimgs[i]=temp.getSubimage(i*B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
			ringimgs[i]=temp.getSubimage(i*RING_WIDTH_DEFAULT,  0, RING_WIDTH_DEFAULT, 61);
		
		spikeimg= LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);
		flagimg= LoadSave.GetSpriteAtlas(LoadSave.FLAG_ATLAS);
		explosionImg= LoadSave.GetSpriteAtlas(LoadSave.EXPLOSION);
		
	}
	public void update(int[][] lvlData, Player player) {
		for (Ring r: rings)
			if(r.isActive())
				r.update();
		
		updateExplosion(lvlData,player);
		
	}
	private void updateExplosion(int[][] lvlData, Player player) {
		// TODO Auto-generated method stub
		for (Explosion e : explosions)
			if(e.isActive())
				e.updatepos();
			
	}

	public void draw(Graphics g, int xLvlOffset) {
		drawRings(g, xLvlOffset);
		drawTraps(g, xLvlOffset);
		drawFlags(g, xLvlOffset);
		drawExplosions(g,xLvlOffset);
	}	
	
	public void drawExplosions(Graphics g, int xLvlOffset) {
		// TODO Auto-generated method stub
		for (Explosion e : explosions)
			g.drawImage(explosionImg, (int) (e.getHitbox().x - xLvlOffset), (int) (e.getHitbox().y), Explosion_WIDTH, Explosion_HEIGHT, null);

	}

	private void drawFlags(Graphics g, int xLvlOffset) {
		for (Flag f : flags)
			g.drawImage(flagimg, (int) (f.getHitbox().x - xLvlOffset), (int) (f.getHitbox().y - f.getyDrawOffset()), FLAG_WIDTH, FLAG_HEIGHT+10, null);

	}
	public void checkSpikesTouched(Player p) {
		for (Spike s : spikes)
			if (s.getHitbox().intersects(p.getHitbox())) {
				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.pics);
				p.kill();
			}
				
	}	
	public void checkFlagTouched(Player p) {
		for (Flag f : flags)
			if (f.getHitbox().intersects(p.getHitbox())) {
				playing.getGame().getAudioPlayer().lvlCompleted();
				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.panneau);
				//playSong(AudioPlayer.LEVELCOMPLETED);
				playing.setLevelCompleted(true);
				
			}
				

	}

	private void drawTraps(Graphics g, int xLvlOffset) {
		for (Spike s : spikes)
			g.drawImage(spikeimg, (int) (s.getHitbox().x - xLvlOffset), (int) (s.getHitbox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);

	}

	private void drawRings(Graphics g, int xLvlOffset) {
		// TODO Auto-generated method stub
		for (Ring r: rings)
			if(r.isActive()) {
				g.drawImage(ringimgs[r.getAnimationIndex()],
						(int) (r.getHitbox().x - r.getxDrawOffset() -xLvlOffset),
						(int) (r.getHitbox().y - r.getyDrawOffset()),
						RING_WIDTH, RING_HEIGHT, null);

			}
	}
	public void resetAllObject() {
		for (Ring r: rings)
			r.reset();
		
	}	




}
