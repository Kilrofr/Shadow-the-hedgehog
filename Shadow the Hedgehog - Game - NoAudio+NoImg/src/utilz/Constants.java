package utilz;

import main.Game;

public class Constants {
	public static final float GRAVITY = 0.04f * Game.SCALE;

	public static class objectsConstants {
		public static final int RING = 51;
		public static final int SPIKE = 4;
		public static final int FLAG = 5;		
		
		public static final int RING_WIDTH_DEFAULT = 74;
		public static final int RING_HEIGHT_DEFAULT = 60;
		public static final int RING_WIDTH = (int) (RING_WIDTH_DEFAULT * Game.SCALE)/3;
		public static final int RING_HEIGHT = (int) (RING_HEIGHT_DEFAULT * Game.SCALE)/3;
		
		
		public static final int SPIKE_WIDTH_DEFAULT = 32;
		public static final int SPIKE_HEIGHT_DEFAULT = 32;
		public static final int SPIKE_WIDTH = (int) (SPIKE_WIDTH_DEFAULT * Game.SCALE);
		public static final int SPIKE_HEIGHT = (int) (SPIKE_HEIGHT_DEFAULT * Game.SCALE);
		
		public static final int FLAG_WIDTH_DEFAULT = 30;
		public static final int FLAG_HEIGHT_DEFAULT = 30;
		public static final int FLAG_WIDTH = (int) (FLAG_WIDTH_DEFAULT * Game.SCALE);
		public static final int FLAG_HEIGHT = (int) (FLAG_HEIGHT_DEFAULT * Game.SCALE);
		
		public static int GetSpriteAmount(int objtype) {
			switch (objtype) {
			case RING:
				return 30;
				}
			return 0;
		}
	}
	
	public static class EnemyConstants {
		public static final int CRABBY = 0;

		public static final int IDLE = 0;
		public static final int RUNNING = 1;
		public static final int ATTACK = 2;
		public static final int HIT = 3;
		public static final int DEAD = 4;

		public static final int CRABBY_WIDTH_DEFAULT = 72;
		public static final int CRABBY_HEIGHT_DEFAULT = 32;

		public static final int CRABBY_WIDTH = (int) (CRABBY_WIDTH_DEFAULT * Game.SCALE);
		public static final int CRABBY_HEIGHT = (int) (CRABBY_HEIGHT_DEFAULT * Game.SCALE);

		public static final int CRABBY_DRAWOFFSET_X = (int) (26 * Game.SCALE);
		public static final int CRABBY_DRAWOFFSET_Y = (int) (9 * Game.SCALE);

		public static int GetSpriteAmount(int enemy_type, int enemy_state) {

			switch (enemy_type) {
			case CRABBY:
				switch (enemy_state) {
				case IDLE:
					return 9;
				case RUNNING:
					return 6;
				case ATTACK:
					return 7;
				case HIT:
					return 4;
				case DEAD:
					return 5;
				}
			}

			return 0;

		}

		public static int GetMaxHealth(int enemy_type) {
			switch (enemy_type) {
			case CRABBY:
				return 10;
			default:
				return 1;
			}
		}

		public static int GetEnemyDmg(int enemy_type) {
			switch (enemy_type) {
			case CRABBY:
				return 15;
			default:
				return 0;
			}

		}

	}

	public static class Environment {
		public static final int BIG_CLOUD_WIDTH_DEFAULT = 108;
		public static final int BIG_CLOUD_HEIGHT_DEFAULT = 80;

		public static final int BIG_CLOUD_WIDTH = (int) (BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
		public static final int BIG_CLOUD_HEIGHT = (int) (BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
		
		
		public static final int background_WIDTH_DEFAULT = 600;
		public static final int background_HEIGHT_DEFAULT = 460;

		public static final int  background_WIDTH = (int) (background_WIDTH_DEFAULT * Game.SCALE);
		public static final int  background_HEIGHT = (int) (background_HEIGHT_DEFAULT * Game.SCALE);

	}

	public static class UI {
		public static class Buttons{
			public static final int B_WIDTH_DEFAULT = 123;
			public static final int B_HEIGHT_DEFAULT = 26;
			public static final int B_WIDTH =(int) (B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT =(int) (B_HEIGHT_DEFAULT * Game.SCALE);
			}
		public static class PauseButtons{
			public static final int SOUND_SIZE_DEFAULT = 81; //carre
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE / 2); // /2 pour rendre image plus ptite

		}

		public static class URMButtons {
		
			public static final int URM_DEFAULT_WIDTH = 522;
			public static final int URM_DEFAULT_HEIGHT = 74;
			public static final int B_WIDTH =(int) (URM_DEFAULT_WIDTH * Game.SCALE);
			public static final int B_HEIGHT =(int) (URM_DEFAULT_HEIGHT * Game.SCALE);

		}

		public static class VolumeButtons {
			public static final int VOLUME_DEFAULT_WIDTH = 28;
			public static final int VOLUME_DEFAULT_HEIGHT = 44;
			public static final int SLIDER_DEFAULT_WIDTH = 215;

			public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
		}
	}

	public static class Directions {
		public static final int LEFT = 0;
		public static final int UP = 1;
		public static final int RIGHT = 2;
		public static final int DOWN = 3;
	}
	
	
	
	
	public static class Explosion {
		public static final int Explosion_DEFAULT_WIDTH = 441;
		public static final int Explosion_DEFAULT_HEIGHT = 446;
		public static final int Explosion_WIDTH =(int) (Explosion_DEFAULT_WIDTH * Game.SCALE);
		public static final int Explosion_HEIGHT =(int) (Explosion_DEFAULT_HEIGHT * Game.SCALE);
	}

		
	public static class PlayerChaosBlast {
		public static final int debut = 0;
		public static final int fin = 1;
	
	public static int GetSpriteAmount1 (int player_action1) {
		switch (player_action1) {
		case debut:
			return 11;
		case fin:
			return 4;
		default :
			return 1;
		}
	}
	}

	
	
	
	
	public static class PlayerConstants {
		public static final int rien = 0;
		public static final int courir = 1;
		public static final int courirmax = 2;
		public static final int attaque1 = 4;
		public static final int saut = 6;
		public static final int falling = 8;
		public static final int touché = 11;
		public static final int attaqueBalais = 9;
		//public static final int jsp =8;
		public static final int chaosBlast = 5;
		public static final int mort = 10;
		
		public static final int powerup = 15;
	
	public static int GetSpriteAmount (int player_action) {
		switch (player_action) {
		case rien:
			return 15;
		case courir:
			return 12;
		case courirmax:
			return 16;
		case touché:
		case mort:
			return 2;
		case attaque1:
			return 3;		
		case chaosBlast:
			return 15;	
		case attaqueBalais:
			return 12;	
		case saut:
			return 1;
		case falling:
			return 2;
		case powerup:
			return 3;
		default :
			return 1;
		}
	}

	}
	}