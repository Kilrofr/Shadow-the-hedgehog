package utilz;

import java.awt.Color;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import entities.Crabby;
import main.Game;

import static utilz.Constants.EnemyConstants.CRABBY;

public class LoadSave {

	//public static final String LEVEL_ONE_DATA = "old/level_one_data_long.png";
	//public static final String MENU_BUTTONS = "old/button_atlas.png";
	//public static final String MENU_BACKGROUND = "old/menu_background.png";
	//public static final String PAUSE_BACKGROUND = "old/pause_menu.png";
//	public static final String SOUND_BUTTONS = "old/sound_button.png";
	public static final String URM_BUTTONS = "cenarios/lvloverlaybutton.png";
	public static final String VOLUME_BUTTONS = "old/volume_buttons.png";
	public static final String MENU_BACKGROUND_IMG = "cenarios/283398.jpg";
	public static final String PLAYING_BG_IMG = "/cenarios/DHEXno8.png";
	public static final String BIG_CLOUDS = "cenarios/big_clouds1.png";
	public static final String SMALL_CLOUDS = "old/small_clouds.png";
	public static final String CRABBY_SPRITE = "old/crabby_sprite.png";
	public static final String STATUS_BAR = "Sprites/health_power_bar.png";

	public static final String PLAYER_ATLAS= "sprites/NewSprite1.png";
	public static final String LEVEL_ATLAS= "cenarios/tutofond1.png";

	public static final String MENU_BUTTONS= "Sprites/boutons.png";
	public static final String MENU_BACKGROUND= "Sprites/Background.png";
	public static final String MENU_START= "Sprites/pressstart.png";
	public static final String PAUSE_BACKGROUND= "Sprites/pausemenu.png";
	public static final String SOUND_BUTTONS= "Sprites/boutons1.png";
	public static final String LVLCOMPLETED= "cenarios/lvlcomplete.png";
	public static final String RINGS= "Sprites/rings.png";
	public static final String TRAP_ATLAS= "Sprites/SMSpikes.png";
	public static final String FLAG_ATLAS= "Sprites/Flag.png";
	public static final String GAMEOVER= "Sprites/gameover.png";
	public static final String EXPLOSION= "Sprites/souffleChaosBlast.png";
	

	
	public static BufferedImage GetSpriteAtlas(String fileName) {
		BufferedImage img = null;
		InputStream is = LoadSave.class.getResourceAsStream("/images/" + fileName);
		try {
			img = ImageIO.read(is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return img;
	}
	


	
	public static BufferedImage[] GetAllLevels() {
		URL url = LoadSave.class.getResource("/images/cenarios/lvls/");
		File file = null;
		
		try {
			file = new File(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		File[] files = file.listFiles();
		File[] filesSorted = new File[files.length];
		
		for  (int i=0;i<filesSorted.length;i++) 
			for  (int j=0;j<files.length;j++) {
				if (files[j].getName().equals((i+1) +".png"))
					filesSorted[i] = files[j];
			}
		 BufferedImage[] imgs = new BufferedImage[filesSorted.length];
		 
		 for (int i =0;i<imgs.length;i++)
			try {
				imgs[i]=ImageIO.read(filesSorted[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return imgs;
	}


	

}