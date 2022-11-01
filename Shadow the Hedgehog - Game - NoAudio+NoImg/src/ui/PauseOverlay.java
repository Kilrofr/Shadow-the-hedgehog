package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;

import static utilz.Constants.UI.PauseButtons.*;
import static utilz.Constants.UI.URMButtons.URM_DEFAULT_HEIGHT;
import static utilz.Constants.UI.URMButtons.URM_DEFAULT_WIDTH;
import static utilz.Constants.UI.Buttons.*;

import main.Game;
import utilz.LoadSave;


public class PauseOverlay {
	
	private Playing playing;
	private UrmButton menu;
	private UrmButton resume;
	private UrmButton retry;
	private BufferedImage img;
	private int bgH;
	private int bgW;
	private int bgX;
	private int bgY;
	
	public PauseOverlay(Playing playing) {
		this.playing = playing;
		loadBackground();
		createSoundButtons();

	}
	private void createSoundButtons() {
		// TODO Auto-generated method stub
		int X = (int)(285*Game.SCALE);//position Y du premier bouton
		int Ymenu = (int)(245*Game.SCALE);
		int Yretry = (int)(205*Game.SCALE);
		int Yresume= (int)(165*Game.SCALE);
		menu =new UrmButton(X, Ymenu, URM_DEFAULT_WIDTH, URM_DEFAULT_HEIGHT, 1);
		retry =new UrmButton(X, Yretry, URM_DEFAULT_WIDTH, URM_DEFAULT_HEIGHT, 2);
		resume =new UrmButton(X, Yresume, URM_DEFAULT_WIDTH, URM_DEFAULT_HEIGHT, 0);
	}
	private void loadBackground() {
		// TODO Auto-generated method stub
		img = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
		bgW = (int)(img.getWidth() * Game.SCALE)/2;
		bgH = (int)(img.getHeight() * Game.SCALE)/2;
		bgX = Game.GAME_WIDTH/2- bgW/2;
		bgY = (int)(50* Game.SCALE);

	}
	public void update() {
		retry.update();
		menu.update();
		resume.update();

	}
	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.drawImage(img, bgX, bgY, bgW, bgH, null);
		resume.draw(g);
		retry.draw(g);
		menu.draw(g);
		

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		resume.setMousePressed(false);
		menu.setMousePressed(false);
		retry.setMousePressed(false);
		
		if(isIn(menu, e))
			menu.setMousePressed(true);
		else if(isIn(resume,e))
			resume.setMousePressed(true);	
		else if(isIn(retry, e))
			retry.setMousePressed(true);
		
	}


	public void mouseReleased(MouseEvent e) {
		
		if(isIn(menu, e)) {
			if(menu.isMousePressed()) {
				playing.resetAll();
				playing.setGamestate(Gamestate.MENU);
			}
				
			}else if(isIn(resume,e)) {
				if(resume.isMousePressed())
					playing.unpauseGame();
				}
		else if(retry.isMousePressed()) {
			playing.resetAll();
			playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLvlIndex());
		}
				
		
			retry.resetBools();
			menu.resetBools();
			resume.resetBools();
		
	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		resume.setMouseOver(false);
		menu.setMouseOver(false);
		retry.setMouseOver(false);

		if(isIn(menu, e))
			menu.setMouseOver(true);
		else if(isIn(resume,e))
			resume.setMouseOver(true);	
		else if(isIn(retry, e))
			retry.setMouseOver(true);
	}
	
	
	
	private boolean isIn(UrmButton b,MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
		
}
