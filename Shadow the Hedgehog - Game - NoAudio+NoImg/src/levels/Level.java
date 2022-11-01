package levels;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import entities.Crabby;
import main.Game;
import objects.Flag;
import objects.Ring;
import objects.Spike;
import utilz.HelpMethods;

import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetCrabs;
import static utilz.HelpMethods.GetPlayerSpawn;

public class Level {
	private BufferedImage img;
	private int[][] lvlData;
	private ArrayList<Crabby>crabs;
	private ArrayList<Ring>rings;
	private ArrayList<Spike>spikes;
	private ArrayList<Flag>flags;
	private int lvlTilesWide;
	private int maxTilesOffset;
	private int maxLvlOffsetX;
	private Point playerSpawn;
	

	public Level(BufferedImage img) {
		this.img = img;
		createLevelData();
		createEnnemies();
		createRings();
		createSpikes();
		createFlag();
		calcLvlOffset();
		calcPlayerSpawn();
	}

	private void createFlag() {
		// TODO Auto-generated method stub
		flags = HelpMethods.GetFlags(img);

	}

	private void createSpikes() {
		spikes = HelpMethods.GetSpikes(img);
	}


	private void createRings() {
		// TODO Auto-generated method stub
		rings=HelpMethods.GetRings(img);
	}

	private void calcPlayerSpawn() {
		// TODO Auto-generated method stub
		playerSpawn = GetPlayerSpawn(img);
	}

	private void calcLvlOffset() {
		// TODO Auto-generated method stub
		lvlTilesWide = img.getWidth();
		maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
		maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
	}

	private void createEnnemies() {
		// TODO Auto-generated method stub
		crabs = GetCrabs(img);
	}

	private void createLevelData() {
		// TODO Auto-generated method stub
		lvlData = GetLevelData(img);
		
	}

	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}

	public int[][] getLevelData() {
		return lvlData;
	}	
	public int getLvlOffset() {
		return maxLvlOffsetX;
	}

	public ArrayList<Crabby> getCrabs(){
		return crabs;
	}	
	public ArrayList<Ring> getRings(){
		return rings;
	}	
	public ArrayList<Spike> getSpikes() {
		return spikes;
	}
	public Point getPlayerSpawn() {
		return playerSpawn;
	}

	public ArrayList<Flag> getFlags() {
		return flags;
	}

	}
