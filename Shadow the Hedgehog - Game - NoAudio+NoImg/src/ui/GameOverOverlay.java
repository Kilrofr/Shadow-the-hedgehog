package ui;

import static utilz.Constants.UI.URMButtons.URM_DEFAULT_HEIGHT;
import static utilz.Constants.UI.URMButtons.URM_DEFAULT_WIDTH;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

public class GameOverOverlay {

	private Playing playing;
	private BufferedImage img;
	private int bgH;
	private int bgW;
	private int bgX;
	private int bgY;
	private UrmButton menu1;
	private UrmButton retry1;

	public GameOverOverlay(Playing playing) {
		this.playing = playing;
		createImg();
		initButton();
	}

	public void draw(Graphics g) {
		g.setColor(new Color(0, 0, 0, 200));
		g.drawImage(img, bgX, bgY, bgW, bgH, null);
		retry1.draw(g);
		menu1.draw(g);
		
//		g.setColor(Color.white);
//		g.drawString("Game Over", Game.GAME_WIDTH / 2, 150);
//		g.drawString("Press esc to enter Main Menu!", Game.GAME_WIDTH / 2, 300);

	}
	public void createImg() {
		img = LoadSave.GetSpriteAtlas(LoadSave.GAMEOVER);
		bgW = (int)(img.getWidth() * Game.SCALE)/2;
		bgH = (int)(img.getHeight() * Game.SCALE)/2;
		bgX = Game.GAME_WIDTH/2- bgW/2;
		bgY = (int)(50* Game.SCALE);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			playing.resetAll();
			Gamestate.state = Gamestate.MENU;
		}
	}
	private void initButton() {
		// TODO Auto-generated method stub
		int X = (int)(285*Game.SCALE);//position Y du premier bouton
		int Ymenu = (int)(225*Game.SCALE);
		int Yretry = (int)(175*Game.SCALE);
		menu1 =new UrmButton(X, Ymenu, URM_DEFAULT_WIDTH, URM_DEFAULT_HEIGHT, 1);
		retry1 =new UrmButton(X, Yretry, URM_DEFAULT_WIDTH, URM_DEFAULT_HEIGHT, 2);
	}	
	
	public void update() {
		menu1.update();
		retry1.update();
	}
	
	public void mouseMoved(MouseEvent e) {
		menu1.setMouseOver(false);
		retry1.setMouseOver(false);

		if(isIn(menu1, e))
			menu1.setMouseOver(true);

		else if(isIn(retry1, e))
			retry1.setMouseOver(true);
		
	}
	public void mousePressed(MouseEvent e) {
		menu1.setMousePressed(false);
		retry1.setMousePressed(false);
		
		if(isIn(menu1, e))
			menu1.setMousePressed(true);
	
		else if(isIn(retry1, e))
			retry1.setMousePressed(true);
	}	
	public void mouseReleased(MouseEvent e) {
			if(isIn(menu1, e)) {
			if(menu1.isMousePressed()) {
				playing.resetAll();
				playing.setGamestate(Gamestate.MENU);
			}
				
			}
		else if(isIn(retry1, e)){
			if(retry1.isMousePressed()) {
				playing.resetAll();
				playing.getGame().getAudioPlayer().setLevelSong(playing.getLevelManager().getLvlIndex());
			}
				
			
				
		}
			retry1.resetBools();
			menu1.resetBools();	}
	
	private boolean isIn(UrmButton b,MouseEvent e) {
		return b.getBounds().contains(e.getX(), e.getY());
	}
	
	
	
}
