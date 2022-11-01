package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.PlayerChaosBlast.*;
import static utilz.Constants.GRAVITY;
import static utilz.HelpMethods.*;
import java.util.ArrayList;

import audio.AudioPlayer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import objects.Explosion;
import objects.ObjectManager;
import objects.Ring;
import objects.Spike;
import utilz.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 15;
	private int playerAction = rien;
	private boolean moving = false, fullspeed=false, attacking = false;
	private boolean left, up, right, down, jump;
	private float playerSpeed = 2.0f * Game.SCALE;
	private int[][] lvlData;
	private float xDrawOffset = 4 * Game.SCALE;
	private float yDrawOffset = 4 * Game.SCALE;

	// Jumping / Gravity
	private float airSpeed = 0f;
	private float jumpSpeed = -2.35f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;

	// StatusBarUI
	private BufferedImage statusBarImg;

	private int statusBarWidth = (int) (192 * Game.SCALE);
	private int statusBarHeight = (int) (58 * Game.SCALE);
	private int statusBarX = (int) (10 * Game.SCALE);
	private int statusBarY = (int) (10 * Game.SCALE);
	
	private int powerBarWidth = (int) (104 * Game.SCALE);
	private int powerBarHeight = (int) (2 * Game.SCALE);
	private int powerBarXStart = (int) (44 * Game.SCALE);
	private int powerBarYStart = (int) (34 * Game.SCALE);
	private int powerWidth = powerBarWidth;
	private int powerMaxValue = 200;
	private int powerValue = powerMaxValue;
	
	//private ArrayList<Explosion>explosions;
	
	private int maxHealth = 10;
	private int currentHealth = maxHealth;
	
	private boolean powerAttackActive = false;	
	
	private int powerGrowSpeed;
	private int powerGrowTick;



	// AttackBox
	private Rectangle2D.Float attackBox;

	private int flipX = 0;
	private int flipW = 1;
	//ring


	private boolean attackChecked;
	private Playing playing;
	
	
	public Player(float x, float y, int width, int height, Playing playing) {
		super(x, y, width, height);
		this.playing = playing;
		loadAnimations();
		//loadChaosBlast();
		initHitbox(x, y, (int) (18 * Game.SCALE), (int) (28 * Game.SCALE));
		initAttackBox();
	}
	
	public void setSpawn(Point spawn) {
		this.x=spawn.x;
		this.y=spawn.y;
		
		hitbox.x=x;
		hitbox.y=y;
	}
	


	private void initAttackBox() {
		attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
	}

	public void update() {
	
		updatePowerBar();
		if (currentHealth <= 0) {
			if(playerAction != mort) {
				playerAction = mort;
				aniTick=0;
				aniIndex=0;
				playing.setPlayerDying(true);
				playing.getGame().getAudioPlayer().playEffect(AudioPlayer.mort);
				
			} else if(aniIndex==GetSpriteAmount(mort) - 1 && aniTick >= aniSpeed -1) {
				playing.setGameOver(true);
				playing.getGame().getAudioPlayer().stopSong();
			}
			else
				updateAnimationTick();		
			//playing.setGameOver(true);
			return;
		}

		updateAttackBox();
			
		updatePos();
		if (moving) {
			checkRingTouched();
			checkFlagTouched();
			checkSpikesTouched();
		}
		
		
		
		
		if (attacking || powerAttackActive)
			checkAttack();

		updateAnimationTick();
		setAnimation();
	}

				
		





	private void updatePowerBar() {
		// TODO Auto-generated method stub
		powerWidth = (int) ((powerValue / (float) powerMaxValue) * powerBarWidth);
		powerGrowTick++;
		if(powerGrowTick>=powerGrowSpeed) {
			powerGrowTick=0;
			changePower(1);
		}
	}

	private void changePower(int value) {
		// TODO Auto-generated method stub
		powerValue+=value;
		if(powerValue>=powerMaxValue) {
			powerValue=powerMaxValue;
			}
		else if(powerValue<=0)
			powerValue=0;
		
		
	}

	private void checkFlagTouched() {
		// TODO Auto-generated method stub
		playing.checkFlagTouched(this);
	}

	private void checkSpikesTouched() {
		// TODO Auto-generated method stub
		playing.checkSpikesTouched(this);
	}

	private void checkAttack() {
		if (attackChecked || aniIndex != 1)
			return;
		
		playing.checkEnemyHit(attackBox);
		}

	private void updateAttackBox() {
		if (right)
			attackBox.x = hitbox.x + hitbox.width + (int) (Game.SCALE * 0.7);
		else if (left)
			attackBox.x = hitbox.x - hitbox.width - (int) (Game.SCALE * 1);

		attackBox.y = hitbox.y + (Game.SCALE * 10);
	}



	public void render(Graphics g, int lvlOffset) {
		g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset) - lvlOffset + flipX, (int) (hitbox.y - yDrawOffset), width * flipW, height, null);
		//drawHitbox(g, lvlOffset);
		//drawAttackBox(g, lvlOffset);
		drawUI(g);
	}

//	private void drawAttackBox(Graphics g, int lvlOffsetX) {
//		g.setColor(Color.red);
//		g.drawRect((int) attackBox.x - lvlOffsetX, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
//
//	}

	private void drawUI(Graphics g) {
		//g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
		//g.setColor(Color.red);
		
		g.setColor(Color.white);
		g.drawString("Rings: " + ObjectManager.nbreRing , 150, 50);
		
		//g.setColor(Color.yellow);
		//g.fillRect(powerBarXStart+statusBarX, powerBarYStart+statusBarY, powerWidth, powerBarHeight);
	}

	
	public void RingsAmountStats() {
//		if(attackChecked = true)
//			ObjectManager.nbreRing=0;

		if(ObjectManager.nbreRing==0)
			currentHealth=1;
		else
			currentHealth=100;
	}
	
	private void checkRingTouched() {
		// TODO Auto-generated method stub
		playing.checkRingTouched(hitbox);
		RingsAmountStats();
	}

	

	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
				attackChecked = false;

			
			if (moving) { //atteindre animation vitessemax
				moving=false;
				fullspeed=true;
				attacking=false;
			}
			if (fullspeed)
				if (attacking) { //atteindre animation vitessemax
					fullspeed=false;
					moving=true;

			}	
			}
			}
		}

	
	public void setSpeed(boolean fullspeed) {
		this.fullspeed=fullspeed;
		
	}
	public void setMoving(boolean moving) {
		this.moving=moving;
		
	}

	private void setAnimation() {
		
		int startAni = playerAction;
		aniSpeed=15;
		if (moving) {
			playerAction = courir;
			playerSpeed = 2.0f;
		}
		else {
			playerAction = rien;
			aniSpeed=20;
		}
		
//		if(powerAttackActive) {
//			aniSpeed=25;
//			playerAction=chaosBlast;
//			}
		
		
	
		if (attacking) {
			if(!inAir) {
				aniSpeed=15;
				playerAction = attaque1;
				if (aniIndex==GetSpriteAmount(attaque1) - 1 && aniTick >= aniSpeed -1) {
					attacking=false;
					moving=true;
					fullspeed = false;
				}	
			}
			else 
				playerAction = attaqueBalais;
			}
		
		if (fullspeed) {
			playerAction = courirmax;
			playerSpeed += 1.0f;
			
			
		}
		
		if (startAni != playerAction) 
			resetAniTick();
		if (inAir) {
			if(airSpeed<0)
				playerAction = saut;
			else playerAction = falling;
			if (attacking) 
				playerAction = attaqueBalais;
			if(powerAttackActive)
				playerAction=chaosBlast;
		}
		}


	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void updatePos() {
		moving = false;

		if (jump)
			jump();

		if (!inAir)
			if(!powerAttackActive)
				if ((!left && !right) || (right && left))
					return;
		

		float xSpeed = 0;

		if (left) {
			xSpeed -= playerSpeed;
			flipX = width;
			flipW = -1;
		}
		if (right) {
			xSpeed += playerSpeed;
			flipX = 0;
			flipW = 1;
		}
		
		if(powerAttackActive)
			xSpeed=0;

		if (!inAir)
			if (!IsEntityOnFloor(hitbox, lvlData))
				inAir = true;

		if (inAir && !powerAttackActive) { //pas update Y si on est en chaosblast
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += GRAVITY;
				updateXPos(xSpeed);
			} else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}

		} else
			updateXPos(xSpeed);
		moving = true;
	}

	private void jump() {
		if (inAir)
			return;
		playing.getGame().getAudioPlayer().playEffect(AudioPlayer.saut);
		inAir = true;
		airSpeed = jumpSpeed;
		
		

	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}

	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData))
			hitbox.x += xSpeed;
		else
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
	}

	public void changeHealth(int value) {
		currentHealth += value;

		if (currentHealth <= 0)
			currentHealth = 0;
		else if (currentHealth >= maxHealth)
			currentHealth = maxHealth;
	}

	private void loadAnimations() {		
			BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
			animations = new BufferedImage[16][33];
			for (int j=0; j < animations.length;j++)		
			for (int i=0; i<animations.length; i++) 
				animations[j][i]=img.getSubimage(i*55, j*46, 56, 47);

		statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
	}
	

	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}

	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
		moving=true;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public void resetAll() {
		resetDirBooleans();
		inAir = false;
		attacking = false;
		moving = false;
		playerAction = rien;
		currentHealth = maxHealth;

		hitbox.x = x;
		hitbox.y = y;

		if (!IsEntityOnFloor(hitbox, lvlData))
			inAir = true;
	}

	public void kill() {
		// TODO Auto-generated method stub
		currentHealth=0;
	}
	


		
		
			
	
	}
