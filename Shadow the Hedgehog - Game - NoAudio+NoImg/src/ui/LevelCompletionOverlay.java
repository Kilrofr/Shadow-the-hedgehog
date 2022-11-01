package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import audio.AudioPlayer;
import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.Constants.UI.URMButtons;
import static utilz.Constants.UI.URMButtons.*;
import utilz.LoadSave;

public class LevelCompletionOverlay {
	private Playing playing;
	private UrmButton menu;
	private UrmButton next;
	private UrmButton retry;
	private BufferedImage img;
	private int bgH;
	private int bgW;
	private int bgX;
	private int bgY;
	
	public LevelCompletionOverlay(Playing playing) {
		this.playing=playing;
		initImg();
		initButton();
		
	}
	private void initButton() {
		// TODO Auto-generated method stub
		int X = (int)(285*Game.SCALE);//position Y du premier bouton
		int Ymenu = (int)(245*Game.SCALE);
		int Yretry = (int)(205*Game.SCALE);
		int Ynext= (int)(165*Game.SCALE);
		menu =new UrmButton(X, Ymenu, URM_DEFAULT_WIDTH, URM_DEFAULT_HEIGHT, 1);
		retry =new UrmButton(X, Yretry, URM_DEFAULT_WIDTH, URM_DEFAULT_HEIGHT, 2);
		next =new UrmButton(X, Ynext, URM_DEFAULT_WIDTH, URM_DEFAULT_HEIGHT, 0);
	}	
	
	
	
	private void initImg() {
		// TODO Auto-generated method stub
		img = LoadSave.GetSpriteAtlas(LoadSave.LVLCOMPLETED);
		bgW = (int)(img.getWidth() * Game.SCALE)/2;
		bgH = (int)(img.getHeight() * Game.SCALE)/2;
		bgX = Game.GAME_WIDTH/2- bgW/2;
		bgY = (int)(50* Game.SCALE);
	}
	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.drawImage(img, bgX, bgY, bgW, bgH, null);
		next.draw(g);
		retry.draw(g);
		menu.draw(g);

		
	}
	public void update() {
		menu.update();
		next.update();
		retry.update();
	}
	
	public void mouseMoved(MouseEvent e) {
		next.setMouseOver(false);
		menu.setMouseOver(false);
		retry.setMouseOver(false);

		if(isIn(menu, e))
			menu.setMouseOver(true);
		else if(isIn(next,e))
			next.setMouseOver(true);	
		else if(isIn(retry, e))
			retry.setMouseOver(true);
		
	}
	public void mousePressed(MouseEvent e) {
		next.setMousePressed(false);
		menu.setMousePressed(false);
		retry.setMousePressed(false);
		
		if(isIn(menu, e))
			menu.setMousePressed(true);
		else if(isIn(next,e))
			next.setMousePressed(true);	
		else if(isIn(retry, e))
			retry.setMousePressed(true);
	}	
	public void mouseReleased(MouseEvent e) {
			if(isIn(menu, e)) {
			if(menu.isMousePressed()) {
				playing.resetAll();
				playing.setGamestate(Gamestate.MENU);
			}
				
			}else if(isIn(next,e)) {
				if(next.isMousePressed()) {
					playing.loadNextLevel();
					playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLvlIndex());
				}

			}
		else if(isIn(retry, e)){
			if(retry.isMousePressed()) {
				playing.resetAll();
				playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLvlIndex());
			}
				
				
		}
			retry.resetBools();
			menu.resetBools();
			next.resetBools();
	}
	
	private boolean isIn(UrmButton b,MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
	
}
