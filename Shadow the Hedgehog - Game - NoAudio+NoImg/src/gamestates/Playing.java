package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import audio.AudioPlayer;
import entities.Crabby;
import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.GameOverOverlay;
import ui.LevelCompletionOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;
import static utilz.Constants.Environment.*;

public class Playing extends State implements Statemethods {
	private Player player;
	private LevelManager levelManager;
	private EnemyManager enemyManager;
	private ObjectManager objectManager;
	private PauseOverlay pauseOverlay;
	private GameOverOverlay gameOverOverlay;
	private LevelCompletionOverlay levelCompletionOverlay;
	private boolean paused = false;

	private int xLvlOffset;
	private int leftBorder = (int) (0.3* Game.GAME_WIDTH);
	private int rightBorder = (int) (0.3 * Game.GAME_WIDTH);
	//private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
	//private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
	private int maxLvlOffsetX;

	private BufferedImage backgroundImg, bigCloud;

	private boolean playerDying;
	private boolean gameOver;
	private boolean lvlCompleted=false;

	public Playing(Game game) {
		super(game);
		initClasses();

		backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);
		bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
		
		calcLvlOffset();
		loadStartLevel();
	}

	private void calcLvlOffset() {
		// TODO Auto-generated method stub
		maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
	}

	public void loadNextLevel() {
		resetAll();
		levelManager.loadNextLevel();
		player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

		
	}
	
	private void loadStartLevel() {
		// TODO Auto-generated method stub
		enemyManager.loadEnemies(levelManager.getCurrentLevel());
		objectManager.loadObjects(levelManager.getCurrentLevel());

	}



	private void initClasses() {
		levelManager = new LevelManager(game);
		enemyManager = new EnemyManager(this);
		objectManager = new ObjectManager(this);
		
		
		player = new Player(200, 200, (int) (22 * Game.SCALE), (int) (35 * Game.SCALE), this);
		player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
		player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
		pauseOverlay = new PauseOverlay(this);
		gameOverOverlay = new GameOverOverlay(this);
		levelCompletionOverlay = new LevelCompletionOverlay(this);
		
		
		
	
	}

	@Override
	public void update() {
		if(paused) {
			pauseOverlay.update();
		}
		else if(lvlCompleted)
			levelCompletionOverlay.update();
		else if (gameOver) {
			gameOverOverlay.update();
		}
		else if (playerDying) {
			player.update();
		}
		else {
			levelManager.update();
			objectManager.update(levelManager.getCurrentLevel().getLevelData(), player);
			player.update();
			enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
		}
		checkCloseToBorder();

	}

	private void checkCloseToBorder() {
		int playerX = (int) player.getHitbox().x;
		int diff = playerX - xLvlOffset;

		if (diff > rightBorder)
			xLvlOffset += diff - rightBorder;
		else if (diff < leftBorder)
			xLvlOffset += diff - leftBorder;

		if (xLvlOffset > maxLvlOffsetX)
			xLvlOffset = maxLvlOffsetX;
		else if (xLvlOffset < 0)
			xLvlOffset = 0;
	}

	@Override
	public void draw(Graphics g) {
		
		drawClouds(g);

		levelManager.draw(g, xLvlOffset);
		player.render(g, xLvlOffset);
		enemyManager.draw(g, xLvlOffset);
		objectManager.draw(g, xLvlOffset);


		if (paused) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		} else if (gameOver)
			gameOverOverlay.draw(g);
		else if(lvlCompleted)
			levelCompletionOverlay.draw(g);
	}

	private void drawClouds(Graphics g) {
		for (int i = 0; i < 8; i++) {

			g.drawImage(bigCloud, i * BIG_CLOUD_WIDTH*3 - (int) (xLvlOffset * 0.3), (int) (84 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
			g.drawImage(backgroundImg, i * background_WIDTH - (int) (xLvlOffset * 0.2), (int) (0 * Game.SCALE), background_WIDTH, background_HEIGHT, null);
			
		}
			

			}

	public void resetAll() {
		ObjectManager.nbreRing=0;
		gameOver = false;
		paused = false;
		lvlCompleted=false;
		playerDying=false;
		player.resetAll();
		enemyManager.resetAllEnemies();
		objectManager.resetAllObject();
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public void checkEnemyHit(Rectangle2D.Float attackBox) {
		enemyManager.checkEnemyHit(attackBox);
	}	
	public void checkRingTouched(Rectangle2D.Float attackBox) {
		objectManager.CheckObjectTouchedPlayer(attackBox);
	}
	public void checkSpikesTouched(Player p) {
		// TODO Auto-generated method stub
		objectManager.checkSpikesTouched(p);
	}
	public void checkFlagTouched(Player p) {
		// TODO Auto-generated method stub
		objectManager.checkFlagTouched(p);
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		if (!gameOver) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				player.setAttacking(true);
				player.setSpeed(false);
				getGame().getAudioPlayer().playAttackSound();
			}
		}
			
			
			
			
//				 if(e.getButton() == MouseEvent.BUTTON3)
					//player.ChaosBlast();
				 
				
	}
	
	public void setLevelCompleted(boolean levelCompleted) {
		this.lvlCompleted=levelCompleted;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (gameOver)
			gameOverOverlay.keyPressed(e);
		else
			switch (e.getKeyCode()) {
			case KeyEvent.VK_Z: player.setUp(true);
			break;
			case KeyEvent.VK_Q: player.setLeft(true);
			break;
			case KeyEvent.VK_D: player.setRight(true);
			break;
			case KeyEvent.VK_S: player.setDown(true);
			break;
			case KeyEvent.VK_SPACE: player.setJump(true);
			
			break;
			case KeyEvent.VK_ESCAPE:
				paused = !paused;
				break;
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (!gameOver)
			switch (e.getKeyCode()) {
			case KeyEvent.VK_Z:
				player.setUp(false);
				player.setSpeed(false);
				break;
			case KeyEvent.VK_Q:
				player.setLeft(false);
				player.setSpeed(false);
				break;
			case KeyEvent.VK_D: 
				player.setRight(false);
				player.setSpeed(false);
				break;
			case KeyEvent.VK_S: 
				player.setDown(false);
				player.setSpeed(false);
			break;
			case KeyEvent.VK_SPACE: 
				player.setJump(false);
				player.setSpeed(false);
			break;
			}

	}

	

	@Override
	public void mousePressed(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mousePressed(e);
			else if(lvlCompleted)
				levelCompletionOverlay.mousePressed(e);
		}
		else
			gameOverOverlay.mousePressed(e);
		
			
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!gameOver) {
			if (paused) 
				pauseOverlay.mouseReleased(e);
			else if(lvlCompleted)
				levelCompletionOverlay.mouseReleased(e);
	}
		else
			gameOverOverlay.mouseReleased(e);
		}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!gameOver) {
			if (paused)
				pauseOverlay.mouseMoved(e);			
			else if(lvlCompleted)
				levelCompletionOverlay.mouseMoved(e);
			}
		else
			gameOverOverlay.mouseMoved(e);
		
		
	}

	public void unpauseGame() {
		paused = false;
	}	
	public void setMaxLvlOffset(int LvlOffset) {
		this.maxLvlOffsetX=LvlOffset;
	}

	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	public Player getPlayer() {
		return player;
	}
	
	
	public EnemyManager getEnemyManager() {
		return enemyManager;
	}
	public ObjectManager getObjectManager() {
		return objectManager;
	}
	public LevelManager getLevelManager() {
		return levelManager;
	}

	public void setPlayerDying(boolean b) {
		// TODO Auto-generated method stub
		
	}


	

}